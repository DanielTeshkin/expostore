package com.expostore.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.expostore.R
import com.expostore.databinding.*
import com.expostore.extension.sp
import com.expostore.model.category.CategoryCharacteristicModel
import com.expostore.model.category.CharacteristicType
import com.google.android.material.chip.Chip

/**
 * @author Fedotov Yakov
 */
class FilterListView(context: Context) : LinearLayout(context) {

    var selectItemClockListener: ((CategoryCharacteristicModel) -> Unit)? = null

    var list: List<CategoryCharacteristicModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private fun notifyDataSetChanged() {
        list.forEach {
            when (it.type) {
                CharacteristicType.SINGLE_INPUT -> addSingleInputItem(it)
                CharacteristicType.DOUBLE_INPUT -> addDoubleInputItem(it)
                CharacteristicType.SELECT -> addSelectItem(it)
                CharacteristicType.CHECKBOX -> addCheckboxItem(it)
                CharacteristicType.RADIO -> addRadioItem(it)
                else -> {
                    /* no-op */
                }
            }
        }
    }

    private fun addSingleInputItem(item: CategoryCharacteristicModel) {
        val singleInputItem =
            SingleInptutItemBinding.inflate(LayoutInflater.from(context), this, true).apply {
                title.text = item.name
                start.hint = item.fieldName
            }
        addView(singleInputItem.root)
    }

    private fun addDoubleInputItem(item: CategoryCharacteristicModel) {
        val doubleInputItem =
            DoubleInptutItemBinding.inflate(LayoutInflater.from(context), this, true).apply {
                title.text = item.name
                start.hint = item.leftName
                end.hint = item.rightName
            }
        addView(doubleInputItem.root)
    }

    private fun addSelectItem(item: CategoryCharacteristicModel) {
        val singleInputItem =
            SelectItemBinding.inflate(LayoutInflater.from(context), this, true).apply {
                select.text = item.name
                root.setOnClickListener { selectItemClockListener?.invoke(item) }
            }
        addView(singleInputItem.root)
    }

    private fun addCheckboxItem(item: CategoryCharacteristicModel) {
        val checkBoxItem =
            CheckBoxItemBinding.inflate(LayoutInflater.from(context), this, true).apply {
                checkbox.text = item.name
            }
        addView(checkBoxItem.root)
    }

    private fun addRadioItem(item: CategoryCharacteristicModel) {
        val radioItem =
            RadioItemBinding.inflate(LayoutInflater.from(context), this, true).apply {
                title.text = item.name
                item.listValue.forEach { chipGroup.addView(createTagChip(it)) }
            }
        addView(radioItem.root)
    }

    private fun createTagChip(chipName: String): Chip =
        Chip(context, null, R.style.Widget_MaterialComponents_Chip_Choice).apply {
            text = chipName
            setChipBackgroundColorResource(R.color.white)
            setTextColor(ContextCompat.getColor(context, R.color.white))
            textSize = 16.sp
        }
}