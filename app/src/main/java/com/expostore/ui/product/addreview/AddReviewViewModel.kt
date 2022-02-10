package com.expostore.ui.product.addreview

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.addreview.AddReviewRequestData
import com.expostore.api.pojo.addreview.AddReviewResponseData
import com.expostore.api.pojo.getproduct.ProductResponseData
import com.expostore.api.pojo.signup.SignUpResponseData
import com.expostore.data.AppPreferences
import com.expostore.utils.hideKeyboard
import com.willy.ratingbar.BaseRatingBar
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AddReviewViewModel : ViewModel() {

    var rating: Int? = null
    var id: String? = null
    lateinit var context: Context
    lateinit var navController: NavController

    var imagesId: ArrayList<String> = arrayListOf()
    var review: String = ""
    var btnSaveReview: Button? = null



    val reviewTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (review.isNotEmpty() && imagesId.isNotEmpty()) {
                btnSaveReview!!.isEnabled = true
            }
        }
    }

    fun navigateBack(view: View){
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }

    fun saveReview(view: View){
        val token = AppPreferences.getSharedPreferences(context).getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        if (!id.isNullOrEmpty() && rating != null){
            serverApi.addReview("Bearer $token", id!!, AddReviewRequestData(rating!!,review,imagesId)).enqueue(object : Callback<AddReviewResponseData> {
                override fun onFailure(call: Call<AddReviewResponseData>, t: Throwable) {
                    Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<AddReviewResponseData>, response: Response<AddReviewResponseData>) {
                    try {
                        if (response.isSuccessful)
                            if (response.body() != null)
                                navigateBack(view)
                            else {
                                if (response.errorBody() != null) {
                                    val jObjError = JSONObject(response.errorBody()!!.string())
                                    val message = jObjError.getString("message")
                                    if (message.isNotEmpty())
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.toString())
                    }
                }
            })
        }
    }
}