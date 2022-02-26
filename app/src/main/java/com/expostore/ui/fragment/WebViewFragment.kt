package com.expostore.ui.fragment

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.expostore.databinding.WebViewFragmentBinding
import com.expostore.ui.base.BaseFragment

class WebViewFragment : BaseFragment<WebViewFragmentBinding>(WebViewFragmentBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.webView.webViewClient = WebViewClient()
        arguments?.getString("url")?.let { binding.webView.loadUrl(it) }
    }
}