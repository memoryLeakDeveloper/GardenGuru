package com.example.gardenguru.ui.fragments.settings.support

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.nedim.maildroidx.MaildroidX
import co.nedim.maildroidx.MaildroidXType
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor() : ViewModel() {

    private val _files = MutableLiveData<ArrayList<File>>().apply { value = arrayListOf() }
    val files: LiveData<ArrayList<File>> = _files

    fun addFile(file: File) {
        _files.value!!.add(file)
    }

    fun removeAtFile(position: Int) {
        _files.value!!.removeAt(position).delete()
    }

    fun sendFeedback(
        email: String,
        subject: String,
        body: String,
        onCompleteLambda: (success: Boolean) -> Unit
    ) {
        var builder = MaildroidX.Builder()
            .smtp("smtp.yandex.ru")
            .smtpUsername("yurykorhav@yandex.by")
            .smtpPassword("pudauhxfbqdwmcwm")
            .port("465")
            .type(MaildroidXType.HTML)
            .to("mlearsoft+gardenguru@gmail.com")
            .from("yurykorhav@yandex.by")
            .subject("$subject - Sent from Android, email: $email")
            .body(body)
            .onCompleteCallback(object : MaildroidX.onCompleteCallback {
                override val timeout: Long = 2000

                override fun onFail(errorMessage: String) {
                    Log.d("qqqqq", "MaildroidX error: $errorMessage")
                    onCompleteLambda(false)
                }

                override fun onSuccess() {
                    onCompleteLambda(true)
                    removeAllFiles()
                }
            })

        if (files.value!!.size == 1) builder = builder.attachment(files.value!!.first().absolutePath)
        else if (files.value!!.size > 1) builder = builder.attachments(files.value!!.map { it.absolutePath })

        builder.mail()
    }

    fun removeAllFiles() {
        _files.value!!.forEach {
            it.delete()
        }
        _files.value = arrayListOf()
    }

}
