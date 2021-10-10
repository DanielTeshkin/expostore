package com.expostore.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import java.io.ByteArrayOutputStream
import java.io.InputStream

// Скрытие клавиатуры
fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

// Создание ссылок в тексте с выделением
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

// Кодирование изображения в формат строки Base 64
fun encodeImage(inputStream: InputStream): String{
    val baos = ByteArrayOutputStream()
    val bitmap = BitmapFactory.decodeStream(inputStream)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageBytes: ByteArray = baos.toByteArray()
    return Base64.encodeToString(imageBytes, Base64.DEFAULT)
}

//Декодирование Base 64 строки изображения в формат Bitmap
fun decodeImage(imageString: String): Bitmap {
    val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}

