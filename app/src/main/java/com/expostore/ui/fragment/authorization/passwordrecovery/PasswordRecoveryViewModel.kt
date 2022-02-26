package com.expostore.ui.fragment.authorization.passwordrecovery

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.utils.hideKeyboard
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@SuppressLint("StaticFieldLeak")

class PasswordRecoveryViewModel : ViewModel() {
    private lateinit var serverApi: ServerApi
    private lateinit var navController: NavController
    lateinit var context: Context
    var btnResendCode: TextView? = null
    var phoneInput: String? = null
    var code: String = ""
    val bundle = Bundle()

    val timer = object : CountDownTimer(60000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            btnResendCode!!.text = context.getString(R.string.confirm_code_btn_resend_code_text, millisUntilFinished / 1000)
            btnResendCode!!.isClickable = false
        }

        override fun onFinish() {
            btnResendCode!!.text = context.getString(R.string.confirm_code_btn_resend_code_title)
            btnResendCode!!.isClickable = true
        }
    }

    fun confirmCode(view: View, code: String){
        val request = ConfirmCodeRequestData(phoneInput, code)
        /*serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
        serverApi.confirmCode(request).enqueue(object : Callback<ConfirmCodeResponseData> {

            override fun onFailure(call: Call<ConfirmCodeResponseData>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ConfirmCodeResponseData>, response: Response<ConfirmCodeResponseData>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.message != null) {
                                view.hideKeyboard()
                                bundle.putString("phoneInput",phoneInput)
                                navController = Navigation.findNavController(view)
                                navController.navigate(R.id.action_passwordRecoveryFragment_to_newPasswordFragment, bundle)
                            }
                        }
                    } else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("message")

                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e: Exception){
                    Log.d("RESPONSE", e.toString())
                }
            }
        })*/
    }

    fun confirmNumber(view: View) {
        val request = ConfirmNumberRequestData(phoneInput)
        /*serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
        serverApi.confirmNumber(request).enqueue(object : Callback<ConfirmNumberResponseData> {
            override fun onFailure(call: Call<ConfirmNumberResponseData>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ConfirmNumberResponseData>, response: Response<ConfirmNumberResponseData>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null) {
                            if (response.body()!!.phone != null) {
                                view.hideKeyboard()
                                timer.start()
                            }
                        }
                    }
                }
                catch (e: Exception){
                    Log.d("RESPONSE", e.toString())
                }
            }
        })*/
    }

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}