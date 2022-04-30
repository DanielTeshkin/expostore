package com.expostore.ui.fragment.chats.dialog

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.expostore.api.pojo.getchats.MessageRequest
import com.expostore.api.pojo.saveimage.SaveImageRequestData
import com.expostore.api.pojo.saveimage.SaveImageResponseData
import com.expostore.databinding.FragmentImageDialogBinding
import com.expostore.ui.state.ResponseState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.search_filter_fragment.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
open class ImageDialog(val uri: Uri?, val id:String) : DialogFragment() {
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

        _binding.progressBar.visibility=View.INVISIBLE

        Glide.with(requireContext()).load(uri).override(900,1200).centerCrop().into( _binding.imageView2)

        _binding.back.setOnClickListener {
            dismiss()
        }
       _binding.messageSen.setOnClickListener {
           val bitmap = _binding.imageView2.drawable.toBitmap()

            val path = ImageMessage().encode(bitmap)
            dialogViewModel.apply {
              saveMessage(SaveImageRequestData(path!!, "png"))
                single_subscribe(save) { handleImage(it) }
            }
        }
    }
    private fun handleImage(it: ResponseState<SaveImageResponseData>) {
        when(it){
              is ResponseState.Loading ->  progresbar()
            //  is ResponseState.Error ->  handleError(state.throwable.message!!)
            is ResponseState.Success -> sendImage(it.item.id)}
    }



    private fun <T> single_subscribe(flow: Flow<T>, action: suspend (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launch {
            flow.collect(action)
        }
    }


    private  fun sendImage(url:String){
        val list= ArrayList<String>()
        Log.i("my_id",url)
        list.add(url)

        val text=_binding.send.text.toString()
        val message =  MessageRequest(text=text, images = list)
        dialogViewModel.sentMessageOrUpdate(id, message)
        dialogViewModel.apply {
            single_subscribe(response){handle(it)}
        }
    }

    private fun handle(state: ResponseState<MessageRequest>) {
        when(state){
           is ResponseState.Success -> succsess()
        }

    }

    private fun succsess() {

        dismiss()
    }

    fun progresbar(){
        _binding.progressBar.visibility=View.VISIBLE
    }


}