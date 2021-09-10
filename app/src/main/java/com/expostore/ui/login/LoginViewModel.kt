package com.expostore.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.Retrofit.getClient
import com.expostore.api.ServerApi
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.utils.hideKeyboard
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private lateinit var navController: NavController
    lateinit var serverApi: ServerApi
    lateinit var request: SignInRequestData
    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context
    var password: String = ""
    var number: String = ""


    fun signin(view: View){
        request = SignInRequestData(number,password)

        serverApi = getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.auth(request).enqueue(object : Callback<SignInResponseData> {
            override fun onFailure(call: Call<SignInResponseData>, t: Throwable) {}
            override fun onResponse(call: Call<SignInResponseData>, response: Response<SignInResponseData>) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        if (response.body()!!.access != null) {
                            val token = response.body()!!.access
                            (context as MainActivity).sharedPreferences
                                .edit()
                                .putString("token", token)
                                .apply()

                            view.hideKeyboard()
                            navController = Navigation.findNavController(view)
                            navController.navigate(R.id.action_loginFragment_to_mainFragment)
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

    fun navigateToMain(view: View){
        navController = Navigation.findNavController(view)
        navController.navigate(R.id.action_loginFragment_to_mainFragment)
    }
}