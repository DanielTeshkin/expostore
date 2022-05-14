package com.expostore.ui.fragment.chats.dialog.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.databinding.GalleryItemBinding

class ImageDialogRecyclerViewAdapter(private val images:MutableList<Uri>, private val context: Context):
        RecyclerView.Adapter<ImageDialogRecyclerViewAdapter.ImageViewHolder>() {
        inner class ImageViewHolder( val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind(uri:Uri,index: Int){
                Glide.with(context).load(uri).override(400,800).centerCrop().into(binding.imageGallery)
                binding.floatingActionButton.setOnClickListener {
                    removeAt(index)
                }

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            return ImageViewHolder(
                GalleryItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }

        override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
            holder.bind(images[position],position)
        }

        override fun getItemCount(): Int {
            return images.size
        }
    fun removeAt(index: Int) {
        images.removeAt(index)
        notifyItemRemoved(index)
        notifyItemRangeChanged(index,itemCount);
    }
    }
