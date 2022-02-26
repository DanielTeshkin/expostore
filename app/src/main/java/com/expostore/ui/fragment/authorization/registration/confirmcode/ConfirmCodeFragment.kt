package com.expostore.ui.fragment.authorization.registration.confirmcode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.ConfirmCodeFragmentBinding
import com.expostore.ui.base.BaseFragment

class ConfirmCodeFragment : BaseFragment<ConfirmCodeFragmentBinding>(ConfirmCodeFragmentBinding::inflate) {

    private lateinit var confirmCodeViewModel: ConfirmCodeViewModel
    var number: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        confirmCodeViewModel = ViewModelProvider(this).get(ConfirmCodeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSignInNext.setOnClickListener {
            confirmCodeViewModel.confirmCode(it, binding.etNumber.text.toString())
        }

        confirmCodeViewModel.phoneInput = arguments?.getString("numberInput")
        confirmCodeViewModel.context = requireContext()

        confirmCodeViewModel.timer.start()

        //Отключение нижней навигации для фрагмента
        (context as MainActivity).binding.bottomNavigationView.visibility = View.GONE

        // Подключение наблюдателя текста
        binding.btnSignInNext.isEnabled = false
        binding.etNumber.addTextChangedListener(codeTextWatcher)

        confirmCodeViewModel.btnResendCode = binding.btnResendCode

        //Добавление сохраненного номера в подзаголовок
        number = arguments?.getString("numberText")
        if (!number.isNullOrEmpty())
            binding.subtitle.text = getString(R.string.confirm_number_subtitle_text, number)

    }

    // Наблюдатель текста для кода
    private val codeTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Если длина кода равна 6 символам, то кнопка "Продолжить" включена
            binding.btnSignInNext.isEnabled = binding.etNumber.length() == 6
        }
    }
}