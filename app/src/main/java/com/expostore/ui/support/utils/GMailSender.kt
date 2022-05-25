package com.expostore.ui.support.utils

import java.util.*
import javax.activation.DataHandler
import javax.activation.DataSource
import javax.activation.FileDataSource
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart
import javax.mail.util.ByteArrayDataSource


class GMailSender(val user:String,val password:String) : Authenticator() {
    val  mailhost = "smtp.gmail.com";
    private val _multipart: Multipart = MimeMultipart()
    var props = Properties()
     private var session:Session
   init {
       props.setProperty("mail.transport.protocol", "smtp");
       props.setProperty("mail.host", mailhost);
       props.put("mail.smtp.auth", "true");
       props["mail.smtp.port"] = "465";
       props["mail.smtp.socketFactory.port"] = "465";
       props["mail.smtp.socketFactory.class"] = "javax.net.ssl.SSLSocketFactory";
       props["mail.smtp.socketFactory.fallback"] = "false";
       props.setProperty("mail.smtp.quitwait", "false");
       session = Session.getDefaultInstance(props, this);
   }

    override fun getPasswordAuthentication(): PasswordAuthentication? {
        return PasswordAuthentication(user, password)
    }

    @Synchronized
    @Throws(Exception::class)
    fun sendMail(subject: String?, body: String, sender: GMailSender?) {
        try {
            val message = MimeMessage(session)
            val handler = DataHandler(ByteArrayDataSource(body.toByteArray(), "text/plain"))
            message.sender = InternetAddress(sender?.mailhost)
            message.subject = subject
            message.dataHandler = handler
            val messageBodyPart: BodyPart = MimeBodyPart()
            messageBodyPart.setText(body)
            _multipart.addBodyPart(messageBodyPart)
            message.setContent(_multipart)
            if (user.indexOf(',') > 0) message.setRecipients(
                Message.RecipientType.TO,
                InternetAddress.parse(user)
            ) else message.setRecipient(Message.RecipientType.TO, InternetAddress(user))
            Transport.send(message)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(java.lang.Exception::class)
    fun addAttachment(filename: String?) {
        val messageBodyPart: BodyPart = MimeBodyPart()
        val source: DataSource = FileDataSource(filename)
        messageBodyPart.dataHandler = DataHandler(source)
        messageBodyPart.fileName = "download image"
        _multipart.addBodyPart(messageBodyPart)
    }


}