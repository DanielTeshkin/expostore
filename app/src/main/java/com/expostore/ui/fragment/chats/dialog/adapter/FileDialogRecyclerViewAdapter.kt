package com.expostore.ui.fragment.chats.dialog.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.FileItemBinding

class FileDialogRecyclerViewAdapter(private val files: ArrayList<Uri>, val context: Context): RecyclerView.Adapter<FileDialogRecyclerViewAdapter.FileDialogViewHolder>() {

    class FileDialogViewHolder( val binding: FileItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(chatFile: Uri){
            if (chatFile.lastPathSegment.toString().length<=8){
            binding.nameFile.text=chatFile.lastPathSegment.toString()}
            else{binding.nameFile.text="Новый файл" }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileDialogViewHolder {
        return FileDialogViewHolder(FileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FileDialogViewHolder, position: Int) {
        holder.bind(files[position])

    }

    override fun getItemCount(): Int {
        return files.size
    }


}