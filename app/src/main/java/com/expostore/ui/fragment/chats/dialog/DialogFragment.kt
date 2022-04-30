package com.expostore.ui.fragment.chats.dialog


import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getchats.ItemChatResponse
import com.expostore.api.pojo.getchats.MessageResponse
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.state.ResponseState
import com.expostore.utils.DialogRecyclerViewAdapter
import com.expostore.utils.OnClickImage
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
 class DialogFragment(val id: String, val author:String, val username:String) : BaseFragment<DialogFragmentBinding>(DialogFragmentBinding::inflate){
    private val viewModel: DialogViewModel by viewModels()
    private lateinit var manager: LinearLayoutManager
    lateinit var mAdapter: DialogRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateMessages(id)
        subscribeViewModel()
    }

    override fun onStart() {
        super.onStart()
        binding.messageSend.setOnClickListener {
            sendMessage()
        }
        binding.messageSendBtn.setOnClickListener {
            openGallery()
        }
    }

    private fun subscribeViewModel(){
        viewModel.apply {
            subscribe(item){handle(it)}
        }
    }

    private  fun sendMessage(){
        viewModel.sentMessageOrUpdate(id,MessageRequest(text = binding.etInput.text.toString(), author = username))
        binding.etInput.text.clear()
    }

    private fun handle(state: ResponseState<ItemChatResponse>){
        when (state) {
            is ResponseState.Loading ->  handleLoading()
            is ResponseState.Error ->  handleError(state.throwable.message!!)
            is ResponseState.Success -> loadOrUpdate(state.item.messageResponses)
        }
    }
    private fun handleLoading(){
        viewModel.instanceProgressBar.observe(viewLifecycleOwner, Observer {
            when(it){
                true-> binding.progressBar2.visibility=View.VISIBLE
                false->binding.progressBar2.visibility=View.INVISIBLE
            }
        })

    }

 private fun loadOrUpdate(messageResponses:MutableList<MessageResponse>){
         viewModel.instanceAdapter.observe(viewLifecycleOwner, Observer {
             when(it){
                 false->load(messageResponses)
                 true -> update(messageResponses)
             }
         })

 }

    private fun load(messageResponses:MutableList<MessageResponse>){
           val onClickImage=object :OnClickImage{
               override fun click(bitmap: Bitmap) {
                   val dialogFragment=ImageDownload(bitmap)
                   dialogFragment.show(requireActivity().supportFragmentManager,"DialogImage")
               }

           }

        binding.rvMessages.apply {
            manager= LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mAdapter=DialogRecyclerViewAdapter(messageResponses,author,requireContext(),onClickImage)
            layoutManager=manager
            adapter=mAdapter
            viewModel.changeProgressBarInstance()
        }
       viewModel.changeAdapterInstance()

    }

    private fun handleError(throwable: String) {
        Toast.makeText(requireContext(), throwable, Toast.LENGTH_LONG).show()
    }

    private  fun update(messageResponses: MutableList<MessageResponse>){
        mAdapter.addData(messageResponses,binding.rvMessages)

    }

    fun openGallery() {
        val galleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        resultLauncher.launch(galleryIntent)
    }
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val contentURI = data!!.data
            if (contentURI != null) {



                val dialogFragment= ImageDialog(contentURI,id)
                dialogFragment.show(requireActivity().supportFragmentManager,"imageDialog")
                }
            }
        }
    }







