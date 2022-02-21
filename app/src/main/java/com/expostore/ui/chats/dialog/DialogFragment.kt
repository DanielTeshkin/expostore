package com.expostore.ui.chats.dialog

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.expostore.R
import com.expostore.databinding.DialogFragmentBinding
import com.expostore.ui.base.BaseFragment

class DialogFragment : BaseFragment<DialogFragmentBinding>(DialogFragmentBinding::inflate){

    companion object {
        fun newInstance() = DialogFragment()
    }

    private lateinit var viewModel: DialogViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DialogViewModel::class.java)
    }

}