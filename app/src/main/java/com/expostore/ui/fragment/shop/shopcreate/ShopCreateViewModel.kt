package com.expostore.ui.fragment.shop.shopcreate

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import androidx.lifecycle.ViewModel

class ShopCreateViewModel : ViewModel() {

    var name: String = ""
    var address: String = ""
    var shoppingCenter: String = ""
    var office: String = ""

    @SuppressLint("StaticFieldLeak")
    var btnSave: Button?= null

    val shopCreateTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (name.isNotEmpty() && address.isNotEmpty() && shoppingCenter.isNotEmpty() && office.isNotEmpty()) {
                btnSave!!.isEnabled = true
            }
        }
    }

    fun createShop(view: View){

    }

}