package com.expostore.ui.fragment.chats.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.base.Load
import com.expostore.ui.fragment.chats.*
import com.expostore.utils.OnClick
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Teshkin Daniel
 */

typealias Show = (List<MainChat>) -> Unit
@AndroidEntryPoint
class ChatsFragment : BaseFragment<ChatsFragmentBinding>(ChatsFragmentBinding::inflate) {
    private lateinit var mAdapter: ChatsRecyclerViewAdapter
    private lateinit var manager: LinearLayoutManager
    private val chatsViewModel: ChatsViewModel by viewModels()
    private lateinit var onClick:OnClick

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }
    override fun onStart() {
        super.onStart()
        manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        onClick = object : OnClick {
            override fun onClickChat(position: Int, mainChat: MainChat) {
                    val result=InfoItemChat(mainChat.identify()[1],
                        mainChat.identify()[0],
                        mainChat.chatsId(),
                        mainChat.imagesProduct(),
                        mainChat.productsName(),mainChat.identify()[3])

                chatsViewModel.openChatItem(result)}} }

   private fun subscribeViewModel(){
        val show:Show = {showChats(it as MutableList<MainChat>)}
        val load:Load={loading(it)}
        chatsViewModel.apply {
            chatsList()
            subscribe(chats) { handleState(it,load,show)}
            subscribe(navigation) { navigateSafety(it) }
        }
    }
 fun loading(state:Boolean){
     when(state){
         true->  binding.progressBar3.visibility=View.VISIBLE
         false->  binding.progressBar3.visibility=View.GONE
     }
 }
    private fun showChats(list: MutableList<MainChat>) {
        if(list.isNotEmpty()) binding.progressBar3.visibility=View.GONE
        mAdapter = ChatsRecyclerViewAdapter(list, onClick)
        binding.apply {
            rvChats.install(manager,mAdapter)
        }
    }
}





