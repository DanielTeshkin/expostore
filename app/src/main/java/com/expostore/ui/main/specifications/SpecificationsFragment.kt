package com.expostore.ui.main.specifications

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.R
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.databinding.SpecificationsFragmentBinding
import com.expostore.utils.SpecificationsRecyclerViewAdapter

class SpecificationsFragment : Fragment() {

    private lateinit var binding: SpecificationsFragmentBinding
    private lateinit var specificationsViewModel: SpecificationsViewModel
    private lateinit var mAdapter: SpecificationsRecyclerViewAdapter
    private lateinit var specifications: ArrayList<ProductCategory>
     var categories: ArrayList<String> = arrayListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.specifications_fragment, container, false)
        specificationsViewModel = ViewModelProvider(this).get(SpecificationsViewModel::class.java)
        binding.specificationsVM = specificationsViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        specifications = arguments?.getParcelableArrayList<ProductCategory>("specifications") as ArrayList<ProductCategory>
        mAdapter = SpecificationsRecyclerViewAdapter(specifications)
        binding.rvSpecifications.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.onClick = addCategory()
        mAdapter.notifyDataSetChanged()
    }

    private fun addCategory(): SpecificationsRecyclerViewAdapter.OnClickListener {
        return object : SpecificationsRecyclerViewAdapter.OnClickListener {
            override fun addCategory(isChecked: Boolean, id: String?) {
                if (!id.isNullOrEmpty()){
                    if (isChecked)
                        categories.add(id)
                    else categories.remove(id)
                }
                specificationsViewModel.categories = categories
            }
        }
    }
}