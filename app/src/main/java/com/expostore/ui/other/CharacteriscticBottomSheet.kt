package com.expostore.ui.other

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.FilterBottomSheetBinding
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.general.FilterRecyclerAdapter
import com.expostore.ui.general.FilterState
import com.google.android.material.bottomsheet.BottomSheetDialog

fun showCharacteristics(context:Context, list: List<CategoryCharacteristicModel>, filterState: FilterState, actionSave:()->Unit){
    val binding=FilterBottomSheetBinding.inflate(LayoutInflater.from(context))
    val bottomSheetDialog= BottomSheetDialog(context)
    bottomSheetDialog.setContentView(binding.root)
    val myAdapter= FilterRecyclerAdapter(context,filterState,"other")
    myAdapter.addElement(list)
    binding.rvCharacter.apply {
        layoutManager=LinearLayoutManager(context)
        adapter=myAdapter
    }
    binding.saving.click {
        bottomSheetDialog.hide()
        actionSave.invoke()
    }
    bottomSheetDialog.show()

}