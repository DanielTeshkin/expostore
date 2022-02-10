package com.expostore.ui.tender.create

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.TenderCreateFragmentBinding
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.expostore.utils.decodeImage
import com.expostore.utils.encodeImage
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
import java.lang.Exception

class TenderCreateFragment : Fragment() {

    private lateinit var binding: TenderCreateFragmentBinding
    private lateinit var tenderCreateViewModel: TenderCreateViewModel
    private lateinit var mAdapter: TenderCreateImageRecyclerViewAdapter
    private lateinit var images: ArrayList<Bitmap>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.tender_create_fragment, container, false)
        tenderCreateViewModel = ViewModelProvider(this).get(TenderCreateViewModel::class.java)
        binding.tenderCreateVM = tenderCreateViewModel
        tenderCreateViewModel.context = requireContext()
        return binding.root
    }

    //TODO доделать работу кнопки сохранить (выключение и включение)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<ArrayList<String>>("specifications")
            ?.observe(viewLifecycleOwner) {
                tenderCreateViewModel.category = it
            }


        // Передача параметров во вьюмодель
        tenderCreateViewModel.btnCancel = binding.btnCancel
        tenderCreateViewModel.btnSaveDraft = binding.btnSaveDraft
        tenderCreateViewModel.btnSave = binding.btnSave

        // Подключение наблюдателя текста
        binding.etTenderName.addTextChangedListener(tenderCreateViewModel.tenderTextWatcher)
        binding.etTenderDescription.addTextChangedListener(tenderCreateViewModel.tenderTextWatcher)
        binding.etTenderPriceFrom.addTextChangedListener(tenderCreateViewModel.tenderTextWatcher)
        binding.etTenderPriceUpTo.addTextChangedListener(tenderCreateViewModel.tenderTextWatcher)
        binding.etTenderLocation.addTextChangedListener(tenderCreateViewModel.tenderTextWatcher)

        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        val bmp = Bitmap.createBitmap(100, 100, conf)
        images = arrayListOf(bmp)

        mAdapter = TenderCreateImageRecyclerViewAdapter(images)
        binding.rvTenderImages.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = addPhoto()
        mAdapter.notifyDataSetChanged()
    }

    private fun addPhoto(): TenderCreateImageRecyclerViewAdapter.OnClickListener {
        return object : TenderCreateImageRecyclerViewAdapter.OnClickListener {
            override fun addPhoto() {
                //TODO Добавить выборку изображения для Android 8-9

                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "*/*"
                val mimeTypes = arrayOf("image/*")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                launchSomeActivity.launch(intent)
            }
        }
    }

    var launchSomeActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val uri = data!!.data
            val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri!!)

            if (inputStream != null) saveImage(encodeImage(inputStream))
            else Toast.makeText(context,"Не удалось добавить изображение", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveImage(image: String){
        val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
        val requestData = SaveImageRequestData(image,"jpeg")

        serverApi.saveImage("Bearer $token", requestData).enqueue(object : Callback<SaveImageResponseData> {
            override fun onResponse(call: Call<SaveImageResponseData>, response: Response<SaveImageResponseData>) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.id?.let { addImage(image, it) }

                    } else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("detail")
                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e: Exception){
                    Log.d("exception", e.toString())
                }
            }

            override fun onFailure(call: Call<SaveImageResponseData>, t: Throwable) {
                Toast.makeText(context, (context as MainActivity).getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun addImage(image: String, id:String){
        tenderCreateViewModel.imagesId.add(id)
        images.add(decodeImage(image))

        mAdapter = TenderCreateImageRecyclerViewAdapter(images)
        binding.rvTenderImages.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = addPhoto()
        mAdapter.notifyDataSetChanged()
    }
}