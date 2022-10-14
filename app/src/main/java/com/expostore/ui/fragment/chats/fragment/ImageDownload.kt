package com.expostore.ui.fragment.chats.fragment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.expostore.databinding.FragmentImageDownloadBinding
import com.expostore.ui.fragment.chats.general.FileStorage


 class ImageDownload( val bitmap: Bitmap) : DialogFragment() {
    private lateinit var _binding:FragmentImageDownloadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDownloadBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(requireActivity()).load(bitmap).into(_binding.imageFull)
        _binding.buttonSave.setOnClickListener {
            FileStorage(requireContext()).saveImage(bitmap)
        }
    }


}