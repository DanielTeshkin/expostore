package com.expostore.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebViewClient
import com.expostore.databinding.WebViewFragmentBinding
import com.expostore.ui.base.fragments.BaseFragment


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
        val format= WebViewFragmentArgs.fromBundle(requireArguments()).format
        when {
            (url != null) and (format=="file") -> loadPage("https://docs.google.com/viewer?url=$url")
            (url != null) and (format!="file") -> url?.let { loadPage(it) }
            else -> loadPage("https://expostore.ru/")
        }
    }

    fun loadPage(path:String)= binding.webView.loadUrl(path)

}