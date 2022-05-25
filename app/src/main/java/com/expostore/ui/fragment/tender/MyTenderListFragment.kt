package com.expostore.ui.fragment.tender

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.databinding.MyDraftFragmentBinding
import com.expostore.databinding.MyTenderListFragmentBinding
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.product.myproducts.OnClickMyProduct
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyTenderListFragment : BaseFragment<MyTenderListFragmentBinding>(MyTenderListFragmentBinding::inflate) {
        private val viewModel :MyTenderListViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val show : Show<List<TenderModel>> = { showMyTenders(it)}
        viewModel.apply {
            loadMyTenders()
            subscribe(myTender){handleState(it,show)}
            subscribe(navigation){navigateSafety(it)}
        }
    }






    private fun showMyTenders(list: List<TenderModel>) {

         binding.rvMyTenders.apply {
             layoutManager=LinearLayoutManager(requireContext())
             adapter=MyTenderListAdapter(list) }
    }

}