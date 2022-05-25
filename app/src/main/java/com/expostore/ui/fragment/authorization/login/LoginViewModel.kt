package com.expostore.ui.fragment.authorization.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.model.auth.SignInResponseDataModel
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.authorization.login.interactor.LoginInteractor
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val interactor: LoginInteractor
) : BaseViewModel() {

    private val _uiState = MutableSharedFlow<ResponseState<SignInResponseDataModel>>()
    val uiState = _uiState.asSharedFlow()

    private val _error = MutableSharedFlow<String>()
    val error = _error.asSharedFlow()

    //private lateinit var navController: NavController
    lateinit var serverApi: ServerApi
    lateinit var request: SignInRequestData
    lateinit var context: Context
    var btnSignIn: Button? = null
    var btnForgotPassword: TextView? = null
    var password: String = ""
    var phone: String = ""
    val bundle = Bundle()
    var phoneLength: Int? = null

    override fun onStart() {
        /* no-op */
    }

    val phonePasswordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            btnSignIn?.isEnabled =
                phone.isNotEmpty() && password.isNotEmpty() && phone.length == phoneLength
            btnForgotPassword?.isEnabled = phone.length == phoneLength
        }
    }

    fun signIn(phone: String, password: String) {
        interactor.login(phone, password)
            .handleResult(_uiState, {
                navigationTo(LoginFragmentDirections.actionLoginFragmentToMainFragment())
            })
    }

   fun saveToken(refresh:String,access:String) =
       viewModelScope.launch {
           interactor.saveToken(refresh, access)
       }



    fun confirmNumber(view: View, phone: String) {
        val request = ConfirmNumberRequestData(phone)
        /* serverApi = getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
         serverApi.confirmNumber(request).enqueue(object : Callback<ConfirmNumberResponseData> {
             override fun onFailure(call: Call<ConfirmNumberResponseData>, t: Throwable) {
                 Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
             }
             override fun onResponse(call: Call<ConfirmNumberResponseData>, response: Response<ConfirmNumberResponseData>) {
                 if (response.isSuccessful) {
                     if (response.body() != null) {
                         if (response.body()!!.phone != null) {
                             bundle.putString("phone", phone)
                             navController = Navigation.findNavController(view)
                             navController.navigate(R.id.action_loginFragment_to_passwordRecoveryFragment, bundle)
                             view.hideKeyboard()
                         }
                     }
                 }
             }
         })*/
    }

    fun navigateBack(view: View) {
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}