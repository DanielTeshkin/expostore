package com.expostore.ui.fragment.authorization.registration.confirmnumber

import com.expostore.api.pojo.confirmnumber.ConfirmNumberResponseData
import com.expostore.data.repositories.AuthorizationRepository
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

const val MAX_LENGTH = 12
const val PHONE_INPUT = 11
/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class ConfirmNumberViewModel@Inject constructor(private val registration: AuthorizationRepository) : BaseViewModel() {

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




