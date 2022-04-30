package com.expostore.ui.fragment.chats.chat

import android.Manifest.permission.CALL_PHONE
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.expostore.databinding.ActivityMainBinding
import com.expostore.databinding.ChatFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import java.util.jar.Manifest

/**
 * @author Teshkin Daniel
 */
class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var chatViewPagerAdapter: ChatViewPagerAdapter
    private lateinit var tabLayoutMediator: TabLayoutMediator
    private lateinit var username:String
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = ChatFragmentArgs.fromBundle(requireArguments()).idData
         username = ChatFragmentArgs.fromBundle(requireArguments()).username
        val author = ChatFragmentArgs.fromBundle(requireArguments()).idAuthor
        chatViewModel.loadName(ChatFragmentArgs.fromBundle(requireArguments()).name)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            chatViewModel.name.collect{binding.title.text=it}
        }
        binding.btnCall.setOnClickListener {
            navigateToCall(username)
        }
        /**
         * Переписать,когда будет БД
         */
        val size: Int = ChatFragmentArgs.fromBundle(requireArguments()).idData.size
        val id_image = ChatFragmentArgs.fromBundle(requireArguments()).idImages
        val product_name = ChatFragmentArgs.fromBundle(requireArguments()).productNames

        chatViewPagerAdapter = ChatViewPagerAdapter(
            this@ChatFragment,
            requireContext(),
            size,
            id,
            username,
            id_image,
            product_name,
            author
        )
        binding.chatVp2.adapter = chatViewPagerAdapter
        binding.chatVp2.offscreenPageLimit = chatViewPagerAdapter.itemCount
        tabLayoutMediator =
            TabLayoutMediator(binding.chatTl, binding.chatVp2) { tab, position ->
                tab.customView = chatViewPagerAdapter.getTabView(position)
            }
        tabLayoutMediator.attach()

    }
}







