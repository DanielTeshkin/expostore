package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.extension.lastElement
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.product.priceSeparator
import com.expostore.model.review.ReviewModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.chats.general.ControllerUI
import com.expostore.ui.fragment.note.NoteData
import com.expostore.ui.fragment.product.reviews.ReviewRecyclerViewAdapter
import com.expostore.ui.fragment.product.utils.openBottomSheet
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickImage
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.*

@AndroidEntryPoint
class ProductFragment : BaseFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate),OnMapReadyCallback
    {
        private val productViewModel:ProductViewModel by viewModels()
        private val imageAdapter:ImageAdapter by lazy { ImageAdapter() }
        private val reviewsList= mutableListOf<ReviewModel>()

        private val onClickImage :OnClickImage by lazy {
            object : OnClickImage {
                override fun click(bitmap: Bitmap) {
                    ControllerUI(requireContext()).openImageFragment(bitmap)
                        .show(requireActivity().supportFragmentManager, "DialogImage")
                }
            }
        }
        private val reviewAdapter:ReviewRecyclerViewAdapter by lazy {
            ReviewRecyclerViewAdapter(reviewsList,onClickImage)
        }



    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val result=ProductFragmentArgs.fromBundle(requireArguments()).product
        result.let { productViewModel.saveProduct(it) }
        initButton(result)
        setFragmentResultListener("requestNote"){_,bundle->
            val result=bundle.getString("text")
            binding.tvProductNote.text=result
            if (result==null) binding.editNote.text="Создать заметку"
        }
        subscribeUI()
        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

       private fun subscribeUI(){
           observeNavigation()
           state { productViewModel.product.collect { observeUI(it) } }
       }

        @SuppressLint("SetTextI18n")
        private fun observeUI(model: ProductModel) {
            binding.apply {
                tvProductPrice.text = model.price.priceSeparator()
                tvProductName.text = model.name
                tvProductDescription.text = model.longDescription
                imageAdapter.items = model.images.map { it.file }
                rvProductImages.adapter = imageAdapter
                ivProductShopImage.loadAvatar(model.shop.image.file)
                tvProductRating.text = "Оценка: " + model.rating
                rbProductRating.rating = model.rating.toFloat()
                tvProductLocation.text=model.shop.address
                if(model.count>0){
                    tvProductAvailable.setTextColor(Color.BLUE)
                    tvProductAvailable.text="В наличии"
                }
                if (model.reviews.isNotEmpty()) {
                    linearLayout4.visibility=View.VISIBLE
                    tvProductAllReviews.click {
                        setFragmentResult("reviews", bundleOf("list" to ReviewsModel( reviews = model.reviews)))
                        productViewModel.navigationToReview()
                    }
                    rvProductReviews.apply {
                        visibility=View.VISIBLE
                        layoutManager = LinearLayoutManager(requireContext())
                        model.reviews.lastElement().let { reviewsList.add(it) }
                        adapter = reviewAdapter
                    }
                }
            }

        }

      private  fun initButton(model: ProductModel){
            binding.apply {
                btnCallDown.click {
                    navigateToCall(model.author.username)
                }
                write.click { productViewModel.createChat(model.id, "product") }
                btnProductAddReview.click {
                    setFragmentResult("key_product", bundleOf("productId" to model.id))
                    productViewModel.navigationToAddReview()
                }
              llProductShop.click {
                  setFragmentResult("shop", bundleOf("model" to model.shop.id))
                  productViewModel.navigationToShop()
              }
                editNote.click {
                    setFragmentResult("dataNote", bundleOf("note" to NoteData(id=model.id,
                        flag = "product", flagNavigation = "product", isLiked = model.isLiked) ))
                    productViewModel.navigationToNote()
                }
                categoryOpen.click {
                     openBottomSheet(model.characteristics,requireContext())
                }
                qrCode.click {
                    setFragmentResult("request_key", bundleOf("image" to model.qrcode.qr_code_image))
                    productViewModel.navigationToQrCodeFragment()
                }

            }

      }


        private fun observeNavigation(){
            val chatNavigate:Show<MainChat> =  {chatOpen(it)}
            productViewModel.apply {
                subscribe(navigation){navigateSafety(it)}
                subscribe(chatUI){handleState(it,chatNavigate)}
            }
        }
        private fun chatOpen(chat:MainChat){
           Log.i("fff","fff")

        }



        override fun onMapReady(map: GoogleMap) {
            state {
                productViewModel.product.collect {
                    val myLocation = LatLng(it.shop.lat, it.shop.lng)
                    map.addMarker(MarkerOptions().position(myLocation))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation,5f))
                    map.uiSettings.isZoomGesturesEnabled = true
                    map.uiSettings.isScrollGesturesEnabled=true
                }
            }
        }


    }