package com.expostore.ui.fragment.chats.fragment

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.expostore.data.remote.api.pojo.getchats.FileOrImageMessage
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveImageRequestData
import com.expostore.databinding.FragmentImageDialogBinding

import com.expostore.ui.state.ResponseState
import com.expostore.ui.fragment.chats.dialog.adapter.ImageDialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.general.ImageMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ImageDialog(val list: MutableList<Uri>, val id:String) : DialogFragment() {
     private val dialogViewModel: ImageDialogView by viewModels()
      lateinit var _binding:FragmentImageDialogBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImageDialogBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding.rec.apply {
            val gridLayoutManager=GridLayoutManager(requireContext(),2)
            val mAdapter= ImageDialogRecyclerViewAdapter(list,requireContext())
            adapter=mAdapter
            layoutManager=gridLayoutManager
        }
        _binding.progressBar.visibility=View.INVISIBLE
        val bitmapList=ArrayList<Bitmap>()
        list.map{
            Glide.with(requireContext()).asBitmap().load(it).centerCrop().into(object :
                CustomTarget<Bitmap>(){
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    bitmapList.add(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {

                }

            })
        }

        _binding.messageSen.setOnClickListener {
           val path= ImageMessage().encodeBitmapList(bitmapList)

            val list = mutableListOf<SaveImageRequestData>()
            dialogViewModel.apply {
                path.map { list.add(SaveImageRequestData(it,"png"))  }
                saveMessage(list)
                single_subscribe(save) {
                    when (it) {
                        is ResponseState.Loading -> _binding.progressBar.visibility=View.VISIBLE
                        is ResponseState.Success -> {
                            sendImage(it.item.id)
                            Toast.makeText(requireContext(), "Идёт загрузка...", Toast.LENGTH_LONG).show()
                        }
                        is ResponseState.Error -> Toast.makeText(requireContext(),it.throwable.message , Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
    }




    private fun <T> single_subscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect(action)
        }
    }


    private  fun sendImage(list:List<String>){
        val text=_binding.send.text.toString()
         if(text.isNotEmpty()){

            dialogViewModel.sentMessageOrUpdate(id, MessageRequest(text =text, images = list as ArrayList<String>) )
        }
        else{
             dialogViewModel.sendFileOrImage(id,FileOrImageMessage(images = list as ArrayList<String>) )
        }

        dialogViewModel.apply {
            single_subscribe(response){handle(it)}
        }
    }

    private fun handle(state: ResponseState<MessageRequest>) {
        when(state){
           is ResponseState.Success -> success()

        }

    }

    private fun success() {
        dismiss()
    }




}