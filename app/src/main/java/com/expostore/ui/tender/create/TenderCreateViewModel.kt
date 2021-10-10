package com.expostore.ui.tender.create

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.createtender.CreateTenderRequestData
import com.expostore.api.pojo.createtender.CreateTenderResponseData
import com.expostore.api.pojo.productcategory.ProductCategory
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

@SuppressLint("StaticFieldLeak")
class TenderCreateViewModel : ViewModel() {

    lateinit var context:Context
    lateinit var navController: NavController

    var title: String = ""
    var description: String = ""
    var priceFrom: String = ""
    var priceUpTo: String = ""
    var location: String = ""
    var imagesId: ArrayList<String> = arrayListOf()
    var category: ArrayList<String> = arrayListOf()

    var btnCancel: Button? = null
    var btnSaveDraft: Button? = null
    var btnSave: Button? = null

    val bundle = Bundle()

    val tenderTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            if (title.isNotEmpty() && description.isNotEmpty() && priceFrom.isNotEmpty() && priceUpTo.isNotEmpty() && location.isNotEmpty() && imagesId.isNotEmpty()) {
                btnCancel!!.visibility = View.VISIBLE
                btnSaveDraft!!.visibility = View.VISIBLE
                btnSave!!.isEnabled = true
            }
        }
    }

    fun getProductCategory(view: View){
        val token = (context as MainActivity).sharedPreferences.getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.getProductCategory("Bearer $token").enqueue(object : Callback<ArrayList<ProductCategory>> {
            override fun onResponse(call: Call<ArrayList<ProductCategory>>, response: Response<ArrayList<ProductCategory>>) {
                try {
                    if (response.isSuccessful) {
                        navController = Navigation.findNavController(view)
                        bundle.putParcelableArrayList("specifications",response.body())

                        navController.navigate(R.id.action_tenderCreateFragment_to_specificationsFragment, bundle)
                    } else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("detail")
                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e: Exception){
                    Log.d("exception", e.toString())
                }
            }

            override fun onFailure(call: Call<ArrayList<ProductCategory>>, t: Throwable) {
                Toast.makeText(context, (context as MainActivity).getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun createTender(view: View){
        val token = (context as MainActivity).sharedPreferences.getString("token", "")
        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
        val requestData = CreateTenderRequestData(title,description,priceFrom,priceUpTo,location,imagesId,category)

        serverApi.createTender("Bearer $token", requestData).enqueue(object : Callback<CreateTenderResponseData> {
            override fun onResponse(call: Call<CreateTenderResponseData>, response: Response<CreateTenderResponseData>) {
                try {
                    if (response.isSuccessful) {
                        navController = Navigation.findNavController(view)
                        navController.popBackStack()
                    } else {
                        if (response.errorBody() != null) {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            val message = jObjError.getString("detail")
                            if (message.isNotEmpty())
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                catch (e: Exception){
                    Log.d("exception", e.toString())
                }
            }

            override fun onFailure(call: Call<CreateTenderResponseData>, t: Throwable) {
                Toast.makeText(context, (context as MainActivity).getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
        })
    }
}