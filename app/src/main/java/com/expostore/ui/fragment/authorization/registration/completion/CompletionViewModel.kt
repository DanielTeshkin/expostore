package com.expostore.ui.fragment.authorization.registration.completion

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.editprofile.EditProfileRequestData
import com.expostore.api.pojo.editprofile.EditProfileResponseData
import com.expostore.api.pojo.getcities.City
import com.expostore.data.AppPreferences
import com.expostore.ui.base.BaseViewModel
import com.expostore.ui.fragment.authorization.registration.interactor.InteractorRegistration
import com.expostore.ui.state.ResponseState
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * @author Teshkin Daniel
 */
@HiltViewModel
class CompletionViewModel@Inject constructor(private val registration: InteractorRegistration): BaseViewModel() {

    private val _ui= MutableSharedFlow<ResponseState<EditProfileResponseData>>()
    val ui=_ui.asSharedFlow()

    override fun onStart() {
        TODO("Not yet implemented")
    }
          fun editProfile(editProfileRequestData: EditProfileRequestData){
              registration.editProfile(editProfileRequestData).handleResult(_ui) {
                  navigationTo(CompletionFragmentDirections.actionCompletionFragmentToMainFragment())
              }
          }
    fun backEnd(){
        navigationTo(CompletionFragmentDirections.actionCompletionFragmentToCreatePasswordFragment())
    }




}