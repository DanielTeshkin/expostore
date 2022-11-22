package com.expostore.ui.fragment.chats.dialog.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.ImageItemsBinding
import com.expostore.ui.fragment.chats.loadImage
import com.expostore.utils.OnClickImage

class ImageMessageRecyclerViewAdapter( private val images:ArrayList<String>,  val onClickImage: OnClickImage, private val type:String):
    RecyclerView.Adapter<ImageMessageRecyclerViewAdapter.ImageViewHolder>() {
   inner class ImageViewHolder( val binding:ImageItemsBinding) : RecyclerView.ViewHolder(binding.root){
       fun bind(url:String){
           if(type=="network") binding.messageImage.loadImage(url)
           else binding.messageImage.loadImage(Uri.parse(url))

       }

    }

    override fun onViewAttachedToWindow(holder: ImageViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.apply {
            binding.messageImage.setOnClickListener {
                val drawable = binding.messageImage.drawable
                val bitmap= drawable.toBitmap()
                onClickImage.click(bitmap)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
         return ImageViewHolder(
             ImageItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
       holder.bind(images[position])
    }

    override fun getItemCount(): Int {
        return images.size
    }
}