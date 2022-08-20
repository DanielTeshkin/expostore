package com.expostore.ui.fragment.authorization.newpassword

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R

import com.expostore.api.ServerApi
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.data.AppPreferences
import com.expostore.ui.base.BaseViewModel
import com.expostore.utils.hideKeyboard
import dagger.hilt.android.lifecycle.HiltViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@HiltViewModel
class NewPasswordViewModel : BaseViewModel() {
    override fun onStart() {
        TODO("Not yet implemented")
    }


}