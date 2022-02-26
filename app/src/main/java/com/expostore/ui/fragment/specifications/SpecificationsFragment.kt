package com.expostore.ui.fragment.specifications

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.databinding.SpecificationsFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.utils.SpecificationsRecyclerViewAdapter

class SpecificationsFragment :
    BaseFragment<SpecificationsFragmentBinding>(SpecificationsFragmentBinding::inflate) {

    private lateinit var specificationsViewModel: SpecificationsViewModel
    private lateinit var mAdapter: SpecificationsRecyclerViewAdapter
    private lateinit var specifications: ArrayList<ProductCategory>
    var categories: ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        specificationsViewModel = ViewModelProvider(this).get(SpecificationsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSave.setOnClickListener {
            specificationsViewModel.saveCategories(it)
        }

        specifications =
            arguments?.getParcelableArrayList<ProductCategory>("specifications") as ArrayList<ProductCategory>
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
                if (!id.isNullOrEmpty()) {
                    if (isChecked)
                        categories.add(id)
                    else categories.remove(id)
                }
                specificationsViewModel.categories = categories
            }
        }
    }
}