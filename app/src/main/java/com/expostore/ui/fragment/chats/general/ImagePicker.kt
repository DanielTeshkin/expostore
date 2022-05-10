package com.expostore.ui.fragment.chats.general

import com.expostore.R
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType

class ImagePicker {
    fun bottomSheetImageSetting(): BottomSheetImagePicker.Builder{
        return BottomSheetImagePicker.Builder("com.expostore.MyFileProvider")
            .cameraButton(ButtonType.Button)
            .galleryButton(ButtonType.Button)
            .multiSelect(1,4)
            .multiSelectTitles(R.plurals.pick_multi, R.plurals.pick_multi_more, R.string.pick_multi_limit)
            .requestTag("multi")

    }
}