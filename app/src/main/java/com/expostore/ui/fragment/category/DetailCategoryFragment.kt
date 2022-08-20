package com.expostore.ui.fragment.category

import android.content.Intent
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
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.note.NoteData
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
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
    BaseFragment<DetailCategoryFragmentBinding>(DetailCategoryFragmentBinding::inflate) ,OnClickBottomSheetFragment{

    private  val detailCategoryViewModel: DetailCategoryViewModel by viewModels()
    private lateinit var  mAdapter: ProductSelectionAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

           setFragmentResultListener("requestKey"){_,bundle->
               val result=bundle.getParcelable<SelectionModel>("selection")
               Log.i("my",result?.id?:"")
               result?.let { showUI(it) }
           }
        setFragmentResultListener("requestKey"){_,bundle->
            val result=bundle.getString("flag")

        }
       observeChangeState()
    }


    private fun observeChangeState() {
        val show:Show<List<SelectionModel>> ={ initClickListener(it)}
        detailCategoryViewModel.apply {
            subscribe(selection) { showUI(it) }
            subscribe(navigation) { navigateSafety(it) }
            subscribe(selections) { handleState(it,show) }
        }
    }



    private fun showUI(model: SelectionModel) {
        binding.apply {
            tvCategoryName.text=model.name
            rvDetailProduct.apply {
                layoutManager=LinearLayoutManager(requireContext())
                mAdapter= ProductSelectionAdapter(model.products as MutableList<ProductModel>)
                adapter=mAdapter
            }
        }

    }
    private fun initClickListener(selections:List<SelectionModel>){
        mAdapter.onClick= object :OnClickListener{
            override fun onClickLike(id: String) {
                detailCategoryViewModel.updateSelected(id)
            }

            override fun onClickProduct(model: ProductModel) {
                detailCategoryViewModel.navigateToProduct(model)
            }

            override fun onClickMessage(model: ProductModel) {
             createChat(model.id)

            }

            override fun onClickCall(phone: String) {
                navigateToCall(phone)
            }

            override fun onClickAnother(model: ProductModel) {

              showBottomSheet(requireContext(),model, personalOrNot =detailCategoryViewModel.flag.value , list = selections, onClickBottomFragment =this@DetailCategoryFragment)
            }


        }
    }


    private fun createChat(id:String)=detailCategoryViewModel.createChat(id)
    override fun createSelection(product: String) {
        setFragmentResult("name", bundleOf("product" to product))
        detailCategoryViewModel.navigateToCreateSelection()
    }

    override fun addToSelection(id: String, product: String) {
        detailCategoryViewModel.addToSelection(id, product)
    }

    override fun call(username: String) {
        navigateToCall(username)
    }

    override fun createNote(product: ProductModel) {
        setFragmentResult("dataNote", bundleOf("note" to NoteData(id=product.id,
            flag = "product", flagNavigation = "search", isLiked = product.isLiked) ))
        detailCategoryViewModel.navigateToNote()
    }

    override fun chatCreate(id: String) {
        chatCreate(id)
    }

    override fun share(id: String) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, id)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun block() {
        detailCategoryViewModel.navigateToBlock()
    }


}