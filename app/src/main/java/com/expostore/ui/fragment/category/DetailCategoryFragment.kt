package com.expostore.ui.fragment.category

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.api.request.ChatCreateRequest
import com.expostore.data.AppPreferences
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.product.name
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.utils.DetailCategoryRecyclerViewAdapter
import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import com.expostore.utils.OnClickRecyclerViewListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.product_item.*
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@AndroidEntryPoint
class DetailCategoryFragment :
    BaseFragment<DetailCategoryFragmentBinding>(DetailCategoryFragmentBinding::inflate) {

    private  val detailCategoryViewModel: DetailCategoryViewModel by viewModels()
    private lateinit var  mAdapter: ProductSelectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
           setFragmentResultListener("requestKey"){_,bundle->
               val result=bundle.getParcelable<SelectionModel>("selection")
               Log.i("my",result?.id?:"")
               result?.let { showUI(it) }
           }
        detailCategoryViewModel.apply {
          //  subscribe(selection){showUI(it)}
            subscribe(navigation){navigateSafety(it)}
        }
    }

    override fun onStart() {
        super.onStart()
       // detailCategoryViewModel.getProfile()
    }


  private  fun observeUI(){

    }

    private fun showUI(model: SelectionModel) {
        val onClickListener= object :OnClickListener{
            override fun onClickLike(id: String) {
                detailCategoryViewModel.updateSelected(id)
            }

            override fun onClickProduct(model: ProductModel) {
               setFragmentResult("requestKey", bundleOf("product" to model))
                detailCategoryViewModel.navigateToProduct()

            }

            override fun onClickMessage(model: ProductModel) {
              detailCategoryViewModel.apply {
                  state {
                      createChat(model.id).collect {

                          val result = InfoItemChat(
                              it.identify()[1],
                              it.identify()[0],
                              it.chatsId(),
                              it.imagesProduct(),
                              it.productsName(), it.identify()[3]
                          )
                          setFragmentResult("new_key", bundleOf("info" to result))
                          navigateToChat()
                      }
                  }
              }

            }

            override fun onClickCall(phone: String) {
                navigateToCall(phone)
            }

        }
        binding.apply {
            tvCategoryName.text=model.name
            rvDetailProduct.apply {
                layoutManager=LinearLayoutManager(requireContext())
                mAdapter= ProductSelectionAdapter(model.products as MutableList<ProductModel>)
                mAdapter.onClick=onClickListener

                adapter=mAdapter

            }
        }

    }


}