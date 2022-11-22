package com.expostore.ui.fragment.chats.chat

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.expostore.R
import com.expostore.databinding.ChatFragmentBinding
import com.expostore.databinding.ChatTablayoutItemBinding
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.fragments.BaseFragment

import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.chats.listPath
import com.expostore.ui.fragment.chats.loadTabImage
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.chat_tablayout_item.view.*

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
        subscribe(PagerChatRepository.getInstance().getOpenFileState()){ if(it) resultLauncher.launch(FileStorage(requireContext()).openStorage())}


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
            tabLayoutMediator = TabLayoutMediator(chatTl, chatVp2) { tab, position ->
                    tab.customView = chatViewPagerAdapter.getTabView(position)


            }
            tabLayoutMediator.attach()
            chatTl.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {

                    if (tab != null) {
                    val view=     chatTl.getTabAt(tab.position)?.customView
                        view?.findViewById<ImageView>(R.id.green_point)?.visibility=View.VISIBLE
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    if (tab != null) {
                        val view=     chatTl.getTabAt(tab.position)?.customView
                        view?.findViewById<ImageView>(R.id.green_point)?.visibility=View.GONE
                     }
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                   // view?.findViewById<ImageView>(R.id.green_point)?.visibility=View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
        PagerChatRepository.getInstance().getUriFiles().value= listOf()
    }


}







