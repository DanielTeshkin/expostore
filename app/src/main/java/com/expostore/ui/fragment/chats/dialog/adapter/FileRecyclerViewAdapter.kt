package com.expostore.ui.fragment.chats.dialog.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.FileElementBinding
import com.expostore.model.chats.DataMapping.FileChat
import com.expostore.ui.fragment.chats.general.FileStorage

class FileRecyclerViewAdapter(private val files: ArrayList<FileChat>, val context: Context): RecyclerView.Adapter<FileRecyclerViewAdapter.FileViewHolder>() {

    class FileViewHolder( val binding:FileElementBinding):RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(chatFile: FileChat){
            val uri=Uri.parse(chatFile.file)
            if(chatFile.name.length<=8){
            binding.filename.text=chatFile.name
            }else binding.filename.text ="новый файл"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
             return FileViewHolder(FileElementBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.bind(files[position])
        holder.binding.file.setOnClickListener {
            FileStorage(context).saveFile(files[position].file,files[position].name)
        }
    }

    override fun getItemCount(): Int {
       return files.size
    }


}