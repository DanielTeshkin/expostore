package com.expostore.ui.fragment.tender.my.tabs

import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.PublishedTenderFragmentBinding
import com.expostore.model.tender.TenderModel
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.Show

import com.expostore.ui.fragment.tender.my.MyTenderListAdapter
import com.expostore.ui.fragment.tender.my.OnClickMyTender
import com.expostore.ui.fragment.tender.my.SharedTenderModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.confirm_number_fragment.view.*
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class MyTendersFragment : BaseFragment<PublishedTenderFragmentBinding>(PublishedTenderFragmentBinding::inflate) {

    private val viewModel : MyTendersViewModel by viewModels()
    private val sharedModel:SharedTenderModel by lazy {arguments?.getParcelable("shared")!!  }
    override fun onStart() {
        super.onStart()

        val show : Show<List<TenderModel>> = { showMyTenders(it)}
        viewModel.apply {
            loadMyTenders(sharedModel.status)
            subscribe(myTender){handleState(it,show)}
            subscribe(navigation){navigateSafety(it)}
        }
    }


    private fun showMyTenders(list: List<TenderModel>) {
        binding.barTender.visibility=View.GONE
        val myAdapter=MyTenderListAdapter(list)
        myAdapter.onClickMyTender=initOnClick()
        binding.rvTenders.apply {
            layoutManager = LinearLayoutManager(requireContext())
                    adapter = myAdapter
                }
            }
    private fun initOnClick() = object : OnClickMyTender {
        override fun onClickTender(model: TenderModel) {
               viewModel.navigateToEdit(model)
        }
    }
        }




