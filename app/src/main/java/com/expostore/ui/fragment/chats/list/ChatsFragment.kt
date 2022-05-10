package com.expostore.ui.fragment.chats.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.InfoChat
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.state.ResponseState
import com.expostore.utils.OnClick
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        chatsViewModel.apply {
            subscribe(chats) { handleState(it)}
            subscribe(navigation) { navigateSafety(it) }
        }
        chatsViewModel.chatsList()
    }

    override fun onStart() {
        super.onStart()
        manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        onClick = object : OnClick {
            override fun onClickChat(position: Int, mainChat: MainChat) {

                chatsViewModel.
                openChatItem(mainChat.identify()[1],
                    mainChat.identify()[0],
                    mainChat.chatsId(),
                    mainChat.imagesProduct(),
                    mainChat.productsName(),mainChat.identify()[3])}}
        
    }
    fun loading(){
              binding.progressBar3.visibility=View.VISIBLE
    }

    private fun handleState(state: ResponseState<List<MainChat>>) {
        when (state) {
            is ResponseState.Loading -> loading()
            //is ResponseState.Error -> //state.throwable.message
            is ResponseState.Success -> showChats(state.item as MutableList<MainChat> )
        }
    }

    private fun showChats(list: MutableList<MainChat>) {
        binding.rvChats.apply {
            mAdapter = ChatsRecyclerViewAdapter(list, onClick)
            layoutManager = manager
            adapter = mAdapter
        }
        binding.progressBar3.visibility=View.GONE
    }
    
}





