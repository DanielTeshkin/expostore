package com.expostore.ui.fragment.chats.dialog.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.databinding.GalleryItemBinding
import com.expostore.databinding.TenderCreateImageItemBinding
import com.expostore.ui.fragment.profile.profile_edit.click

class ImageDialogRecyclerViewAdapter(private val images:MutableList<Uri>, private val context: Context):
        RecyclerView.Adapter<ImageDialogRecyclerViewAdapter.ImageViewHolder>() {
    var removeUri:((Uri)->Unit)?=null
        inner class ImageViewHolder( val binding: TenderCreateImageItemBinding) : RecyclerView.ViewHolder(binding.root){
            fun bind(uri:Uri,index: Int){
                Glide.with(context).load(uri).centerCrop().into(binding.tenderImage)

                binding.deleteImage.apply{
                    visibility= View.VISIBLE
                    click {
                        removeUri?.invoke(uri)
                        removeAt(index)
                    }
                }

            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
            return ImageViewHolder(
                TenderCreateImageItemBinding.inflate(
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

    fun addData(list:List<Uri>){
        images.addAll(list)
        notifyItemRangeChanged(0,itemCount);
    }
    }
