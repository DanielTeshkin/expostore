package com.expostore.ui.fragment.tender.create

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R

import com.expostore.api.ServerApi
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.TenderCreateFragmentBinding
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.expostore.utils.decodeImage
import com.expostore.utils.encodeImage
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.InputStream
@AndroidEntryPoint
class TenderCreateFragment :
    BaseFragment<TenderCreateFragmentBinding>(TenderCreateFragmentBinding::inflate) {

    private  val tenderCreateViewModel :TenderCreateViewModel by viewModels()
    private lateinit var mAdapter: TenderCreateImageRecyclerViewAdapter
    private lateinit var images: ArrayList<Uri>


    //TODO доделать работу кнопки сохранить (выключение и включение)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("category") { _, bundle ->
            val result = bundle.getParcelable<ProductCategoryModel>("intent")
            if (result != null) {
                tenderCreateViewModel.apply {
                    saveCategory(result.id)
                }
            }
        }
        binding.llTenderCreateCategory.click {
            setFragmentResult("requestKey", bundleOf("flag" to "tender"))
            tenderCreateViewModel.navigateToCategory()
        }
        tenderCreateViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }

        images = ArrayList<Uri>()
        val uri=Uri.parse("")
        images.add(uri)

        mAdapter = TenderCreateImageRecyclerViewAdapter(images)
        binding.rvTenderImages.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = addPhoto()
        binding.apply {
            btnSave.click {
                val connect= if(message.isChecked) "chatting"
                else "call_and_chatting"
            tenderCreateViewModel.createTender(
                name = etTenderName.text.toString(),
                description = etTenderDescription.text.toString(),
                count = etTenderCount.text.toString(),
                location = etTenderLocation.text.toString(),
                priceFrom = etTenderPriceFrom.text.toString(),
                priceUp = etTenderPriceFrom.text.toString(),
                contact = connect,
                context = requireContext()

            )
        }}

    }

    private fun addPhoto(): TenderCreateImageRecyclerViewAdapter.OnClickListener {
        return object : TenderCreateImageRecyclerViewAdapter.OnClickListener {
            override fun addPhoto() {
                ImagePicker.with(this@TenderCreateFragment)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent { intent -> launchSomeActivity.launch(intent) }
            }
        }
    }

    var launchSomeActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result  ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val fileUri = data?.data!!
                    tenderCreateViewModel.saveUri(fileUri)
                    mAdapter.update(fileUri)

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Вы вышли", Toast.LENGTH_SHORT).show()
                }
            }
        }




}