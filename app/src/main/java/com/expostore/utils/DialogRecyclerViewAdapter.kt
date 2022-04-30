package com.expostore.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.expostore.api.pojo.getchats.MessageResponse
import com.expostore.databinding.ImageReceviedItemBinding
import com.expostore.databinding.ImageSentItemBinding
import com.expostore.databinding.MessageReceivedItemBinding
import com.expostore.databinding.MessageSentItemBinding
import com.expostore.ui.fragment.chats.down
import com.expostore.ui.fragment.chats.loadImage
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class DialogRecyclerViewAdapter(private val messageResponses:MutableList<MessageResponse>,
                                private val username:String, val context: Context,
                                val onClickImage: OnClickImage)
    :RecyclerView.Adapter<DialogRecyclerViewAdapter.DialogViewHolder>() {
    abstract class DialogViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(item: MessageResponse)

    }

    override fun getItemViewType(position: Int): Int {
        Log.i("id_user",username)
        return if(messageResponses[position].author==username&&messageResponses[position].imageResponsImages?.size!=0) 1
        else if(messageResponses[position].author!=username&&messageResponses[position].imageResponsImages?.size!=0) 0
        else if(messageResponses[position].author==username&&messageResponses[position].imageResponsImages?.size==0) 3
        else 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        return when (viewType) {
            0 -> RevImageViewHolder(
                ImageReceviedItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            1 -> SentImageViewHolder(
                ImageSentItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            2->RevViewHolder(
                MessageReceivedItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            3->SentViewHolder(  MessageSentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
            else -> throw IllegalArgumentException("Error")
        }
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {

        when (holder) {
            is SentViewHolder -> holder.bind(messageResponses[position])
            is RevImageViewHolder->holder.bind(messageResponses[position])
            is SentImageViewHolder ->holder.bind(messageResponses[position])
            is RevViewHolder -> holder.bind(messageResponses[position])
        }

    }

    override fun getItemCount(): Int {
        return messageResponses.size
    }

    inner class SentViewHolder(val binding: MessageSentItemBinding) :
        DialogViewHolder(binding.root) {
        override fun bind(item: MessageResponse) {
            val textView = binding.textMessageSent
            textView.text = item.text

        }
    }

    inner class RevViewHolder(val binding: MessageReceivedItemBinding) :
        DialogViewHolder(binding.root) {
        override fun bind(item: MessageResponse) {
            val textView = binding.textMessageReceived
            textView.text = item.text

        }
    }
    inner class SentImageViewHolder(val binding: ImageSentItemBinding) :
        DialogViewHolder(binding.root) {
        override fun bind(item: MessageResponse) {
            val textView = binding.textMessageS
            textView.text = item.text
            if (item.imageResponsImages?.size != 0)
               binding.imageS.loadImage(item.imageResponsImages?.get(0)?.file!!)
            binding.imageS.setOnClickListener {
                val drawable = binding.imageS.drawable
                val bitmap:Bitmap=drawable.toBitmap()
            onClickImage.click(bitmap)

            }

        }
        }

    inner class RevImageViewHolder(val binding: ImageReceviedItemBinding) :
        DialogViewHolder(binding.root) {
        override fun bind(item: MessageResponse) {
            val textView = binding.textImage
            textView.text = item.text
            if (item.imageResponsImages?.size != 0) {
                binding.imageRec.loadImage(item.imageResponsImages?.get(0)?.file!!)
                binding.imageRec.setOnClickListener {
                    val drawable = binding.imageRec.drawable
                    val bitmap:Bitmap=drawable.toBitmap()
                    onClickImage.click(bitmap)
                }

            }
        }
    }

        fun addData(listItems: MutableList<MessageResponse>, recyclerView: RecyclerView) {
            val size = messageResponses.size
            messageResponses.clear()
            messageResponses.addAll(listItems)
            val sizeNew = messageResponses.size
            notifyItemRangeChanged(size, sizeNew)
            if (sizeNew > size) recyclerView.down(itemCount)
        }

        fun putMessage(messageResponse: MessageResponse, recyclerView: RecyclerView) {
            val size = messageResponses.size
            messageResponses.clear()
            messageResponses.add(messageResponse)
            val sizeNew = messageResponses.size
            notifyItemRangeChanged(size, sizeNew)
            if (sizeNew > size) recyclerView.down(itemCount)
        }
    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String? = null
        val imageFileName = "JPEG_" + "FILE_NAME" + ".jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/YOUR_FOLDER_NAME"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            // Add the image to the system gallery
            galleryAddPic(savedImagePath)
           // Toast.makeText(mContext, "IMAGE SAVED", Toast.LENGTH_LONG).show()
        }
        return savedImagePath
    }

    private fun galleryAddPic(imagePath: String?) {
        val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val f = File(imagePath)
        val contentUri: Uri = Uri.fromFile(f)
        mediaScanIntent.data = contentUri
            // sendBroadcast(mediaScanIntent)
    }


}