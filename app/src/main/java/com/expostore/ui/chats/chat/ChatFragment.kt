package com.expostore.ui.chats.chat

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.expostore.databinding.ChatFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator

class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private lateinit var chatViewModel: ChatViewModel
    private lateinit var chatViewPagerAdapter: ChatViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatViewPagerAdapter = ChatViewPagerAdapter(this@ChatFragment, requireContext(), 7)
        binding.chatVp2.adapter = chatViewPagerAdapter
        binding.chatVp2.offscreenPageLimit = chatViewPagerAdapter.itemCount

        tabLayoutMediator = TabLayoutMediator(binding.chatTl, binding.chatVp2) { tab, position ->
            tab.customView = chatViewPagerAdapter.getTabView(position)
        }
        tabLayoutMediator.attach()
    }

}