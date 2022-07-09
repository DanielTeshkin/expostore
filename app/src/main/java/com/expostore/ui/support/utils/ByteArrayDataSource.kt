package com.expostore.ui.support.utils

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import javax.activation.DataSource

class ByteArrayDataSource( val type:String,val data:ByteArray) :DataSource {

    override fun getInputStream(): InputStream = ByteArrayInputStream(data)


    override fun getOutputStream(): OutputStream =  throw IOException("Not Supported");

    override fun getContentType(): String {
        return type ?:"application/octet-stream"
    }

    override fun getName(): String="ByteArrayDataSource"

}