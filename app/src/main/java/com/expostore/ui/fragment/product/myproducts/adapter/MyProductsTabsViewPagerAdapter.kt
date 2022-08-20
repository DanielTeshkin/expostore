package com.expostore.ui.fragment.product.myproducts.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expostore.R
import com.expostore.ui.fragment.product.myproducts.OnClickMyProduct
import com.expostore.ui.fragment.product.myproducts.tabs.TabMyProductFragment
import kotlinx.android.synthetic.main.favorites_tab_item.view.text
import kotlinx.android.synthetic.main.my_products_tab_item.view.*

class MyProductsTabsViewPagerAdapter(
    val fragment: Fragment,
    val context: Context,


): FragmentStateAdapter(fragment) {

    private val tabs = listOf("Опубликованы", "Черновики", "Архив")
    private val icons = listOf(R.drawable.ic_my_products_published,R.drawable.ic_my_products_draft,R.drawable.ic_my_products_archive)

    override fun getItemCount(): Int = tabs.size

    @SuppressLint("InflateParams")
    fun getTabView(position: Int): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.my_products_tab_item, null)

        view.text.text = tabs[position]
        view.icon.setImageResource(icons[position])

        return view
    }

    override fun createFragment(position: Int): Fragment {
        val result: Fragment =TabMyProductFragment()
        val bundle=Bundle()


        when(position){
            0 -> {
                bundle.putParcelable("shared",SharedMyProductsModel("my"))
                result.arguments=bundle
            }
            1 -> {
                bundle.putParcelable("shared",SharedMyProductsModel("draft"))
                result.arguments=bundle
            }
            2 -> {
                bundle.putParcelable("shared",SharedMyProductsModel("archive"))
                result.arguments=bundle
            }
        }
        return result!!
    }
}