package com.expostore.ui.fragment.specifications.adapter.holder

import android.content.Context
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.CategoryMultiItemBinding
import com.expostore.model.category.ProductCategoryModel
import com.expostore.ui.fragment.profile.profile_edit.selectListener
import com.expostore.ui.fragment.specifications.adapter.SpecificationsRecyclerViewAdapter
import com.expostore.ui.fragment.specifications.adapter.utils.OnClickSelectionCategory

class MultiSpecificationHolder( val binding:CategoryMultiItemBinding,
                                val context: Context,
                                val onClickSelectionCategory: OnClickSelectionCategory) :SpecificationViewHolder(binding.root,context,onClickSelectionCategory) {

    override fun bind(item: ProductCategoryModel) {
        binding.apply {
            tvAddProductConnectionsName.text = item.name
            rvChilds.visibility= View.GONE
            tvAddProductConnectionsName.selectListener {
                when(it){
                    true -> {
                        rvChilds.visibility= View.VISIBLE
                        rvChilds.apply {
                            layoutManager=LinearLayoutManager(context)
                            adapter=SpecificationsRecyclerViewAdapter(item.child_category!!,context,onClickSelectionCategory!!)
                        }
                    }
                    false -> rvChilds.visibility=View.GONE
                }
            }
        }
    }


}