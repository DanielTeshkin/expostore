package com.expostore.ui.fragment.search.filter

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.databinding.SearchFilterFragmentBinding
import com.expostore.model.category.*
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
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
class SearchFilterFragment : BaseFragment<SearchFilterFragmentBinding>(SearchFilterFragmentBinding::inflate),CharacteristicState {

    private val viewModel: SearchFilterViewModel by viewModels()
    private val myAdapter: CharacteristicInputRecyclerAdapter by lazy {
        CharacteristicInputRecyclerAdapter(requireContext(),this,"filter")
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        installBundleResultListeners()
        val loadCharacteristics:Show<List<CategoryCharacteristicModel>> = { loadCharacteristic(it)}
        val loadCategories:Show<List<ProductCategoryModel>> = { initBottomCategoriesSheet(it)}
            viewModel.apply {
                start(findNavController())
                subscribe(navigation) { navigateSafety(it) }
                subscribe(cities) { state -> handleState(state){loadCities(it)} }
                subscribe(categories){ handleState(it,loadCategories)}
                subscribe(characteristics){handleState(it,loadCharacteristics)}
            }
         binding.apply {
                 apply.click {
                     viewModel.apply {
                        searchFilter()
                         val result = FilterModel(
                             name = searchEt.text.toString(),
                             price_min = priceMin.text.toString().toIntOrNull(),
                             price_max = priceMax.text.toString().toIntOrNull(),
                             city = etCity.text.toString(),
                             characteristics = filterCharacteristic.value as MutableList<CharacteristicFilterModel>,
                             category = category.value?.id)
                              navigateToSearch(result) }

                }
                select.click { viewModel.navigateToMapChoice() }
             reset.click {   searchEt.text.clear()
                  priceMin.text.clear()
                priceMax.text.clear()
                 etCity.text.toString()
                 viewModel.saveCategory(ProductCategoryModel())
                 myAdapter.removeAll()
             }
             back.click { viewModel.navigateToBack() }


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

    private fun loadCharacteristic(list:List<CategoryCharacteristicModel>) {
        binding.rvFilters.visibility = View.VISIBLE
        myAdapter.addElement(list )
        binding.rvFilters.apply {
            isNestedScrollingEnabled = false
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


    override fun inputListener(left: String, right: String?, name: String) = viewModel.addFilterInput(left,right?:"",name)

    override fun radioListener(id: String, name: String) =viewModel.addFilterRadio(id,name)

    override fun selectListener(id: String, name: String, checked: Boolean) {
        when(checked){
            true-> myAdapter.addSelect(id)
            false->myAdapter.removeSelect(id)
        }
        viewModel.addFilterSelect(name,myAdapter.selectList)
    }

    override fun checkBoxListener(name: String, checked: Boolean) = viewModel.addFilterCheckbox(name,checked)
}

