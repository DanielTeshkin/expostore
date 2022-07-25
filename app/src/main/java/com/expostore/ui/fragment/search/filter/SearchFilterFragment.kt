package com.expostore.ui.fragment.search.filter

import android.R
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getcities.City
import com.expostore.databinding.SearchFilterFragmentBinding
import com.expostore.model.category.*
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.fragment.search.filter.adapter.FilterRecyclerAdapter
import com.expostore.ui.fragment.search.filter.adapter.utils.FilterState
import com.expostore.ui.fragment.search.filter.models.FilterModel
import com.expostore.ui.fragment.specifications.CategoryChose
import com.expostore.ui.fragment.specifications.DataModel
import com.expostore.ui.fragment.specifications.showBottomSpecifications
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SearchFilterFragment :
    BaseFragment<SearchFilterFragmentBinding>(SearchFilterFragmentBinding::inflate) {

    private val viewModel: SearchFilterViewModel by viewModels()
    private lateinit var myAdapter: FilterRecyclerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installBundleResultListeners()
        binding.category.click {
            setFragmentResult("requestKey", bundleOf("flag" to "filter"))
            binding.apply {
                val data = DataModel(
                    name = searchEt.text.toString(),
                    priceMin = priceMin.text.toString(),
                    priceMax = priceMax.text.toString(),
                    city = etCity.text.toString(),
                    flag = viewModel.flag.value
                )
                setFragmentResult("key", bundleOf("data" to data))


                     //viewModel.navigateToCategory()
            }

        }

        val load: Show<List<City>> = { loadCities(it) }
            val loadCharacteristics:Show<List<CategoryCharacteristicModel>> = { loadCharacteristic(it)}
        val loadCategories:Show<List<ProductCategoryModel>> = { initBottomCategoriesSheet(it)}
            viewModel.apply {
                getCities()
                subscribe(navigation) { navigateSafety(it) }
                subscribe(cities) { handleState(it, load) }
                subscribe(categories){ handleState(it,loadCategories)}
                    subscribe(characteristics){handleState(it,loadCharacteristics)}
            }

            binding.apply {
                apply.click {
                    viewModel.apply {
                        searchFilter()
                        state {
                            filterCharacteristic.collect {
                                val result = FilterModel(
                                    name = searchEt.text.toString(),
                                    price_min = priceMin.text.toString().toIntOrNull(),
                                    price_max = priceMax.text.toString().toIntOrNull(),
                                    city = etCity.text.toString(),
                                     characteristics = it as MutableList<CharacteristicFilterModel>,
                                    category = category.value?.id

                                )

                                setFragmentResult("requestKey", bundleOf("filters" to result))
                                navigateToSearch()

                            }
                        }
                    }

                }
            }
        }

    override fun onStart() {
        super.onStart()
        binding.apply {
            saving.click {
                viewModel.saveSearch(searchEt.text.toString(),
                    etCity.text.toString(),
                        priceMin.text.toString().toIntOrNull(),
                   priceMax.text.toString().toIntOrNull(),

                )
            }
        }
        viewModel.getListCategories()
    }

        private   fun installBundleResultListeners() {

                // setFragmentResultListener("category") { _, bundle ->
                  // val result = bundle.getParcelable<ProductCategoryModel>("intent")
                  // if (result != null) {
                  //     viewModel.apply {
                      //     saveCategory(result)
                        //   getCategoryCharacteristic(result.id)
                      // }
                  // } }
              // setFragmentResultListener("new_key") { _, bundle ->
                //   val result = bundle.getParcelable<DataModel>("info")!!
               //    viewModel.saveFlag(result.flag)
               //    binding.etCity.setText(result.city)
               //    binding.searchEt.setText(result.name)
                //   binding.priceMin.setText(result.priceMin)
                  // binding.priceMax.setText(result.priceMax)
              // }

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

    private fun loadCharacteristic(list:List<CategoryCharacteristicModel>) {
        binding.rvFilters.visibility = View.VISIBLE
        val filterState=initFilterState()
        myAdapter = FilterRecyclerAdapter(requireContext(),filterState,"filter")

        myAdapter.addElement(list )
        binding.rvFilters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = myAdapter

        }


    }
    private  fun initBottomCategoriesSheet(list:List<ProductCategoryModel>){
        binding.category.click {
            val categoryChose: CategoryChose = { viewModel.saveCategory(it) }
            showBottomSpecifications(
                context = requireContext(),
                categoryChose = categoryChose,
                list = list
            )
        }
    }



   private fun initFilterState() : FilterState{
        return  object :FilterState{
            override fun inputListener(left: String, right: String?, name: String) {
                Log.i("input",name)
                viewModel.addFilterInput(left,right?:"",name)
            }

            override fun radioListener(id: String, name: String) {
                Log.i("radio",name)
                viewModel.addFilterRadio(id,name)
            }

            override fun selectListener(id:String,name: String,checked: Boolean) {
                Log.i("select",name)
                when(checked){
                    true-> myAdapter.addSelect(id)
                    false->myAdapter.removeSelect(id)
                }
               viewModel.addFilterSelect(name,myAdapter.selectList)
            }

            override fun checkBoxListener(name: String, checked: Boolean) {
                Log.i("check",checked.toString())
                viewModel.addFilterCheckbox(name,checked)
            }

        }
    }
}

