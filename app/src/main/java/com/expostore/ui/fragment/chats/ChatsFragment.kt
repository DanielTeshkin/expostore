package com.expostore.ui.fragment.chats

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getchats.ResponseMainChat
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import com.expostore.utils.ChatsRecyclerViewAdapter
import com.expostore.utils.OnClick
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
class ChatsFragment : BaseFragment<ChatsFragmentBinding>(ChatsFragmentBinding::inflate) {
    private lateinit var mAdapter: ChatsRecyclerViewAdapter
    private lateinit var manager: LinearLayoutManager
    private val chatsViewModel: ChatsViewModel by viewModels()
    private lateinit var onClick:OnClick

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatsViewModel.chatsList()
        manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        chatsViewModel.apply {
            subscribe(chats) { handle(it)}
            subscribe(navigation) { navigateSafety(it) }
        }
    }

    override fun onStart() {
        super.onStart()
        onClick = object : OnClick {
            override fun onClickChat(position: Int, responseMainChat: ResponseMainChat) {
                chatsViewModel.
                openChatItem(responseMainChat.identify()[1],
                    responseMainChat.identify()[0],
                    responseMainChat.chatsId(),
                    responseMainChat.imagesProduct(),
                responseMainChat.productsName(),responseMainChat.identify()[3])}}
        
    }
    fun loading(instance:Boolean){

    }

    private fun handle(state: ResponseState<List<ResponseMainChat>>) {
        when (state) {
          //  is ResponseState.Loading ->
            //is ResponseState.Error -> //state.throwable.message
            is ResponseState.Success -> toList(state.item as MutableList<ResponseMainChat> )
        }
    }

    private fun toList(list: MutableList<ResponseMainChat>) {
        binding.rvChats.apply {
            mAdapter = ChatsRecyclerViewAdapter(list, onClick)
            layoutManager = manager
            adapter = mAdapter
        }
    }
    
}





