package com.expostore.ui.authorization.registration.confirmnumber

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import br.com.sapereaude.maskedEditText.MaskedEditText
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.utils.hideKeyboard
import com.expostore.utils.makeLinks
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val MAX_LENGTH = 12
const val PHONE_INPUT = 11

@SuppressLint("StaticFieldLeak")
class ConfirmNumberViewModel : ViewModel() {

    private lateinit var serverApi: ServerApi
    private lateinit var navController: NavController
    lateinit var context: Context
    private var phoneInput: String? = null
    var numberBtn: Button? = null
    var numberEt: MaskedEditText? = null
    var number: String = ""
    val bundle = Bundle()

    val numberTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun afterTextChanged(s: Editable) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when {
                number.length == MAX_LENGTH -> {
                    phoneInput = "8" + numberEt!!.rawText
                    numberBtn!!.isEnabled = true
                }
                number.length > MAX_LENGTH -> {
                    number.dropLast(number.length - MAX_LENGTH)
                    numberBtn!!.isEnabled = true

                }
                else -> numberBtn!!.isEnabled = false
            }
        }
    }

    fun confirmNumber(view: View) {
        if (phoneInput!!.length == PHONE_INPUT) {
            val request = ConfirmNumberRequestData(phoneInput)
            serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
            serverApi.confirmNumber(request).enqueue(object : Callback<ConfirmNumberResponseData> {
                override fun onFailure(call: Call<ConfirmNumberResponseData>, t: Throwable) {
                    Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
                }
                override fun onResponse(call: Call<ConfirmNumberResponseData>, response: Response<ConfirmNumberResponseData>) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body() != null) {
                                if (response.body()!!.phone != null) {
                                    bundle.clear()
                                    if (phoneInput == response.body()!!.phone) bundle.putString(
                                        "numberText",
                                        "+7 $number"
                                    )
                                    else bundle.putString("numberText", response.body()!!.phone)
                                    bundle.putString("numberInput", phoneInput)

                                    view.hideKeyboard()
                                    navController = Navigation.findNavController(view)
                                    navController.navigate(R.id.action_numberFragment_to_confirmNumberFragment, bundle)
                                }
                            }
                        }
                    }
                    catch (e: Exception){
                        Log.d("RESPONSE", e.toString())
                    }
                }
            })
        }
    }

    fun navigateBack(view: View) {
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }

    fun setupClickableTextView(textView: TextView) {

        val personalData = object : ClickableSpan() {
            override fun onClick(view: View) {
                bundle.clear()
                bundle.putString("url",context.getString(R.string.terms_of_use))
                navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_numberFragment_to_webViewFragment, bundle)
            }
        }

        val termsOfUse = object : ClickableSpan() {
            override fun onClick(view: View) {
                bundle.clear()
                bundle.putString("url",context.getString(R.string.terms_of_use))
                navController = Navigation.findNavController(view)
                navController.navigate(R.id.action_numberFragment_to_webViewFragment, bundle)
            }
        }
        makeLinks(
            textView, arrayOf(
                "пользовательским соглашением",
                "политикой конфиденциальности"
            ), arrayOf(personalData, termsOfUse)
        )
    }
}



