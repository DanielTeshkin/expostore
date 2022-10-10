package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.extension.lastElement
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.model.review.ReviewModel
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.ImageItemAdapter
import com.expostore.ui.base.fragments.BaseProductFragment
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.controllers.ControllerUI
import com.expostore.ui.fragment.product.reviews.ReviewRecyclerViewAdapter
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductFragment : BaseProductFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate),OnMapReadyCallback
    {
       override  val viewModel:ProductViewModel by viewModels()
        private val imageAdapter:ImageItemAdapter by lazy { ImageItemAdapter() }
        private val reviewsList= mutableListOf<ReviewModel>()
        private val onClickImage :OnClickImage by lazy {
            object : OnClickImage {
                override fun click(bitmap: Bitmap) {
                    ControllerUI(requireContext()).openImageFragment(bitmap)
                        .show(requireActivity().supportFragmentManager, "DialogImage")
                }
            }
        }
        private val reviewAdapter:ReviewRecyclerViewAdapter by lazy { ReviewRecyclerViewAdapter(reviewsList,onClickImage) }
        override var isBottomNavViewVisible: Boolean = false

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
        ProductFragmentArgs.fromBundle(requireArguments()).apply {
            product?.let { viewModel.saveProduct(it) }
            viewModel.getProduct(id)
            subscribeUI()
        }
    }

       private fun subscribeUI(){
           viewModel.apply {
               start(findNavController())
               subscribe(navigation) { navigateSafety(it) }
               subscribe(visible) { initPriceHistoryButton(it) }
               subscribe(visibleInstruction) { instructionInit(it) }
               subscribe(visiblePresentation) { presentationInit(it) }
               subscribe(product) { observeUI(it) }
           }
       }

        @SuppressLint("SetTextI18n")
        private fun observeUI(model: ProductModel) {
            initButton(model)
            Log.i("id",model.id)
            binding.apply {
                tvProductPrice.text = model.price.priceSeparator()
                tvProductName.text = model.name
                tvProductDescription.text = model.longDescription
                imageAdapter.items = model.images.map { it.file }
                imageAdapter.openImageOnFullScren={onClickImage.click(it)}
                rvProductImages.adapter = imageAdapter
                ivProductShopImage.loadAvatar(model.shop.image.file)
                tvProductRating.text = "Оценка: " + model.rating
                rbProductRating.rating = model.rating.toFloat()
                tvProductLocation.text=model.shop.address
                articul.text="Артикул:"+" "+model.articul
                if(model.count>0){
                    tvProductAvailable.setTextColor(Color.BLUE)
                    tvProductAvailable.text="В наличии"
                }
                if (model.reviews.isNotEmpty()) {
                    linearLayout4.visibility= View.VISIBLE
                    tvProductAllReviews.click { viewModel
                        .navigationToReview(ReviewsModel( reviews = model.reviews)) }
                    rvProductReviews.apply {
                        visibility=View.VISIBLE
                        layoutManager = LinearLayoutManager(requireContext())
                        if (reviewAdapter.itemCount<1) {
                            model
                                .reviews
                                .lastElement()
                                .let { reviewsList.add(it) }
                        }
                        adapter = reviewAdapter
                    }
                }
                if (model.elected.notes.isEmpty())binding.editNote.text="Cоздать заметку"
                else  binding.tvProductNote.text=model.elected.notes
            }

        }

      private  fun initButton(model: ProductModel){
            binding.apply {
                btnCallDown.click { navigateToCall(model.author.username) }
                write.click { viewModel.createChat(model.id) }
                btnProductAddReview.click {
                    setFragmentResult("key_product", bundleOf("productId" to model.id))
                    viewModel.navigationToAddReview()
                }
              llProductShop.click {
                  setFragmentResult("shop", bundleOf("model" to model.shop.id))
                  viewModel.navigationToShop()
              }
                btnBack.click { viewModel.navigateToBack() }
                editNote.click { viewModel.navigationToNote() }
                categoryOpen.apply {
                    character.visible(model.characteristics.isNotEmpty())
                    click{viewModel.navigateToCharacteristics()}
                }
                like.click { viewModel.updateSelected(model.id) }
                qrCode.click {
                    setFragmentResult("request_key", bundleOf("image" to model.qrcode.qr_code_image))
                    viewModel.navigationToQrCodeFragment()
                }

            }

      }
        private fun initPriceHistoryButton(state:Boolean){
            binding.history.apply { visible(state)
                click { viewModel.navigateToPriceHistory() }
            }
        }
        private fun instructionInit(state: Boolean){
            binding.apply {  instruction.visible(state)
                textInstruction.click { viewModel.navigateToInstruction() }
            }
        }
        private fun presentationInit(state: Boolean){
            binding.apply { presentation.visible(state)
              presentationOpen.click { viewModel.navigateToPresentation() }
            }
        }

        override fun onMapReady(map: GoogleMap) {
            state { viewModel.product.collect {
                    val myLocation = LatLng(it.shop.lat, it.shop.lng)
                    map.addMarker(MarkerOptions().position(myLocation))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,5f))
                    map.uiSettings.isZoomGesturesEnabled = true
                    map.uiSettings.isScrollGesturesEnabled=true
                }
            }
        }
        override fun loadSelections(list: List<SelectionModel>) { binding.another
            .click { getClickListener(list).onClickAnother(viewModel.product.value)} }
    }