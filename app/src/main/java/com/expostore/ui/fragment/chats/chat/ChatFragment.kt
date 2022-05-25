package com.expostore.ui.fragment.chats.chat

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.expostore.databinding.ChatFragmentBinding
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.BaseFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private val chatViewModel: ChatViewModel by viewModels()
    private lateinit var tabLayoutMediator: TabLayoutMediator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getParcelable<InfoItemChat>("info")
          chatViewModel.saveInfo(result)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatViewModel.loadName("ddd")
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            chatViewModel.info.collect{
                init(it!!)
            }
        }

    }

    fun init(info: InfoItemChat){
        binding.apply {
            title.text=info.name
            btnCall.setOnClickListener {navigateToCall(info.username!!)}
            val chatViewPagerAdapter = ChatViewPagerAdapter(
                this@ChatFragment,
                requireContext(),
                info.id_list!!.size,
                info.id_list,
                info.username!!,
                info.product_names!!,
                info.id_image!!,
                info.author!!
            )
            Log.i("username",info.username)
            Log.i("username",info.product_names[0])
            Log.i("images",info.id_image[0])

          chatVp2.adapter = chatViewPagerAdapter
           chatVp2.offscreenPageLimit = chatViewPagerAdapter.itemCount
            tabLayoutMediator =
                TabLayoutMediator(chatTl, chatVp2) { tab, position ->
                    tab.customView = chatViewPagerAdapter.getTabView(position)
                }
            tabLayoutMediator.attach()
        }
    }
}







