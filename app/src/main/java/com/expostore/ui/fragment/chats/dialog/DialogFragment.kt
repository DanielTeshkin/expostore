package com.expostore.ui.fragment.chats.dialog



import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.api.pojo.getchats.*
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.model.chats.DataMapping.ItemChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.base.BaseFragment

import com.expostore.ui.fragment.chats.listPath
import com.expostore.ui.state.ResponseState
import com.expostore.ui.fragment.chats.dialog.adapter.DialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.down
import com.expostore.ui.fragment.chats.fragment.FileDialog
import com.expostore.ui.fragment.chats.fragment.ImageDialog
import com.expostore.ui.fragment.chats.general.ControllerUI
import com.expostore.ui.fragment.chats.general.FileStorage
import com.expostore.ui.fragment.chats.general.ImagePicker
import com.expostore.ui.fragment.chats.visible
import com.expostore.utils.OnClickImage
import com.kroegerama.imgpicker.BottomSheetImagePicker
import dagger.hilt.android.AndroidEntryPoint

import kotlin.collections.ArrayList


/**
 * @author Teshkin Daniel
 */
@AndroidEntryPoint
open class DialogFragment(val id: String, val author:String) : BaseFragment<DialogFragmentBinding>(DialogFragmentBinding::inflate),
    BottomSheetImagePicker.OnImagesSelectedListener {
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
        init()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopUpdate()
    }

    private fun subscribeViewModel() {
        viewModel.apply {
            subscribe(item) { handleState(it) }
            subscribe(message) { handleSent(it) }
        }
    }

    private fun init() {
        binding.apply {
            messageSend.setOnClickListener { sendMessage() }
            messageSendBtn.setOnClickListener { openGallery() }
            imageView4.setOnClickListener { openBottomMenu() }
        }
        textListener()
    }

    private fun textListener() {
        binding.apply {
            etInput.addTextChangedListener {
                val stroke = etInput.text.toString()
                messageSend.visible(stroke.isNotEmpty())
                imageView4.visible(stroke.isEmpty())

            }
        }
    }

    private fun sendMessage() {
        val message = MessageRequest(text = binding.etInput.text.toString())
        viewModel.sentMessageOrUpdate(id, message)
        mAdapter.addMessage(
            Message(
                text = binding.etInput.text.toString(),
                author = author,
                images = ArrayList()
            )
        )
        binding.rvMessages.down(mAdapter.itemCount)
        binding.etInput.text.clear()
    }

    @SuppressLint("ResourceType")
    private fun openGallery() {
        ImagePicker()
            .bottomSheetImageSetting()
            .show(childFragmentManager)

    }

    private fun handleSent(state: ResponseState<MessageRequest>) {
        when (state) {
            is ResponseState.Success -> binding.rvMessages.down(mAdapter.itemCount)
        }
    }

    private fun handleState(state: ResponseState<ItemChat>) {
        when (state) {
            is ResponseState.Loading -> handleLoading()
            is ResponseState.Error -> handleError(state.throwable.message!!)
            is ResponseState.Success -> loadOrUpdate(state.item.messages)
        }
    }

    private fun handleLoading() {
        viewModel.instanceProgressBar.observe(viewLifecycleOwner, Observer {
            binding.progressBar2.visible(it)
        })
    }

    private fun loadOrUpdate(messageResponses: MutableList<Message>) {
        viewModel.instanceAdapter.observe(viewLifecycleOwner, Observer {
            when (it) {
                false -> load(messageResponses)
                true -> update(messageResponses)
            }
        })

    }

    private fun load(messages: MutableList<Message>) {
        val onClickImage = object : OnClickImage {
            override fun click(bitmap: Bitmap) {
                ControllerUI(requireContext()).openImageFragment(bitmap)
                    .show(requireActivity().supportFragmentManager, "DialogImage")
            }
        }
        binding.rvMessages.apply {
            manager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            mAdapter =
                DialogRecyclerViewAdapter(messages, author, requireContext(), onClickImage)
            layoutManager = manager
            adapter = mAdapter

        }
        viewModel.changeProgressBarInstance()
        binding.rvMessages.down(mAdapter.itemCount)
        viewModel.changeAdapterInstance()

    }

    private fun handleError(throwable: String) {
        Toast.makeText(requireContext(), throwable, Toast.LENGTH_LONG).show()
    }

    private fun update(messages: MutableList<Message>) {
        mAdapter.addData(messages)

    }

    private fun openBottomMenu() {
        val intent = FileStorage(requireContext())
            .openStorage()
        resultLauncher.launch(intent)
    }

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

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
                val fragment = ImageDialog(uris as ArrayList<Uri>, id)
                fragment.show(requireActivity().supportFragmentManager, "image")


            }


}









