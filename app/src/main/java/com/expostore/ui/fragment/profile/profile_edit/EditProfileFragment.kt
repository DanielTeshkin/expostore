package com.expostore.ui.fragment.profile.profile_edit

import android.R
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.expostore.data.remote.api.pojo.getcities.City
import com.expostore.data.remote.api.response.EditResponseProfile

import com.expostore.databinding.EditProfileFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.general.ProfileDataViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<EditProfileFragmentBinding>(EditProfileFragmentBinding::inflate) {
    private val viewModel: ProfileDataViewModel by viewModels()
    override var isBottomNavViewVisible: Boolean= false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCities()
        subscribeViewModel()
    }

    override fun onStart() {
        super.onStart()
        completion()
        subscribeOnChange()
        binding.apply { btnEditProfile.click { viewModel.patchProfile() } }
    }

 private fun subscribeOnChange(){
        viewModel.run {
            binding.apply {
                etName.addTextChangedListener { updateName(it.toString()) }
                etEmail.addTextChangedListener { updateEmail(it.toString()) }
                etCity.addTextChangedListener { updateCity(it.toString()) }
                etSurname.addTextChangedListener { updateSurname(it.toString()) }
                state { enabledState.collect { btnEditProfile.isEnabled=it } }
            }
        }
    }

    private fun completion(){
        val result=EditProfileFragmentArgs.fromBundle(requireArguments()).info
        viewModel.saveData(result)
                binding.apply {
                    etName.setText(result.name)
                    etSurname.setText(result.surname)
                    etCity.setText(result.city)
                    etEmail.setText(result.email)
                }
    }
    private fun subscribeViewModel(){
        viewModel.apply {
            subscribe(change){handleResult(it)}
            subscribe(navigation){navigateSafety(it)}
            subscribe(cities){ handleCity(it)}
        }
    }



    private fun handleCity(state: ResponseState<List<City>>) {
        when(state){
            is ResponseState.Success -> loadCities(state.item)
            is ResponseState.Error->Toast.makeText(requireContext(),state.throwable.message,Toast.LENGTH_SHORT).show()
        }

    }

    private fun loadCities(item: List<City>) {
        val list=ArrayList<String>()
        item.map { list.add(it.mapCity.first) }
        val array =list.toTypedArray()
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), R.layout.simple_dropdown_item_1line, array)
        binding.etCity.setAdapter(adapter)
        viewModel.saveCities(item)
    }

    private fun handleResult(state: ResponseState<EditResponseProfile>) {
        when(state){
            is ResponseState.Loading ->  binding.loading.visibility=View.VISIBLE
            is ResponseState.Success-> handleSuccess()
            is ResponseState.Error->Toast.makeText(requireContext(),state.throwable.message,Toast.LENGTH_SHORT).show()
        }


    }

    private fun handleSuccess() {
        binding.loading.visibility=View.INVISIBLE
        viewModel.navigateProfile()
    }
}


fun View.click(action:()->Unit){
    setOnClickListener {
        action.invoke()
    }
}
fun View.selectListener(action:(Boolean)->Unit){
    setOnClickListener {
       it.isSelected=!it.isSelected
        action.invoke(it.isSelected)
    }
}
