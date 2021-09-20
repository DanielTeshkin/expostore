package com.expostore.ui.authorization.registration.createpassword

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
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
import com.expostore.api.ServerApi
import com.expostore.api.pojo.signin.SignInRequestData
import com.expostore.api.pojo.signin.SignInResponseData
import com.expostore.api.pojo.signup.SignUpRequestData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.utils.hideKeyboard
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
class CreatePasswordViewModel : ViewModel() {

    private lateinit var navController: NavController
    lateinit var serverApi: ServerApi
    lateinit var request: SignUpRequestData
    lateinit var context: Context

    var phone: String? = null
    var password: String = ""
    var secondPassword: String = ""

    fun signUp(view: View){
        request = SignUpRequestData(phone,password)
        serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.registration(request).enqueue(object : Callback<SignUpResponseData> {
            override fun onFailure(call: Call<SignUpResponseData>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<SignUpResponseData>, response: Response<SignUpResponseData>) {
                try {
                    if (response.isSuccessful) {
                        if(response.body() != null){
                            if (response.body()!!.access != null) {
                                val token = response.body()!!.access
                                (context as MainActivity).sharedPreferences.edit().putString("token", token).apply()
                                view.hideKeyboard()
                                navController = Navigation.findNavController(view)
                                navController.navigate(R.id.action_createPasswordFragment_to_completionFragment)
                            }
                        }
                    }
                    else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("username")
                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e: Exception){
                    Log.d("RESPONSE", e.toString())
                }
            }
        })
    }

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }
}