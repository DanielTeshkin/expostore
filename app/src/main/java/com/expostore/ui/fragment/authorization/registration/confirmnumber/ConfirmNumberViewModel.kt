package com.expostore.ui.fragment.authorization.registration.confirmnumber

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

import com.expostore.api.ServerApi
import com.expostore.api.pojo.confirmnumber.ConfirmNumberRequestData
import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.authorization.registration.interactor.InteractorRegistration
import com.expostore.ui.state.ResponseState
import com.expostore.utils.hideKeyboard
import com.expostore.utils.makeLinks
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

const val MAX_LENGTH = 12
const val PHONE_INPUT = 11
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ConfirmNumberViewModel@Inject constructor(private val registration: InteractorRegistration) : BaseViewModel() {

 private val _confirmState= MutableSharedFlow<ResponseState<ConfirmNumberResponseData>>()
    val confirmState=_confirmState.asSharedFlow()




    fun confirmNumber(phone: String) {
        if (phone.length == PHONE_INPUT) {
         registration.confirmNumber(phone).handleResult(_confirmState,{
             navigationTo(ConfirmNumberFragmentDirections.actionNumberFragmentToConfirmNumberFragment(phone))
         })
        }

    }
    fun toBack(){
        navigationTo(ConfirmNumberFragmentDirections.actionNumberFragmentToOpenFragment())
    }

    override fun onStart() {
        TODO("Not yet implemented")
    }

    //fun setupClickableTextView(textView: TextView,context:Context) {

      //  val personalData = object : ClickableSpan() {
           // override fun onClick(view: View) {
            //    bundle.clear()
             //   bundle.putString("url",context.getString(R.string.terms_of_use))
            //    navController = Navigation.findNavController(view)
             //   navController.navigate(R.id.action_numberFragment_to_webViewFragment, bundle)
            //}
        //}

      //  val termsOfUse = object : ClickableSpan() {
         //   override fun onClick(view: View) {
        //        bundle.clear()
             //   bundle.putString("url",context.getString(R.string.terms_of_use))
             //   navController = Navigation.findNavController(view)
            //    navController.navigate(R.id.action_numberFragment_to_webViewFragment, bundle)
            }

       // makeLinks(
           // textView, arrayOf(
           //     "пользовательским соглашением",
           //     "политикой конфиденциальности"
          //  ), arrayOf(personalData, termsOfUse)
        //)
    //}




