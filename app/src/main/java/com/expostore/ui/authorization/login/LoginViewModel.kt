package com.expostore.ui.authorization.login

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.Retrofit.getClient
import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.data.AppPreferences
import com.expostore.utils.hideKeyboard
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
class LoginViewModel : ViewModel() {

    private lateinit var navController: NavController
    lateinit var serverApi: ServerApi
    lateinit var request: SignInRequestData
    lateinit var context: Context
    var btnSignIn: Button? = null
    var btnForgotPassword: TextView? = null
    var password: String = ""
    var phone: String = ""
    val bundle = Bundle()
    var phoneLength: Int? = null

    val phonePasswordTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
            btnSignIn?.isEnabled = phone.isNotEmpty() && password.isNotEmpty() && phone.length == phoneLength
            btnForgotPassword?.isEnabled = phone.length == phoneLength
        }
    }

    fun signIn(view: View){
        request = SignInRequestData(phone,password)
        serverApi = getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.authorization(request).enqueue(object : Callback<SignInResponseData> {
            override fun onFailure(call: Call<SignInResponseData>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SignInResponseData>, response: Response<SignInResponseData>) {
                try {
                    if (response.isSuccessful) {
                        if(response.body() != null){
                            if (response.body()!!.access != null) {
                                val token = response.body()!!.access
                                AppPreferences.getSharedPreferences(context).edit().putString("token", token).apply()
                                view.hideKeyboard()
                                navController = Navigation.findNavController(view)
                                navController.navigate(R.id.action_loginFragment_to_mainFragment)
                            }
                        }
                    }
                    else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("detail")
                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e:Exception){
                    Log.d("RESPONSE", e.toString())
                }
            }
        })
    }

    fun confirmNumber(view: View) {
        val request = ConfirmNumberRequestData(phone)
        serverApi = getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
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
        })
    }

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}