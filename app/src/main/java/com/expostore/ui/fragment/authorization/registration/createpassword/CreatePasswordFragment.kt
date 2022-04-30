package com.expostore.ui.fragment.authorization.registration.createpassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.data.AppPreferences
import com.expostore.databinding.CreatePasswordFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class CreatePasswordFragment : BaseFragment<CreatePasswordFragmentBinding>(CreatePasswordFragmentBinding::inflate) {

    private val createPasswordViewModel: CreatePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       var phone=CreatePasswordFragmentArgs.fromBundle(requireArguments()).phone
        createPasswordViewModel.apply {
            subscribe(navigation) { navigateSafety(it) }
            subscribe(ui) { handleInstance(it) }
        }
        binding.imageButton.setOnClickListener {
            createPasswordViewModel.backEnd()
        }


        binding.btnSignInNext.setOnClickListener{
            val password=binding.etPassword.toStroke()
            val second =binding.etSecondPassword.toStroke()
            Log.i("one",password)
            Log.i("two",second)
                 createPasswordViewModel.checkPassword(first=password,
                     second =second )
            createPasswordViewModel.instance.observe(viewLifecycleOwner, Observer {
                when(it){
                    false-> Toast.makeText(requireContext(), "что-то не так", Toast.LENGTH_LONG).show()
                    true-> createPasswordViewModel.signUp(phone,password)
                }
            })

        }

    }

    private fun handleInstance(state: ResponseState<SignUpResponseData>) {
        when (state) {
            is ResponseState.Error -> handleError(state.throwable)
            is ResponseState.Success -> handleSuccess(state.item)
        }

    }

    private fun handleSuccess(item: SignUpResponseData) {
        AppPreferences.getSharedPreferences(requireContext()).edit().putString("token", item.access)
            .putString("refresh", item.refresh)
            .apply()
    }

    private fun handleError(throwable: Throwable) {
        throwable.message?.takeIf { it.isNotEmpty() }?.let {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()

        }
    }
    private val passwordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val firstPassword: String = binding.etPassword.text.toString().trim()
            val secondPassword: String = binding.etSecondPassword.text.toString().trim()
            binding.btnSignInNext.isEnabled =
                firstPassword.isNotEmpty() && secondPassword.isNotEmpty() && firstPassword == secondPassword
        }
    }


}

 fun EditText.toStroke(): String {
    return text.toString()
}
