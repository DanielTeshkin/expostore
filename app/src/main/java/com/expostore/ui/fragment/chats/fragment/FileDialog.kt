package com.expostore.ui.fragment.chats.fragment


import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getchats.FileOrImageMessage
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.databinding.FileDialogFragmentBinding
import com.expostore.ui.fragment.chats.general.Dialog
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.dialog.adapter.FileDialogRecyclerViewAdapter
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
@AndroidEntryPoint
class FileDialog(val list: ArrayList<Uri>, val id:String) : DialogFragment(), Dialog {
    private val viewModel: FileDialogViewModel by viewModels()
    lateinit var binding: FileDialogFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FileDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       binding.progressBar.visibility=View.GONE
        binding.rec.apply {

            layoutManager=LinearLayoutManager(requireContext())
            adapter= FileDialogRecyclerViewAdapter(list, context)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.messageSen.setOnClickListener {
            val id_list=ArrayList<String>()
            val fileStorage= FileStorage(requireContext())
            viewModel.apply {
                list.map {
                    val pair = fileStorage.request(it)
                    saveFile(pair.second, pair.first)
                    singleSubscribe(saveFile) { state->
                        when(state){
                            is ResponseState.Success -> id_list.add(state.item.id)
                            is ResponseState.Loading -> {
                                binding.progressBar.visibility=View.VISIBLE
                                Toast.makeText(requireContext(),"Файлы загружаются",Toast.LENGTH_LONG).show()}
                        }
                    }
                }

                lifecycleScope.launch {
                    delay(15000)
                    if(binding.send.text.isEmpty()){

                        sendFileOrImage(id, FileOrImageMessage(chatFiles = id_list))
                    }
                    else{ sendMessage(id, MessageRequest( text =binding.send.text.toString() , chatFiles = id_list))}
                }
                singleSubscribe(message){
                    handle(it)
                }
            }
        }

    }
    private fun handle(state: ResponseState<MessageRequest>) {
        when(state){
            is ResponseState.Success -> dismiss()
            is ResponseState.Error -> Toast.makeText(requireContext(),state.throwable.message,Toast.LENGTH_LONG).show()

        }

    }

    @SuppressLint("RepeatOnLifecycleWrongUsage")
    override fun <T> singleSubscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    flow.collect(action)
                }
            }
        }

    }
}