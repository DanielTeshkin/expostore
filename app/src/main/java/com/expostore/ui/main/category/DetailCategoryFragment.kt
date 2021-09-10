package com.expostore.ui.main.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getcategory.CategoryProduct
import com.expostore.databinding.DetailCategoryFragmentBinding
import com.expostore.databinding.MainFragmentBinding
import com.expostore.ui.main.MainViewModel
import com.expostore.utils.CategoryRecyclerViewAdapter
import com.expostore.utils.DetailCategoryRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailCategoryFragment : Fragment() {

    private lateinit var binding: DetailCategoryFragmentBinding
    private lateinit var detailCategoryViewModel: DetailCategoryViewModel
    private lateinit var mAdapter: DetailCategoryRecyclerViewAdapter
    lateinit var info: Category

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.detail_category_fragment, container, false)
        detailCategoryViewModel = ViewModelProvider(this).get(DetailCategoryViewModel::class.java)
        binding.detailCategoryVM = detailCategoryViewModel

        (context as AppCompatActivity).bottomNavigationView.visibility = View.VISIBLE

        info = arguments?.getSerializable("category") as Category
        binding.tvCategoryName.text = info.name

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mAdapter = DetailCategoryRecyclerViewAdapter(info.products, requireContext())
        binding.rvDetailProduct.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }
        mAdapter.notifyDataSetChanged()
    }

}