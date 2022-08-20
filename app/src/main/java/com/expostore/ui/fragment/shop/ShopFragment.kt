package com.expostore.ui.fragment.shop

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.expostore.api.pojo.getshop.GetShopResponseData
import com.expostore.api.pojo.selectfavorite.SelectFavoriteResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.ShopFragmentBinding
import com.expostore.extension.load
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.model.product.ProductModel
import com.expostore.model.product.ShopModel
import com.expostore.model.product.toModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.category.OnClickListener
import com.expostore.ui.fragment.category.ProductSelectionAdapter
import com.expostore.ui.fragment.chats.chatsId
import com.expostore.ui.fragment.chats.identify
import com.expostore.ui.fragment.chats.imagesProduct
import com.expostore.ui.fragment.chats.productsName
import com.expostore.ui.fragment.note.NoteData
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import com.expostore.ui.general.other.showBottomSheet
import com.expostore.utils.DetailCategoryRecyclerViewAdapter
import com.expostore.utils.OnClickRecyclerViewListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
@AndroidEntryPoint
class ShopFragment : BaseFragment<ShopFragmentBinding>(ShopFragmentBinding::inflate) {

    private val shopViewModel: ShopViewModel by viewModels()
    private val products = mutableListOf<ProductModel>()
    private val mAdapter: ProductSelectionAdapter by lazy {
        ProductSelectionAdapter(products)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFragmentResultListener("shop") { _, bundle ->
            val info = bundle.getString("model")
            if (info != null) {
                shopViewModel.getShop(info)
            }
        }
        val setupInfoShop: Show<GetShopResponseData> = { setupInfoShop(it) }
        val writeMessage:Show<MainChat> = { chatOpen(it)}
        shopViewModel.apply {
            subscribe(shop) { handleState(it, setupInfoShop) }
            subscribe(chatCreateUI){handleState(it,writeMessage)}
            subscribe(navigation){navigateSafety(it)}
        }
    }

    private fun setupInfoShop(data: GetShopResponseData) {
        binding.apply {
            tvShopName.text = data.name
            tvShopAddress.text = data.address
            tvShopShoppingCenter.text = data.shoppingCenter
            ivAvatar.load(data.image)
            ivBackground.load(data.image)
            products.addAll(data.products.map { it.toModel })
            mAdapter.onClick =initClickListener(false)
                rvShopProducts.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = mAdapter
                }
        }
    }
   private fun chatOpen(chat: MainChat){
        val result = InfoItemChat(
            chat.identify()[1],
            chat.identify()[0],
            chat.chatsId(),
            chat.imagesProduct(),
            chat.productsName(), chat.identify()[3]
        )
        setFragmentResult("new_key", bundleOf("info" to result))
    }

    private fun initClickListener(state:Boolean): OnClickListener {
        return  object : OnClickListener {
            override fun onClickLike(id: String) {
                shopViewModel.updateSelected(id)
            }

            override fun onClickProduct(model: ProductModel) {
                shopViewModel.navigateToProduct(model)
            }

            override fun onClickMessage(model: ProductModel) {
                shopViewModel.createChat(model.id)
            }

            override fun onClickCall(phone: String) {
                navigateToCall(phone)
            }

            override fun onClickAnother(model: ProductModel) {
                state {
                    shopViewModel.getSelections().collect {
                        showBottomSheet(
                            requireContext(),
                            model,
                            personalOrNot = state,
                            list = it,
                            onClickBottomFragment = initPersonalSelectionCLick()
                        )
                    }
                }
            }

        }
    }
    private fun initPersonalSelectionCLick(): OnClickBottomSheetFragment {
        return object : OnClickBottomSheetFragment {
            override fun createSelection(product: String) {
                setFragmentResult("name", bundleOf("product" to product))
                shopViewModel.navigateToCreateSelection()
            }

            override fun addToSelection(id: String, product: String) {
                Log.i("add","dddd")
                shopViewModel.addToSelection(id, product)
            }

            override fun call(username: String) {
                navigateToCall(username)
            }

            override fun createNote(model: ProductModel) {
                setFragmentResult("dataNote", bundleOf("note" to NoteData(id=model.id,
                    flag = "product", flagNavigation = "search", isLiked = model.isLiked) ))
                shopViewModel.navigateToNote()
            }

            override fun chatCreate(id: String) {
                shopViewModel.createChat(id)
            }

            override fun share(id:String) {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, id)
                    type = "text/plain"
                }

                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            override fun block() {
                Log.i("add","dddd")
            }

        }
    }

}