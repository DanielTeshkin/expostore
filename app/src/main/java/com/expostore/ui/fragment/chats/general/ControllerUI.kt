package com.expostore.ui.fragment.chats.general

import android.content.Context
import android.graphics.Bitmap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.model.chats.DataMapping.Message
import com.expostore.ui.fragment.chats.dialog.adapter.DialogRecyclerViewAdapter
import com.expostore.ui.fragment.chats.fragment.ImageDownload
import com.expostore.ui.fragment.chats.down
import com.expostore.utils.OnClickImage

class ControllerUI( val context: Context) {
    fun openImageFragment(bitmap: Bitmap): ImageDownload {
        return ImageDownload(bitmap)
    }
}
