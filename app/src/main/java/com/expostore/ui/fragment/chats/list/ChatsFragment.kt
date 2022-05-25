package com.expostore.ui.fragment.chats.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.model.chats.DataMapping.MainChat
import com.expostore.model.chats.InfoItemChat
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.state.ResponseState
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
        chatsViewModel.apply {
            chatsList()
            singleSubscribe(chats) { handleState(it)}
            subscribe(navigation) { navigateSafety(it) }
        }
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
                setFragmentResult("requestKey", bundleOf("info" to result))
                chatsViewModel.openChatItem()}} }

    private fun handleState(state: ResponseState<List<MainChat>>) = when (state) {
        is ResponseState.Error -> handleError(state.throwable.message!!)
        is ResponseState.Success -> showChats(state.item as MutableList<MainChat>)
        is ResponseState.Loading -> Log.i("dddf","Fff")
    }

    private fun showChats(list: MutableList<MainChat>) {
        mAdapter = ChatsRecyclerViewAdapter(list, onClick)
        binding.apply {
            rvChats.install(manager,mAdapter)
        binding.progressBar3.visibility=View.GONE
        }
    }
}





