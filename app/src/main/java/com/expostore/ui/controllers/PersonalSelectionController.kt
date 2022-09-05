package com.expostore.ui.controllers

import android.content.Context
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.DetailPersonalSelectionFragmentBinding
import com.expostore.model.category.SelectionModel
import com.expostore.ui.fragment.category.personal.DetailPersonalSelectionFragment
import com.expostore.ui.fragment.profile.profile_edit.click

class PersonalSelectionController(private val context:Context,private val
                                  binding:DetailPersonalSelectionFragmentBinding,action:()->Unit,action2:()->Unit) :BaseProductController()
{
    init {
        val popupMenu= PopupMenu(context,binding.menu)
        popupMenu.inflate(R.menu.menu_selection)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.edit -> action.invoke()
                R.id.delete -> action2.invoke()
            }
            false
        }
        binding.menu.click {
            popupMenu.show()
        }
    }
    fun showUI(model: SelectionModel) {
        products.addAll(model.products)
        binding.apply {
            tvCategoryName.text=model.name
            rvDetailProduct.apply {
                layoutManager= LinearLayoutManager(context)
                adapter=mAdapter
            }
        }

    }
}