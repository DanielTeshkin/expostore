package com.expostore.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.expostore.databinding.WebViewFragmentBinding
import com.expostore.ui.base.BaseFragment

class WebViewFragment : BaseFragment<WebViewFragmentBinding>(WebViewFragmentBinding::inflate) {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val webView=binding.webView
        webView.webViewClient = WebViewClient()

        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = false;

        webView.settings.setSupportMultipleWindows(false);
        webView.settings.setSupportZoom(false);
        webView.isVerticalScrollBarEnabled = false;
        webView.isHorizontalScrollBarEnabled = false;
        val url= WebViewFragmentArgs.fromBundle(requireArguments()).url
        Log.i("url",url?:"")
        if (url != null) {
            binding.webView.loadUrl("https://docs.google.com/viewer?url=$url")
        }
    }
}