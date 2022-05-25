package com.expostore.ui.fragment.chats.dialog

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getchats.*
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.model.chats.DataMapping.ItemChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.model.chats.DataMapping.createMessage
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.state.ResponseState
import com.expostore.ui.fragment.chats.dialog.adapter.DialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage
import com.expostore.ui.fragment.chats.fragment.FileDialog
import com.expostore.ui.fragment.chats.fragment.ImageDialog
import com.expostore.ui.fragment.chats.general.ControllerUI
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.ImagePicker
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickImage
import dagger.hilt.android.AndroidEntryPoint
import kotlin.collections.ArrayList
/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
open class DialogFragment(val id: String, val author:String) : BaseFragment<DialogFragmentBinding>(DialogFragmentBinding::inflate),
    BottomSheetImage.OnImagesSelectedListener {
    private val viewModel: DialogViewModel by viewModels()
    private lateinit var manager: LinearLayoutManager
    private lateinit var adapterDialog: DialogRecyclerViewAdapter
    private lateinit var onClickImage:OnClickImage

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeViewModel()
    }
    override fun onStart() {
        super.onStart()
        init()
    }

    private fun subscribeViewModel() = viewModel.apply {
            updateMessages(id)
            subscribe(item) { handleState(it) }
            subscribe(message) { handleSent(it) }
        }

    private fun init() {
         onClickImage = object : OnClickImage {
            override fun click(bitmap: Bitmap) {
                ControllerUI(requireContext()).openImageFragment(bitmap)
                    .show(requireActivity().supportFragmentManager, "DialogImage") }
        }
        binding.apply {
            messageSend.click{sendMessage()}
            messageSendBtn.click{openGallery()}
            imageView4.click{openBottomMenu()}
        }
        textListener()
    }

    private fun textListener() =  binding.apply { etInput.textChange(messageSend, imageView4) }


    private fun sendMessage() {
        val message = MessageRequest(text = binding.etInput.text.toString())
        viewModel.sentMessageOrUpdate(id, message)
             adapterDialog.addMessage(
                 createMessage(binding.etInput.text.toString(),author,ArrayList())
             )
        binding.rvMessages.down(adapterDialog.itemCount)
        binding.etInput.text.clear()
    }

    private fun openGallery()=ImagePicker().bottomSheetImageSetting().show(childFragmentManager)

    private fun handleSent(state: ResponseState<MessageRequest>) = when (state) {
            is ResponseState.Success -> binding.rvMessages.down(adapterDialog.itemCount)
            is ResponseState.Error -> handleError(state.throwable.message!!)
        else -> {}
    }

    private fun handleState(state: ResponseState<ItemChat>) = when (state) {
            is ResponseState.Loading -> handleLoading()
            is ResponseState.Error -> handleError(state.throwable.message!!)
            is ResponseState.Success -> loadOrUpdate(state.item.messages)
        }

    private fun handleLoading() = viewModel.instanceProgressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar2.visible(it)
        })


    private fun loadOrUpdate(messageResponses: MutableList<Message>) = viewModel
        .instanceAdapter
        .observe(viewLifecycleOwner, Observer {
            when (it) {
                false -> load(messageResponses)
                true -> update(messageResponses)}})


    private fun load(messages: MutableList<Message>) {
        manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapterDialog = DialogRecyclerViewAdapter(messages, author, requireContext(), onClickImage)
        binding.rvMessages.apply {
            install(manager,adapterDialog)
            down(adapterDialog.itemCount)
        }
        viewModel.apply { changeProgressBarInstance()
      changeAdapterInstance()}
    }

    private fun update(messages: MutableList<Message>) = adapterDialog.addData(messages)
    private fun openBottomMenu() = resultLauncher.launch(FileStorage(requireContext()).openStorage())
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val a = result.data?.clipData
                if (a != null) {
                    val fragment = FileDialog(a.listPath(), id)
                    fragment.show(requireActivity().supportFragmentManager, "file")
                } else {
                    val list = ArrayList<Uri>()
                    result.data?.data?.let { list.add(it) }
                    val fragment = FileDialog(list, id)
                    fragment.show(requireActivity().supportFragmentManager, "file")
                }
            }
        }

    override fun onImagesSelected(uris: MutableList<Uri>, tag: String?) = ImageDialog(uris , id)
        .show(requireActivity().supportFragmentManager, "image")

    }









