package com.expostore.ui.fragment.authorization.registration.confirmcode

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.databinding.ConfirmCodeFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ConfirmCodeFragment : BaseFragment<ConfirmCodeFragmentBinding>(ConfirmCodeFragmentBinding::inflate) {

    private  val confirmCodeViewModel: ConfirmCodeViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val phone=ConfirmCodeFragmentArgs.fromBundle(requireArguments()).phone
        binding.subtitle.text = getString(R.string.confirm_number_subtitle_text, phone)
        binding.imageButton.setOnClickListener {
            confirmCodeViewModel.back()
        }
        binding.btnSignInNext.setOnClickListener {
            confirmCodeViewModel.confirmCode(phone,binding.etNumber.text.toString())
            confirmCodeViewModel.apply {
                singleSubscribe(navigation) { navigateSafety(it) }
                singleSubscribe(state){handleResult(it)}
            }
        }
    }

    private fun handleResult(state: ResponseState<ConfirmCodeResponseData>) {
        when(state){
            is ResponseState.Loading -> Log.i("time","жди")
            is ResponseState.Error -> handleError(state.throwable)
        }
    }

    private fun handleError(throwable:Throwable) {
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

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