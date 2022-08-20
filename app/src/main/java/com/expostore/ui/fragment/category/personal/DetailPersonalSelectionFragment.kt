package com.expostore.ui.fragment.category.personal

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.R
import com.expostore.databinding.DetailPersonalSelectionFragmentBinding
import com.expostore.model.product.ProductModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.general.other.OnClickBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailPersonalSelectionFragment : BaseFragment<DetailPersonalSelectionFragmentBinding>(DetailPersonalSelectionFragmentBinding::inflate),OnClickBottomSheetFragment {
    override fun createSelection(product: String) {
        TODO("Not yet implemented")
    }

    override fun addToSelection(id: String, product: String) {
        TODO("Not yet implemented")
    }

    override fun call(username: String) {
        TODO("Not yet implemented")
    }

    override fun createNote(product: ProductModel) {
        TODO("Not yet implemented")
    }

    override fun chatCreate(id: String) {
        TODO("Not yet implemented")
    }

    override fun share(id: String) {
        TODO("Not yet implemented")
    }

    override fun block() {
        TODO("Not yet implemented")
    }


}