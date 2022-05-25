package com.expostore.ui.fragment.product.addreview

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R

import com.expostore.api.ServerApi
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.AddReviewFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.expostore.utils.decodeImage
import com.expostore.utils.encodeImage
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream

class AddReviewFragment :
    BaseFragment<AddReviewFragmentBinding>(AddReviewFragmentBinding::inflate) {

    private lateinit var addReviewViewModel: AddReviewViewModel
    private lateinit var mAdapter: TenderCreateImageRecyclerViewAdapter
    private lateinit var images: ArrayList<Bitmap>
    var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addReviewViewModel = ViewModelProvider(this).get(AddReviewViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ratingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
            binding.tvRating.text =
                "Оценка: " + rating.toInt() + "/" + ratingBar.numStars.toString()
            addReviewViewModel.rating = rating.toInt()
        }

        binding.btnSaveReview.setOnClickListener {
            addReviewViewModel.saveReview(it)
        }
        addReviewViewModel.context = requireContext()
        id = arguments?.getString("id")
        addReviewViewModel.id = id
        binding.etReview.addTextChangedListener(addReviewViewModel.reviewTextWatcher)

        val conf = Bitmap.Config.ARGB_8888 // see other conf types
        val bmp = Bitmap.createBitmap(100, 100, conf)
        images = arrayListOf(bmp)

        mAdapter = TenderCreateImageRecyclerViewAdapter(images)
        binding.rvReviewImages.apply {
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

                val intent = Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                intent.type = "*/*"
                val mimeTypes = arrayOf("image/*")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                launchSomeActivity.launch(intent)
            }
        }
    }

    var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val uri = data!!.data
                val inputStream: InputStream? =
                    requireContext().contentResolver.openInputStream(uri!!)

                if (inputStream != null) saveImage(encodeImage(inputStream))
                else Toast.makeText(context, "Не удалось добавить изображение", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private fun saveImage(image: String) {
        val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")
        //val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
        val requestData = SaveImageRequestData(image, "jpeg")

       /* serverApi.saveImage("Bearer $token", requestData).enqueue(object :
            Callback<SaveImageResponseData> {
            override fun onResponse(
                call: Call<SaveImageResponseData>,
                response: Response<SaveImageResponseData>
            ) {
                try {
                    if (response.isSuccessful) {
                        response.body()?.id?.let { addImage(image, it)
                            Log.d("1","try/catch1")
                        }

                    } else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("detail")
                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.d("exception", e.toString())
                }
            }

            override fun onFailure(call: Call<SaveImageResponseData>, t: Throwable) {
                Toast.makeText(
                    context,
                    (context as MainActivity).getString(R.string.on_failure_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        })*/
    }

    private fun addImage(image: String, id: String) {
        addReviewViewModel.imagesId.add(id)
        images.add(decodeImage(image))

        mAdapter = TenderCreateImageRecyclerViewAdapter(images)
        binding.rvReviewImages.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
            context
            Log.d("1","addImage2")
        }
        mAdapter.onClick = addPhoto()
        mAdapter.notifyDataSetChanged()
        Toast.makeText(context,"QQQ",Toast.LENGTH_LONG).show()
    }

}