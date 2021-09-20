package com.expostore.ui.open

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.databinding.OpenFragmentBinding

class OpenFragment : Fragment() {

    private lateinit var binding: OpenFragmentBinding
    private lateinit var openViewModel: OpenViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.open_fragment, container, false)
        openViewModel = ViewModelProvider(this).get(OpenViewModel::class.java)
        binding.openVM = openViewModel

        (context as MainActivity).navView.visibility = View.GONE

        //TODO Добавить в клик кнопки выхода
        //(context as MainActivity).sharedPreferences.edit().clear().apply()


        return binding.root
    }
}