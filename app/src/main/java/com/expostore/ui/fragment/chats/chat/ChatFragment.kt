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
import com.expostore.ui.base.fragments.BaseFragment
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage

import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.ImagePicker
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.chats.listPath
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ChatFragment : BaseFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate){

    private lateinit var tabLayoutMediator: TabLayoutMediator
    override var isBottomNavViewVisible: Boolean=false
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(ChatFragmentArgs.fromBundle(requireArguments()).info)
        subscribe(PagerChatRepository.getInstance().getOpenFileState()){
            if(it) resultLauncher.launch(FileStorage(requireContext()).openStorage())}
        //subscribe(PagerChatRepository.getInstance().getImagesOpen()){
        ///    if(it) openGallery()
      //  }

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

    override fun onDestroy() {
        super.onDestroy()
        PagerChatRepository.getInstance().getUriFiles().value= listOf()
    }


}







