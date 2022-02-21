package com.expostore.ui.authorization.registration.completion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.MainActivity
import com.expostore.databinding.CompletionFragmentBinding
import com.expostore.ui.base.BaseFragment


class CompletionFragment : BaseFragment<CompletionFragmentBinding>(CompletionFragmentBinding::inflate) {

    private lateinit var completionViewModel: CompletionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        completionViewModel = ViewModelProvider(this).get(CompletionViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        completionViewModel.context = requireContext()

        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        binding.etSurname.addTextChangedListener(profileTextWatcher)
        binding.etName.addTextChangedListener(profileTextWatcher)
        binding.etCity.addTextChangedListener(profileTextWatcher)
        binding.etEmail.addTextChangedListener(profileTextWatcher)

        binding.btnEditProfile.isEnabled = false

        completionViewModel.etCity = binding.etCity

        completionViewModel.getCities()
//        val cities = arrayOf("Москва", "Санкт-Петербург", "Казань", "Уфа", "Оренбург")
//        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, cities)
//        binding.etCity.setAdapter(adapter)
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