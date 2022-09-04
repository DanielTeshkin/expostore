package com.expostore.ui.fragment.authorization.registration.completion

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.databinding.CompletionFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Show
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.ui.general.ProfileDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class CompletionFragment : BaseFragment<CompletionFragmentBinding>(CompletionFragmentBinding::inflate) {

    private val completionViewModel: ProfileDataViewModel by viewModels()
   private val load: Show<List<City>> = { loadCities(it) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeStartRequest()
        completionViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
            subscribe(cities){handleState(it,load)}
            subscribe(ui){handleState(it)}
        }
    }


    override fun onStart() {
        super.onStart()
        initButtons()
        addChangeTextListeners()
    }


    private fun makeStartRequest()=completionViewModel.getCities()
    private fun loadCities(item: List<City>) {
        val list = ArrayList<String>()
        item.map { list.add(it.mapCity.first) }
        val array = list.toTypedArray()
        val adapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_dropdown_item_1line, array)
        binding.etCity.setAdapter(adapter)
        completionViewModel.saveCities(item)

    }

    private fun addChangeTextListeners(){
        binding.apply {
            etCity.addTextChangedListener { completionViewModel.updateCity(it.toString()) }
            etName.addTextChangedListener {completionViewModel.updateName(it.toString())  }
            etSurname.addTextChangedListener { completionViewModel.updateSurname(it.toString()) }
            etEmail.addTextChangedListener{completionViewModel.updateEmail(it.toString())}
            etPatronymic.addTextChangedListener { completionViewModel.updatePatronymic(it.toString()) }
        }
    }
    private fun initButtons(){
        binding.imageButton.click { completionViewModel.backEnd()}
        binding.btnEditProfile.apply {
            state { completionViewModel.enabledState.collect { isEnabled=it }}
            click { completionViewModel.editProfile() }
        }
    }

}