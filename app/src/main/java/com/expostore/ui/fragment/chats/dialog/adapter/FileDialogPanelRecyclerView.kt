package com.expostore.ui.fragment.chats.dialog.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.expostore.databinding.FilePanelItemBinding
import com.expostore.databinding.TenderCreateImageItemBinding
import com.expostore.ui.fragment.profile.profile_edit.click
import java.nio.file.Files.size

class FileDialogPanelRecyclerView(private var images:MutableList<Uri>, private val context: Context):
    RecyclerView.Adapter<FileDialogPanelRecyclerView.FilePanelHolder>() {
    var removeUri:((Uri)->Unit)?=null
    inner class FilePanelHolder( val binding: FilePanelItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(uri: Uri, index: Int){
                 binding.filenameText.text= getFileName(uri)
            binding.floatingActionButton.click { removeUri?.invoke(uri)
            removeAt(index)
            }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilePanelHolder {
        return  FilePanelHolder(
            FilePanelItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FilePanelHolder, position: Int) {
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

fun getFileName(uri:Uri): String {
    var result = uri.path;
    val cut = result?.lastIndexOf ('/');
    if (cut != -1) {
        if (cut != null) {
            result = result?.substring(cut + 1)
        };
    }

    return result?:"new file"
}
