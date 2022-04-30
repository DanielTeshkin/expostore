package com.expostore.ui.fragment.authorization.registration.completion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.expostore.MainActivity
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.databinding.CompletionFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.authorization.registration.createpassword.toStroke
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Error
/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class CompletionFragment : BaseFragment<CompletionFragmentBinding>(CompletionFragmentBinding::inflate) {

    private val completionViewModel: CompletionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cities = arrayOf("Москва", "Санкт-Петербург", "Казань", "Уфа", "Оренбург")
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
       binding.etCity.setAdapter(adapter)
        completionViewModel.apply {
            subscribe(navigation){navigateSafety(it)}
        }
        binding.imageButton.setOnClickListener {
            completionViewModel.backEnd()
        }
        binding.btnEditProfile.setOnClickListener {
            val editProfileRequestData=EditProfileRequestData(last_name = binding.etSurname.toStroke(), first_name = binding.etName.toStroke(), patronymic =binding.etPatronymic.toStroke(), email = binding.etEmail.toStroke())
            completionViewModel.editProfile(editProfileRequestData)
            completionViewModel.apply {
                singleSubscribe(ui){handleResult(it)}
            }

        }
    }

    private fun handleResult(state: ResponseState<EditProfileResponseData>) {
        when(state){
            is ResponseState.Error->ErrorShow(state.throwable)
        }
    }

    private fun ErrorShow(throwable: Throwable) {
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

    }

    private val profileTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            binding.btnEditProfile.isEnabled =
                binding.etSurname.text.isNotEmpty() && binding.etName.text.isNotEmpty() && binding.etCity.text.isNotEmpty() && binding.etEmail.text.isNotEmpty()
        }
    }
}