package com.expostore.ui.chats.chat

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategoryadvertising.CategoryAdvertising
import com.expostore.databinding.ChatFragmentBinding
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.ui.chats.ChatsViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatFragment : Fragment() {

    private lateinit var binding: ChatFragmentBinding
    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatViewPagerAdapter: ChatViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.chat_fragment, container, false)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
        binding.chatVM = chatViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatViewPagerAdapter = ChatViewPagerAdapter(this@ChatFragment, requireContext(), 7)
        binding.chatVp2.adapter = chatViewPagerAdapter
        binding.chatVp2.offscreenPageLimit = chatViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.chatTl, binding.chatVp2) {
                tab, position ->
            tab.customView = chatViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }

}