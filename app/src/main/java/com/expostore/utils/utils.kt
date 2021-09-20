package com.expostore.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView

fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

fun makeLinks(textView: TextView, links: Array<String>, clickableSpans: Array<ClickableSpan>) {
    val spannableString = SpannableString(textView.text)

    for (i in links.indices) {
        val clickableSpan = clickableSpans[i]
        val link = links[i]

        val startIndexOfLink = textView.text.indexOf(link)

        spannableString.setSpan(
            clickableSpan,
            startIndexOfLink,
            startIndexOfLink + link.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    textView.movementMethod = LinkMovementMethod.getInstance()
    textView.setText(spannableString, TextView.BufferType.SPANNABLE)
}

