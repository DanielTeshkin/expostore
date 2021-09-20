package com.expostore.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import com.expostore.R
import com.expostore.databinding.WebViewFragmentBinding

class WebViewFragment : Fragment() {

    private lateinit var binding: WebViewFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.web_view_fragment, container, false)
        binding.webView.webViewClient = WebViewClient()
        arguments?.getString("url")?.let { binding.webView.loadUrl(it) }

        return binding.root
    }
}