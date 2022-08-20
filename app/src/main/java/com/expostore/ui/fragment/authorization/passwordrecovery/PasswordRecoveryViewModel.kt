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
import com.expostore.api.ApiWorker

import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmcode.ConfirmCodeRequestData
import com.expostore.api.pojo.confirmcode.ConfirmCodeResponseData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.utils.hideKeyboard
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor( private val apiWorker: ApiWorker) : ViewModel() {
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


}