package com.expostore.ui.fragment.product

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getreviews.Review
import com.expostore.databinding.ProductFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.ImageAdapter
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.product.reviews.ReviewRecyclerViewAdapter
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ProductFragment : BaseFragment<ProductFragmentBinding>(ProductFragmentBinding::inflate),OnMapReadyCallback
    {
        private val productViewModel:ProductViewModel by viewModels()
        private val imageAdapter:ImageAdapter by lazy { ImageAdapter() }




    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("requestKey"){_,bundle->
            val result=bundle.getParcelable<ProductModel>("product")
            result?.let { productViewModel.saveProduct(it) }
            if (result != null) {
                initButton(result)
            }
        }

        setFragmentResultListener("new_key"){_,bundle->
            val result=bundle.getParcelable<ProductModel>("product")
            result?.let { productViewModel.saveProduct(it) }
        }
        subscribeUI()

        binding.mapView.onCreate(savedInstanceState)
        binding.mapView.getMapAsync(this)
    }

       private fun subscribeUI(){
           observeNavigation()
           state {
               productViewModel.product.collect {
                   observeUI(it)
               }
           }

        }

        private fun observeUI(model: ProductModel) {
            binding.apply {
                tvProductPrice.text = model.price
                tvProductName.text = model.name
                tvProductDescription.text = model.longDescription
                imageAdapter.items = model.images.map { it.file }
                rvProductImages.adapter = imageAdapter
                ivProductShopImage.loadAvatar(model.shop.image.file)
                tvProductRating.text = "Оценка: " + model.rating
                rbProductRating.rating = model.rating.toFloat()
                if(model.count>0){
                    tvProductAvailable.setTextColor(Color.BLUE)
                    tvProductAvailable.text="В наличии"
                }
                rvProductReviews.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    val reviewList = mutableListOf<Review>()

                }
            }

        }

      private  fun initButton(model: ProductModel){
            binding.apply {
                btnCallDown.click {
                    navigateToCall(model.author.username)
                }
                write.click {
                    productViewModel.apply {
                        state {
                            createChat(model.id, "product").collect {

                                val result = InfoItemChat(
                                    it.identify()[1],
                                    it.identify()[0],
                                    it.chatsId(),
                                    it.imagesProduct(),
                                    it.productsName(), it.identify()[3]
                                )
                                setFragmentResult("new_key", bundleOf("info" to result))
                                    //  productViewModel.navigationChat()
                            }
                        }
                    }
                }
                btnProductAddReview.click {
                    setFragmentResult("key_product", bundleOf("productId" to model.id))
                    productViewModel.navigationToAddReview()
                }
                tvProductAllReviews.click {

                }
            }


        }


        private fun observeNavigation(){
            productViewModel.apply {
                subscribe(navigation){navigateSafety(it)}
            }
        }

        override fun onMapReady(map: GoogleMap) {

            state {
                productViewModel.product.collect {
                    val myLocation = LatLng(it.shop.lat, it.shop.lng)
                    map.addMarker(MarkerOptions().position(myLocation))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 5f))
                    map.uiSettings.isZoomGesturesEnabled = true
                    map.uiSettings.isScrollGesturesEnabled = true
                }
            }
        }


    }