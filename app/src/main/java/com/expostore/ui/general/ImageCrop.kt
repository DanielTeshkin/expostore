package com.expostore.ui.general



import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.Fragment
import com.github.dhaval2404.imagepicker.ImagePicker

class ImagePick() {
    fun start(view: Fragment, action: ActivityResultLauncher<Intent>){
        ImagePicker.with(view)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .createIntent { intent -> action.launch(intent) }
    }
}