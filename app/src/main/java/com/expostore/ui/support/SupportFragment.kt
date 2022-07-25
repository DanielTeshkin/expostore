package com.expostore.ui.support

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.expostore.databinding.SupportFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.fragment.profile.profile_edit.click


class SupportFragment : BaseFragment<SupportFragmentBinding>(SupportFragmentBinding::inflate) {

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
       binding.btnEditProfile.click {
          val intent = Intent(Intent.ACTION_SEND)
          intent.type = "plain/text"
          intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("service.ibuyer@yandex.ru"))

          intent.putExtra(Intent.EXTRA_TEXT, binding.message.text.toString())
          startActivity(Intent.createChooser(intent, ""))
       }




   }

}