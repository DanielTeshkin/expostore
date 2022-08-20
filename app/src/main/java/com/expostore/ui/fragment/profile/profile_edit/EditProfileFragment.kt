package com.expostore.ui.fragment.profile.profile_edit

import android.R
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenStarted
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcities.City
import com.expostore.api.pojo.getprofile.EditProfileRequest
import com.expostore.api.response.EditResponseProfile

import com.expostore.databinding.EditProfileFragmentBinding
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.InfoProfileModel
import com.expostore.ui.general.ProfileDataViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class EditProfileFragment : BaseFragment<EditProfileFragmentBinding>(EditProfileFragmentBinding::inflate) {
    private val viewModel: ProfileDataViewModel by viewModels()


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
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<InfoProfileModel>("info")!!
            viewModel.saveData(result)
                binding.apply {
                    etName.setText(result.name)
                    etSurname.setText(result.surname)
                    etCity.setText(result.city)
                    etEmail.setText(result.email)
                }

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
