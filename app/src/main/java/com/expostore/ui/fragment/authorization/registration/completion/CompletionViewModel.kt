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
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("StaticFieldLeak")
class CompletionViewModel : ViewModel() {

    lateinit var etCity: MaterialAutoCompleteTextView
    private lateinit var serverApi: ServerApi
    private lateinit var navController: NavController
    lateinit var context: Context
    lateinit var adapter: ArrayAdapter<City>
    lateinit var cities: ArrayList<City>

    var idCity: Int? = null

    var surname: String = ""
    var name: String = ""
    var patronymic: String = ""
    var city: String = ""
    var email: String = ""

    fun getCities(){
        val token = AppPreferences.getSharedPreferences(context).getString("token", "")
        /*serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
        serverApi.getCities(token).enqueue(object : Callback<ArrayList<City>> {
            override fun onFailure(call: Call<ArrayList<City>>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
            }
            override fun onResponse(call: Call<ArrayList<City>>, response: Response<ArrayList<City>>) {
                try {
                    if (response.isSuccessful) {
                        if (response.body() != null)
                            if (response.body() != null)
                                setCityAdapter(response.body()!!)
                    } else {
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
        })*/
    }

    fun setCityAdapter(cities: ArrayList<City>){
        adapter = ArrayAdapter<City>(context, android.R.layout.simple_dropdown_item_1line, cities)
        etCity.setAdapter(adapter)

        etCity.doOnTextChanged { text, start, before, count ->
            idCity = cities.find { it.name == etCity.text.toString() }?.id
        }

        etCity.onItemClickListener = OnItemClickListener { _, _, _, _ ->
            idCity = cities.find { it.name == etCity.text.toString() }?.id
        }
    }


    fun navigateBack(view: View) {
        navController = Navigation.findNavController(view)
        navController.popBackStack()
    }

    fun editProfile(view: View) {
        if (idCity != null) {
            val token = AppPreferences.getSharedPreferences(context).getString("token", "")
            val request = EditProfileRequestData(surname, name, patronymic, email, idCity)
            /*serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
            serverApi.editProfile("Bearer $token", request).enqueue(object : Callback<EditProfileResponseData> {
                override fun onFailure(call: Call<EditProfileResponseData>, t: Throwable) {
                    Toast.makeText(context, context.getString(R.string.on_failure_text), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<EditProfileResponseData>, response: Response<EditProfileResponseData>) {
                    try {
                        if (response.isSuccessful) {
                            if (response.body() != null)
                                if (response.body() != null) {
                                    navController = Navigation.findNavController(view)
                                    navController.navigate(R.id.action_completionFragment_to_mainFragment)
                                }
                        } else {
                            if (response.errorBody() != null) {
                                val jObjError = JSONObject(response.errorBody()!!.string())
                                val message = jObjError.getString("detail")
                                if (message.isNotEmpty())
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    } catch (e: Exception) {
                        Log.d("RESPONSE", e.toString())
                    }
                }
            })*/
        }
        else Toast.makeText(context, "Выберите город из списка", Toast.LENGTH_SHORT).show()
    }
}