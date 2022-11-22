package com.expostore.ui.fragment.chats.chat

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expostore.R
import com.expostore.ui.fragment.chats.dialog.DialogFragment
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.chats.loadTabImage
import kotlinx.android.synthetic.main.chat_tablayout_item.view.*
import kotlinx.android.synthetic.main.product_item.view.*


class ChatViewPagerAdapter(
    val fragment: Fragment,
    val context: Context,
    private val dialogs: Int,
  val  id: Array<String>,
    val username:String,
  private  val images:Array<String>,
    private val product_name:Array<String>,
    val author:String
): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = dialogs

        @SuppressLint("InflateParams")
    fun getTabView(position: Int): View{
            val view: View = LayoutInflater.from(context).inflate(R.layout.chat_tablayout_item, null)
            view.chat_product_name.text=  if(position>product_name.size-1) ""
            else product_name[position]
            if(position>product_name.size-1)view.chat_product_image.loadTabImage("")
           else view.chat_product_image.loadTabImage(images[position])

        return view
    }


    fun getUpdateView(position: Int, state: Boolean){
        val view: View = LayoutInflater.from(context).inflate(R.layout.chat_tablayout_item, null)

        view.chat_product_name.text=  if(position>product_name.size-1) ""
        else product_name[position]
        if(position>product_name.size-1)view.chat_product_image.loadTabImage("")
        else view.chat_product_image.loadTabImage(images[position])
        view.green_point.isVisible=state

    }

    override fun createFragment(position: Int): Fragment {
        val bundle=Bundle()
        bundle.apply {
            putString("id",id[position])
            putString("author",author)
        }
        val fragment=DialogFragment()
        fragment.arguments=bundle
        return fragment
    }
}