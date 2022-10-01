package com.expostore.ui.fragment.product.addreview

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.expostore.databinding.AddReviewFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.utils.TenderCreateImageRecyclerViewAdapter
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddReviewFragment :
    BaseFragment<AddReviewFragmentBinding>(AddReviewFragmentBinding::inflate) {

    private  val addReviewViewModel: AddReviewViewModel by viewModels()

    private  val images: MutableList<String> by lazy {
        mutableListOf("")
    }
    private  val mAdapter: TenderCreateImageRecyclerViewAdapter by lazy {
        TenderCreateImageRecyclerViewAdapter(images)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("key_product") { _, bundle ->
            val result = bundle.getString("productId")
            addReviewViewModel.saveProduct(result?:"")
        }
        binding.apply {
            ratingBar.setOnRatingChangeListener { ratingBar, rating, fromUser ->
                tvRating.text =
                    "Оценка: " + rating.toInt() + "/" + ratingBar.numStars.toString()

            }

            btnSaveReview.setOnClickListener {
                addReviewViewModel.saveReview(etReview.text.toString(),ratingBar.rating.toInt(),requireContext())
            }
        }
        val reviewTextWatcher: TextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (binding.etReview.text.isNotEmpty() ) {
                        binding.btnSaveReview.isEnabled = true
                    }
                }
            }
            binding.etReview.addTextChangedListener(reviewTextWatcher)
        binding.rvReviewImages.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = mAdapter
            }
            mAdapter.onClick = addPhoto()
        }




    private fun addPhoto(): TenderCreateImageRecyclerViewAdapter.OnClickListener {
        return object : TenderCreateImageRecyclerViewAdapter.OnClickListener {
            override fun addPhoto() {
                ImagePicker.with(this@AddReviewFragment)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent { intent -> launchSomeActivity.launch(intent) }
            }

            override fun removePhoto(index: Int) {
                TODO()
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
                    addReviewViewModel.saveUri(fileUri)
                    mAdapter.update(fileUri.toString())

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