package com.expostore.ui.fragment.profile.profile_edit

import android.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcities.City
import com.expostore.api.pojo.getprofile.EditProfileRequest

import com.expostore.databinding.EditProfileFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<EditProfileFragmentBinding>(EditProfileFragmentBinding::inflate) {
    private val viewModel: EditProfileViewModel by viewModels()
     var i:Int =0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loading.visibility=View.INVISIBLE
        viewModel.getCities()
        binding.apply {
            etName.setText(EditProfileFragmentArgs.fromBundle(requireArguments()).firstName)
            etSurname.setText(EditProfileFragmentArgs.fromBundle(requireArguments()).lastName)
            etCity.setText(EditProfileFragmentArgs.fromBundle(requireArguments()).city)
            etEmail.setText(EditProfileFragmentArgs.fromBundle(requireArguments()).email)
        }
       viewModel.apply {
           subscribe(change){handleResult(it)}
           subscribe(navigation){navigateSafety(it)}
           subscribe(cities){ handleCity(it)}
       }
        binding.btnEditProfile.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                viewModel.citiesList.collect {
                   i = it[binding.etCity.text.toString()]!!
                }
            }
            val request=EditProfileRequest(firstName = binding.etName.text.toString(),
                lastName = binding.etSurname.text.toString(),
                email = binding.etEmail.text.toString(),
                city = i
            )
            viewModel.patchProfile(request)
        }

    }

    private fun handleCity(state: ResponseState<List<City>>) {
        when(state){
            is ResponseState.Success -> loadCities(state.item)
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

    private fun handleResult(state: ResponseState<EditProfileRequest>) {
        when(state){
            is ResponseState.Loading ->  binding.loading.visibility=View.VISIBLE
            is ResponseState.Success-> handleSuccess()
        }


    }

    private fun handleSuccess() {
        binding.loading.visibility=View.INVISIBLE
        viewModel.navigateProfile()
    }
}