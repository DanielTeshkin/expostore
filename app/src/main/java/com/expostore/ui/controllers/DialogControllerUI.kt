package com.expostore.ui.controllers

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.view.View
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.expostore.data.remote.api.pojo.getchats.MessageRequest
import com.expostore.data.remote.api.pojo.saveimage.SaveFileRequestData
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.model.ImageModel
import com.expostore.model.chats.DataMapping.FileChat
import com.expostore.model.chats.DataMapping.Message
import com.expostore.model.chats.DataMapping.createMessage
import com.expostore.ui.fragment.chats.*
import com.expostore.ui.fragment.chats.dialog.adapter.DialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.dialog.adapter.ImageDialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.dialog.adapter.FileDialogPanelRecyclerView
import com.expostore.ui.fragment.chats.dialog.adapter.getFileName
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

    private val fileAdapter:FileDialogPanelRecyclerView by lazy { FileDialogPanelRecyclerView(files,context) }
    private var progressState=true
    var adapterState=false
    var flagType = false
    init {
       adapterMultimedia.removeUri= { mapImages.remove(it)
       checkImageState()
       }
        messagesView.install(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true), adapterMessage)
        multimediaView.apply {
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=adapterMultimedia
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
    fun setupVisibleControllerForTextInput(action: (String) -> Unit) = binding.etInput.addTextChangedListener {
                if (multimedia.isEmpty()&&files.isEmpty()) {
                    binding.messageSend.visible(it.toString().isNotEmpty())
                    binding.imageView4.visible(it.toString().isEmpty())
                    action.invoke(it.toString())
                } else sendActivate()
            }



    private fun sendActivate()= binding.apply {
        messageSend.visibility = View.VISIBLE
        imageView4.visibility = View.GONE
    }
    private fun setupPassive()=binding.apply {
        messageSend.visibility = View.GONE
        imageView4.visibility = View.VISIBLE
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

    private fun addMessageWithImage()=adapterMessage.addMessage(
        createMessage(binding.etInput.text.toString(),author, images = multimedia.map { ImageModel(id="", file = it.toString()) })
    )
    private fun addMessageWithFile()=adapterMessage.addMessage(
    createMessage(binding.etInput.text.toString(),author, listOf(),files.map { FileChat(id="", file = it.toString(),name= getFileName(it)) })
    )

    private fun sendMessage(action: (String, MessageRequest) -> Unit,
                            loadImageAndSend: (List<Bitmap>) -> Unit,
                            loadFileAndSend: (List<SaveFileRequestData>)->Unit){
        when(multimedia.isEmpty()&&files.isEmpty()){
            true->sendText(action)
            false->{
                when(flagType) {
                    false-> {
                        addMessageWithImage()
                        loadImageAndSend.invoke(mapImages.entries.map { it.value })
                    }
                    true-> {
                        addMessageWithFile()
                        loadFileAndSend.invoke(saveFile())
                    }
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
        fileAdapter.removeUri={checkState()}
        Log.i("dok",fileAdapter.itemCount.toString())
            // files.clear()
        fileAdapter.addData(uris)
        filesView.apply {
            layoutManager= LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter=fileAdapter
        }

        flagType=true
        Log.i("dok",fileAdapter.itemCount.toString())

        sendActivate()
    }
    fun checkState(){
        if(files.isEmpty()) setupPassive()
    }
    fun checkImageState(){
        if(mapImages.isEmpty()) setupPassive()
    }


    fun imagesRv(uris: MutableList<Uri>, tag: String?){
        visiblePanelMultimedia(true)
        adapterMultimedia.addData(uris)
        saveImageLocal()
        sendActivate()

    } }
