package com.expostore.ui.fragment.chats.fragment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.expostore.databinding.FragmentImageDownloadBinding
import com.expostore.ui.fragment.chats.general.FileStorage


open class ImageDownload( val bitmap: Bitmap) : DialogFragment() {
    lateinit var _binding:FragmentImageDownloadBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            androidx.fragment.app.DialogFragment.STYLE_NORMAL,
            android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImageDownloadBinding.inflate(inflater, container, false)
        return _binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding.imageFull.setImageBitmap(bitmap)

        _binding.imageFull.setOnClickListener {
            val alertDialog = AlertDialog.Builder(requireContext())
                .setCancelable(true)
                .setNeutralButton("Cкачать") { _, _ ->
                    FileStorage(requireContext()).saveImage(bitmap)
                }
            alertDialog.apply {
                create()
                show()
            }
        }
    }


}