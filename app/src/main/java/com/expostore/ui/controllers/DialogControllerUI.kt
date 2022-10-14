package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.model.chats.DataMapping.Message
import com.expostore.model.chats.DataMapping.createMessage
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.chats.dialog.adapter.DialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.dialog.adapter.ImageDialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.dialog.adapter.FileDialogPanelRecyclerView
import com.expostore.ui.fragment.chats.general.ImagePicker
import com.expostore.ui.fragment.chats.general.PagerChatRepository
import com.expostore.ui.fragment.profile.profile_edit.click
import com.expostore.utils.OnClickImage

class DialogControllerUI(context: Context,
                         private val binding:DialogFragmentBinding,
                         private val author:String,
                         private val id:String, private  val fragmentManager: FragmentManager) : ControllerUI(context),OnClickImage  {
    private val imagePicker=ImagePicker().bottomSheetImageSetting().build()
    private val messagesView = binding.rvMessages
    private val multimediaView = binding.rvImages
    private val filesView=binding.rvFiles
    private val messages: MutableList<Message> = mutableListOf()
    private val adapterMessage by lazy {DialogRecyclerViewAdapter(messages,author,context,this)  }
    private val adapterMultimedia by lazy { ImageDialogRecyclerViewAdapter(multimedia,context) }

    private val fileAdapter:FileDialogPanelRecyclerView by lazy { FileDialogPanelRecyclerView(multimedia,context) }
    private var progressState=true
    var adapterState=false
    var flagType = false
    init {
       adapterMultimedia.removeUri= { mapImages.remove(it) }
        messagesView.install(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true), adapterMessage)

        multimediaView.apply {
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=adapterMultimedia
        }
        fileAdapter.removeUri={multimedia.remove(it)}
        filesView.apply {
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=fileAdapter
        }

    }

    fun initUI(
        action: (String, MessageRequest) -> Unit,
        loadImageAndSend: (List<Bitmap>) -> Unit,
        manager: FragmentManager,
        loadFileAndSend: (List<SaveFileRequestData>) -> Unit
    ){
        binding.apply {
            messageSend.click{sendMessage(action,loadImageAndSend,loadFileAndSend)}
            messageSendBtn.click{openGallery(manager)}
            imageView4.click { PagerChatRepository.getInstance().getOpenFileState().value= true}
        }
    }
    fun sendImages(action: (String) -> Unit){
        clearMap()
        multimedia.clear()
        action.invoke(id)
    }
    fun progressBarState()= binding.progressBar2.visible(progressState)
    fun setupVisibleControllerForTextInput(action: (String) -> Unit) {
        binding.apply {
            etInput.addTextChangedListener {
                if (multimedia.isNotEmpty()) {
                    messageSend.visible(it.toString().isNotEmpty())
                    imageView4.visible(it.toString().isEmpty())
                    action.invoke(it.toString())
                } else sendActivate()
            }
        }
    }

    private fun sendActivate(){
    binding.apply {
        messageSend.visibility = View.VISIBLE
        imageView4.visibility = View.GONE
    }

    }
    private fun initMessagesList(list:List<Message>) {
        messages.addAll(list)
        messagesView.down(adapterMessage.itemCount)
        progressState=false
        adapterState=true
    }
    fun clearInput() = binding.etInput.text.clear()
    private fun addMessage() =
        adapterMessage.addMessage(
            createMessage(
                binding.etInput.text.toString(),
                author,
                ArrayList()
            )
        )

    private fun sendMessage(action: (String, MessageRequest) -> Unit,
                            loadImageAndSend: (List<Bitmap>) -> Unit,
                            loadFileAndSend: (List<SaveFileRequestData>)->Unit){
        when(multimedia.isEmpty()){
            true->sendText(action)
            false->{
                when(flagType) {
                    false->  loadImageAndSend.invoke(mapImages.entries.map { it.value })
                    true->  loadFileAndSend.invoke(saveFile())
                }
            }
        }
    }

    private fun sendText(action: (String, MessageRequest) -> Unit){
        val message = MessageRequest(text = binding.etInput.text.toString())
        action.invoke(id,message)
        addMessage()
        messagesView.down(0)
        clearInput()
    }
    fun loadOrUpdate(messages: List<Message>)=
        when (adapterState) {
            false -> initMessagesList(messages)
            true -> addMessages(messages as MutableList<Message>)}

     private fun visiblePanelMultimedia(state: Boolean) = binding.images.visible(state)
     fun visiblePanelFiles(state: Boolean) = binding.files.visible(state)
     private fun addMessages(messages:MutableList<Message>)=adapterMessage.addData(messages)
     fun loadingImage(){
        visiblePanelMultimedia(false)
     }



    fun dismissPicker()=imagePicker.hide()
    private fun clearMap()=mapImages.clear()
    private fun openGallery(fragmentManager: FragmentManager)= imagePicker.show(fragmentManager,"load")
    override fun click(bitmap: Bitmap) = openImageFragment(bitmap).show(fragmentManager, "DialogImage")

    fun filesRv(uris:MutableList<Uri>){
        visiblePanelFiles(true)
            fileAdapter.addData(uris)
            flagType=true
        sendActivate()
    }

    fun imagesRv(uris: MutableList<Uri>, tag: String?){
        visiblePanelMultimedia(true)
        adapterMultimedia.addData(uris)
        saveImageLocal()
        sendActivate()

    } }
