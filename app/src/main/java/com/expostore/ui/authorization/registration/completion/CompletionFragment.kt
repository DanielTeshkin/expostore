package com.expostore.ui.authorization.registration.completion

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.CompletionFragmentBinding


class CompletionFragment : Fragment() {

    private lateinit var binding: CompletionFragmentBinding
    private lateinit var completionViewModel: CompletionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.completion_fragment, container, false)
        completionViewModel = ViewModelProvider(this).get(CompletionViewModel::class.java)
        binding.completionVM = completionViewModel

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

        return binding.root
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