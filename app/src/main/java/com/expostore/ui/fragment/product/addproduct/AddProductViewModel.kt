package com.expostore.ui.fragment.product.addproduct

import android.content.Context
import android.os.Bundle
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
import com.expostore.api.pojo.productcategory.ProductCategory
import com.expostore.data.AppPreferences
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class AddProductViewModel : ViewModel() {

    lateinit var context: Context
    lateinit var navController: NavController

    var name: String = ""
    var price: String = ""
    var count: String = ""
    var description: String = ""

    var btnCancel: Button? = null
    var btnSaveDraft: Button? = null
    var btnSave: Button? = null

    val bundle = Bundle()

    fun getProductCategory(view: View){
        val token = AppPreferences.getSharedPreferences(context).getString("token", "")
       /* val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        serverApi.getProductCategory("Bearer $token").enqueue(object :
            Callback<ArrayList<ProductCategory>> {
            override fun onResponse(call: Call<ArrayList<ProductCategory>>, response: Response<ArrayList<ProductCategory>>) {
                try {
                    if (response.isSuccessful) {
                        navController = Navigation.findNavController(view)
                        bundle.putParcelableArrayList("specifications",response.body())
                        navController.navigate(R.id.action_addProductFragment_to_specificationsFragment, bundle)

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
        })*/
    }

    fun getProductCharacteristic(view: View){

    }


    fun getProductConnections(view: View){

    }

    fun addProduct(view: View){

    }

}