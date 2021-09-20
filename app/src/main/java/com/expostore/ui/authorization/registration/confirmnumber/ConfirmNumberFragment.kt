package com.expostore.ui.authorization.registration.confirmnumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.expostore.R
import com.expostore.databinding.ConfirmNumberFragmentBinding

class ConfirmNumberFragment : Fragment() {

    private lateinit var binding: ConfirmNumberFragmentBinding
    private lateinit var confirmNumberViewModel: ConfirmNumberViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.confirm_number_fragment, container, false)
        confirmNumberViewModel = ViewModelProvider(this).get(ConfirmNumberViewModel::class.java)
        binding.confirmNumberVM = confirmNumberViewModel
        confirmNumberViewModel.context = requireContext()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Передача параметров во вьюмодель
        confirmNumberViewModel.numberBtn = binding.btnSignInNext
        confirmNumberViewModel.numberEt = binding.etNumber

        // Подключение и отображение ссылок правил пользования
        confirmNumberViewModel.setupClickableTextView(binding.tvNumberRules)

        // Подключение наблюдателя текста
        binding.etNumber.addTextChangedListener(confirmNumberViewModel.numberTextWatcher)

        // Биндинг кнопки очистки текста
        binding.tilNumber.setEndIconOnClickListener {
            binding.etNumber.text = null
        }
    }
}