package com.expostore.ui.fragment.chats.chat

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expostore.R
import com.expostore.extension.load
import com.expostore.ui.fragment.chats.dialog.DialogFragment
import com.expostore.ui.fragment.chats.loadTabImage
import kotlinx.android.synthetic.main.chat_tablayout_item.view.*


class ChatViewPagerAdapter(
    val fragment: Fragment,
    val context: Context,
    private val dialogs: Int,
  val  id: Array<String>,
    val username:String,
    val id_image:Array<String>,
    val name_products:Array<String>,
    val author:String
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = dialogs

        @SuppressLint("InflateParams")
    fun getTabView(position: Int): View{
            val view: View = LayoutInflater.from(context).inflate(R.layout.chat_tablayout_item, null)
            view.text.text = name_products[position]
                 view.image.loadTabImage(id_image[position])
        return view
    }

    override fun createFragment(position: Int): Fragment {
        return DialogFragment(id[position],  author,username)
    }
}