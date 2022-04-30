package com.expostore.ui.fragment.chats.dialog

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class FileStorage(val context: Context) {

    fun saveImage(bitmap:Bitmap) {
        val imageOutStream: OutputStream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "image_.jpg")
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                values.put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    Environment.DIRECTORY_PICTURES
                )
                val uri: Uri? = context.contentResolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    values
                )
                imageOutStream =context.contentResolver?.openOutputStream(uri!!)!!
            }else {
                val imagesDir =
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        .toString()
                val image = File(imagesDir, "image_new")
                imageOutStream= FileOutputStream(image)
            }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
            imageOutStream.close()
            Toast.makeText(context, "save", Toast.LENGTH_LONG).show()

        }

    }
