package com.expostore.ui.fragment.chats.chat

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.expostore.databinding.ChatFragmentBinding
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.chats.listPath
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
    override var isBottomNavViewVisible: Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val result=ChatFragmentArgs.fromBundle(requireArguments()).info
        chatViewModel.saveInfo(result)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribe(chatViewModel.info) { init(it!!) }
        subscribe(PagerChatRepository.getInstance().getOpenFileState()){
            if(it)
            resultLauncher.launch(FileStorage(requireContext()).openStorage())}

    }

    fun init(info: InfoItemChat) {
        binding.apply {
            title.text = info.name
            btnCall.setOnClickListener { navigateToCall(info.username!!) }
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

            chatVp2.adapter = chatViewPagerAdapter
            chatVp2.offscreenPageLimit = chatViewPagerAdapter.itemCount
            tabLayoutMediator =
                TabLayoutMediator(chatTl, chatVp2) { tab, position ->
                    tab.customView = chatViewPagerAdapter.getTabView(position)
                }
            tabLayoutMediator.attach()
            chatVp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    //  PagerChatRepository.getInstance().getIdChat().value=info.id_list[position]
                }
            })

        }
    }
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            Log.i("dggg","ddd")
            if (result.resultCode == Activity.RESULT_OK) {
                Log.i("dok","ddd")
                val files = result.data?.clipData
                if (files != null) {
                    PagerChatRepository.getInstance().getUriFiles().value=(files.listPath())
                } else {
                    val list = ArrayList<Uri>()
                    result.data?.data?.let { list.add(it) }
                    PagerChatRepository.getInstance().getUriFiles().value=(list)
                }
            }
            PagerChatRepository.getInstance().getOpenFileState().value=false
        }
}







