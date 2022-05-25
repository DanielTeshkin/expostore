package com.expostore.ui.fragment.chats.general

import com.expostore.R
import com.expostore.ui.fragment.chats.dialog.bottom.BottomSheetImage
import com.expostore.ui.fragment.chats.dialog.bottom.ButtonType



class ImagePicker {
    fun bottomSheetImageSetting(): BottomSheetImage.Builder{
        return BottomSheetImage.Builder("com.expostore.MyFileProvider")
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .multiSelect(1,4)
            .multiSelectTitles(R.plurals.pick_multi, R.plurals.pick_multi_more, R.string.pick_multi_limit)
            .requestTag("multi")

    }
    fun bottomSheetSingleImageSetting(): BottomSheetImage.Builder{
        return BottomSheetImage.Builder("com.expostore.MyFileProvider")
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
    }
}