package com.expostore.ui.tender.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getfavoriteslist.GetFavoritesListResponseData
import com.expostore.api.pojo.gettenderlist.Tender
import com.expostore.databinding.OpenFragmentBinding
import com.expostore.databinding.TenderListFragmentBinding
import com.expostore.ui.open.OpenViewModel
import com.expostore.utils.FavoritesProductRecyclerViewAdapter
import com.expostore.utils.TenderRecyclerViewAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TenderListFragment : Fragment() {

    private lateinit var binding: TenderListFragmentBinding
    private lateinit var tenderListViewModel: TenderListViewModel
    lateinit var mAdapter: TenderRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.tender_list_fragment, container, false)
        tenderListViewModel = ViewModelProvider(this).get(TenderListViewModel::class.java)
        binding.tenderListVM = tenderListViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val token = (context as MainActivity).sharedPreferences.getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.getTenders("Bearer $token").enqueue(object : Callback<ArrayList<Tender>> {
            override fun onResponse(call: Call<ArrayList<Tender>>, response: Response<ArrayList<Tender>>) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        mAdapter = TenderRecyclerViewAdapter(response.body()!!)
                        binding.rvTenderList.apply {
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = mAdapter
                        }
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }
            override fun onFailure(call: Call<ArrayList<Tender>>, t: Throwable) {}
        })
    }
}