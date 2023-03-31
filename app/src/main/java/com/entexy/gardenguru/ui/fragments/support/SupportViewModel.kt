package com.entexy.gardenguru.ui.fragments.support

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.nedim.maildroidx.MaildroidX
import co.nedim.maildroidx.MaildroidXType
import com.entexy.gardenguru.ui.fragments.support.adapter.FileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor() : ViewModel() {

    private val _files = MutableLiveData<MutableList<FileModel>>(mutableListOf())
    val files = _files

    private val _filesSize = MutableStateFlow(0L)
    val filesSize = _filesSize

    fun addFile(model: FileModel) {
        _filesSize.value += model.file.length() / (1024 * 1024)
        _files.value = mutableListOf<FileModel>().apply {
            addAll(_files.value ?: emptyList())
            add(0, model)
        }
    }

    fun deleteFiles() {
        val allFiles = _files.value
        val list = _files.value?.filter { it.isSelected }
        list?.let {
            allFiles?.removeAll(it)
        }
        _files.postValue(allFiles ?: emptyList<FileModel>().toMutableList())
    }

    fun selectItem(position: Int, isSelected: Boolean) {
        _files.value?.let {
            it[position].apply {
                this.isSelected = isSelected
            }
        }
    }

    fun modeChanged() {
        _files.value?.forEach {
            it.isSelected = false
        }
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
                    onCompleteLambda(false)
                }

                override fun onSuccess() {
                    onCompleteLambda(true)
                    removeAllFiles()
                }
            })

        if (files.value != null) {
            if (files.value?.size == 1) builder = builder.attachment(files.value!!.first().file.absolutePath)
            else if (files.value!!.size > 1) builder = builder.attachments(files.value!!.map { it.file.absolutePath })
        }
        builder.mail()
    }

    fun removeAllFiles() {
        _files.value?.forEach {
            it.file.delete()
        }
        _files.value = mutableListOf()
    }

}
