package com.expostore.ui.fragment.specifications.adapter.holder

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.expostore.R
import com.expostore.databinding.CategoryMultiItemBinding
import com.expostore.databinding.CategorySingleItemBinding
import com.expostore.ui.fragment.specifications.CategoryChose
import com.google.android.material.bottomsheet.BottomSheetDialog

class TypeHolder(
    private val context: Context,
    private val onClickSelectionCategory: CategoryChose,
   private val bottomSheetDialog: BottomSheetDialog
) {
    fun getType(have_child: Boolean):Int =when(have_child){
        false -> R.layout.category_single_item
        true-> R.layout.category_multi_item
    }

    fun createHolder( type:Int,parent: ViewGroup):SpecificationViewHolder{
     return   if(type==R.layout.category_single_item){
         SingleSpecificationHolder(CategorySingleItemBinding
             .inflate(LayoutInflater.from(parent.context),parent,false),
             context,onClickSelectionCategory,bottomSheetDialog
         )
     }
         else{ MultiSpecificationHolder(CategoryMultiItemBinding.inflate(LayoutInflater.from(parent.context),parent,false),context,onClickSelectionCategory,bottomSheetDialog)}
    }
}