package com.expostore.utils

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton


class Like @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatButton(context, attrs, defStyleAttr){

    sealed class State {
        object Like : State()
        object Unlike : State()
    }

    var state: State = State.Unlike
        set(value) {
            field = value
            refreshDrawableState()
        }

//    override fun onCreateDrawableState(extraSpace: Int): IntArray {
//        super.onCreateDrawableState(extraSpace + 1).apply {
//            val stateAttrRes = when (state) {
//                State.Like -> R.drawable.ic_like
//                State.Unlike -> R.drawable.ic_unlike
//            }
//            View.mergeDrawableStates(this, intArrayOf(stateAttrRes))
//        }
//    }

//    fun selectFavorite(token: String){
//
//        val serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)
//
//        serverApi.selectFavorite("Bearer $token", null).enqueue(object :
//            Callback<SelectFavoriteResponseData> {
//            override fun onResponse(
//                call: Call<SelectFavoriteResponseData>,
//                response: Response<SelectFavoriteResponseData>
//            ) {
//                if (response.isSuccessful)
//                    if (response.body() != null)
//                        Log.d("like", "Like SUCCESS")
//            }
//
//            override fun onFailure(call: Call<SelectFavoriteResponseData>, t: Throwable) {}
//        })
//    }
}