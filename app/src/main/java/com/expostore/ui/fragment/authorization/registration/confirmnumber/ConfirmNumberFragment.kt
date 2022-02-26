package com.expostore.ui.fragment.authorization.registration.confirmnumber

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.ConfirmNumberFragmentBinding
import com.expostore.ui.base.BaseFragment

class ConfirmNumberFragment :
    BaseFragment<ConfirmNumberFragmentBinding>(ConfirmNumberFragmentBinding::inflate) {

    private lateinit var confirmNumberViewModel: ConfirmNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmNumberViewModel = ViewModelProvider(this).get(ConfirmNumberViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSignInNext.setOnClickListener {
            confirmNumberViewModel.confirmNumber(it, binding.etNumber.text.toString())
        }

        confirmNumberViewModel.context = requireContext()

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