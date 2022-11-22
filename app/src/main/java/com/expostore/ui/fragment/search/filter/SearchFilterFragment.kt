package com.expostore.ui.fragment.search.filter

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.databinding.SearchFilterFragmentBinding
import com.expostore.model.category.*
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.base.fragments.CharacteristicsFragment
import com.expostore.ui.base.fragments.Show
import com.expostore.ui.fragment.profile.profile_edit.click

import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import com.expostore.ui.general.CharacteristicInputRecyclerAdapter
import com.expostore.ui.general.CharacteristicState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SearchFilterFragment(): CharacteristicsFragment<SearchFilterFragmentBinding>(SearchFilterFragmentBinding::inflate){
     override val viewModel: SearchFilterViewModel by viewModels()
     override val filter: String="filter"
     override val categoriesLayout: LinearLayout
     get()= binding.category
     override val characteristicsLayout: LinearLayout?
     get() =null
     override val rvCharacteristics: RecyclerView
         get()=binding.rvFilters
     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installBundleResultListeners()
        binding.apply {
                 apply.click {
                     viewModel.apply {
                        searchFilter()
                       getFiltersAndNavigateToSearch(
                             name = searchEt.text.toString(),
                             priceMin = priceMin.text.toString().toIntOrNull(),
                             priceMax = priceMax.text.toString().toIntOrNull(),
                             city = etCity.text.toString(),
                             )

                     }

                }
                select.click { viewModel.navigateToMapChoice() }
             reset.click {   searchEt.text.clear()
                  priceMin.text.clear()
                priceMax.text.clear()
                 etCity.text.toString()
                // viewModel.saveCategory("")
                 clear()


             }
             back.click { viewModel.navigateToBack() }


            }
        }

    override fun onStart() {
        super.onStart()
        subscribe(viewModel.cities) { state -> handleState(state){loadCities(it)} }
        binding.apply {
            saving.click {
                viewModel.saveSearch(searchEt.text.toString(),
                    etCity.text.toString(),
                        priceMin.text.toString().toIntOrNull(),
                   priceMax.text.toString().toIntOrNull(),
                    )
            }
        }

    }

        private   fun installBundleResultListeners() {
            setFragmentResultListener("new_key") { _, bundle ->
                   val result = bundle.getParcelable<DataModel>("info")!!
                   viewModel.saveFlag(result.flag)
                   binding.etCity.setText(result.city)
                   binding.searchEt.setText(result.name)
                   binding.priceMin.setText(result.priceMin)
                   binding.priceMax.setText(result.priceMax)
               }

           }


    private fun loadCities(item: List<City>) {
        val list = ArrayList<String>()
        item.map { list.add(it.mapCity.first) }
        val array = list.toTypedArray()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_dropdown_item_1line, array)
        binding.etCity.setAdapter(adapter)
        viewModel.saveCities(item)

    }
}

