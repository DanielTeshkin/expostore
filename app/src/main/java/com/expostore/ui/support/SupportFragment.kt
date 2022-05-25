package com.expostore.ui.support

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.expostore.databinding.SupportFragmentBinding
import com.expostore.ui.base.BaseFragment
import com.expostore.ui.support.utils.GMailSender

class SupportFragment : BaseFragment<SupportFragmentBinding>(SupportFragmentBinding::inflate) {

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      val sender = GMailSender(
         "you_email@gmail.com",
         "password"
      )

      sender.sendMail(binding.subjectEditText.text.toString(), binding.messageEditText.text.toString(), sender)
   }

}