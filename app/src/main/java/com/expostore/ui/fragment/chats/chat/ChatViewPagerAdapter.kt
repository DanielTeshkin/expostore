package com.expostore.ui.fragment.chats.chat

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expostore.R
import com.expostore.ui.fragment.chats.dialog.DialogFragment
import kotlinx.android.synthetic.main.chat_tablayout_item.view.*


class ChatViewPagerAdapter(val fragment: Fragment, val context: Context, private val dialogs: Int): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = dialogs

    @SuppressLint("InflateParams")
    fun getTabView(position: Int): View{

            val view: View = LayoutInflater.from(context).inflate(R.layout.chat_tablayout_item, null)
            view.text.text = position.toString()
//            GlideToVectorYou.init().with(context).load(Uri.parse(categories[position].image), view.image)
//
//            if (categories[position].categoryId == -1) {
//                cachedDataStorage.favoriteTab.value = true
//
//                view.text.text = "Избранное"
//                //view.image.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_favorites))
//                Glide.with(context).load(R.drawable.ic_favorites).into(view.image)
//            }
            return view
    }


    override fun createFragment(position: Int): Fragment {
        var result: Fragment = DialogFragment()

//        when(position){
//            1 -> {
//                result = DrugsFragment()
//                result.category = null
//            }
//            position -> {
//                result = DrugsFragment()
//                result.category = categories[position].categoryId
//            }
//        }
        return result
    }
}