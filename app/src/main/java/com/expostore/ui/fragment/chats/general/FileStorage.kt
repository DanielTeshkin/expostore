package com.expostore.ui.fragment.chats.general

import android.app.DownloadManager
import android.content.*
import android.content.Context.DOWNLOAD_SERVICE
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.*

class FileStorage(val context: Context) {
    var myDown:Long=0

   fun saveFile(url:String,name:String){
       Toast.makeText(context,"Загрузка файла...",Toast.LENGTH_LONG).show()
          val request=DownloadManager.Request(
              Uri.parse(url)).setTitle(name)
              .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
              .setAllowedOverMetered(true
              )
       var d =context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager

       myDown=  d.enqueue(request)
       context.registerReceiver(broadcastReceiver,IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
   }
    val broadcastReceiver = object :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            var id=p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID,-1)
                if(id==myDown){
                    Toast.makeText(context,"Загрузка завершена",Toast.LENGTH_LONG).show()
            }
        }

    }



    fun saveImage(bitmap: Bitmap) {
        val imageOutStream: OutputStream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DISPLAY_NAME, "image_.jpg")
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            values.put(
                MediaStore.Images.Media.RELATIVE_PATH,
                Environment.DIRECTORY_PICTURES
            )
            val uri: Uri? = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                values
            )
            imageOutStream = context.contentResolver?.openOutputStream(uri!!)!!
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString()
            val image = File(imagesDir, "image_new")
            imageOutStream = FileOutputStream(image)
        }

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, imageOutStream)
        imageOutStream.close()
        Toast.makeText(context, "save", Toast.LENGTH_LONG).show()

    }

   private fun getImageFromUri(FileUri: Uri?): File? {
        FileUri?.let { uri ->
            val mimeType = getMimeType(context, uri)
            if (mimeType != null) {
                Log.i("meme",mimeType)
            }
            mimeType?.let {
                val file =
                    createTmpFileFromUri(context, FileUri, "лол", ".$it")
                file?.let { Log.d("image Url = ", file.absolutePath) }
                return file
            }
        }
        return null
    }

    private fun getMimeType(context: Context, uri: Uri): String? {
        //Check uri format to avoid null
        val extension: String? = if (uri.scheme == ContentResolver.SCHEME_FILE) {
            //If scheme is a content
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            //If scheme is a File
            //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.lastPathSegment)).toString())
        }
        return extension
    }

    private fun createTmpFileFromUri(
        context: Context,
        uri: Uri,
        fileName: String,
        mimeType: String
    ): File? {

            val stream = context.contentResolver.openInputStream(uri)
            val file = File.createTempFile(fileName,mimeType)

            copyStreamToFile(stream!!, file)
        return  file

    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }


    fun request(uri: Uri): Pair<RequestBody, MultipartBody.Part> {
        val file=getImageFromUri(uri)
        Log.i("what",file?.exists().toString())
        val requestBody =
            file!!.name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData(
            "chat_file",
            file.name ?:"log",
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        )
        return Pair(requestBody, multipartBody)
    }

    fun openStorage(): Intent {
        val storageIntent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/*"
        }
        storageIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        return storageIntent
    }




}


