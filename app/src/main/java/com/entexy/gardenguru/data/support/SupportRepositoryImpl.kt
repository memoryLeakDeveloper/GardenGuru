package com.entexy.gardenguru.data.support

import co.nedim.maildroidx.MaildroidX
import co.nedim.maildroidx.MaildroidXType
import com.entexy.gardenguru.domain.repository.SupportRepository
import java.io.File
import javax.inject.Inject

class SupportRepositoryImpl @Inject constructor() : SupportRepository {

    override fun sendFeedback(
        email: String,
        subject: String?,
        body: String,
        files: List<File>?,
        onCompleteLambda: (success: Boolean, message: String?) -> Unit
    ) {
        //todo change email
        var builder = MaildroidX.Builder()
            .smtp("smtp.yandex.ru")
            .smtpUsername("yurykorhav@yandex.by")
            .smtpPassword("pudauhxfbqdwmcwm")
            .port("465")
            .type(MaildroidXType.HTML)
            .to("gu@planx.one")
            .from("yurykorhav@yandex.by")
            .subject("${subject ?: run { "no theme" }} - Sent from Android, email: $email")
            .body(body)
            .onCompleteCallback(object : MaildroidX.onCompleteCallback {
                override val timeout: Long = 2000

                override fun onFail(errorMessage: String) {
                    onCompleteLambda(false, errorMessage)
                }

                override fun onSuccess() {
                    onCompleteLambda(true, null)
                }
            })
        if (files != null) {
            if (files.size == 1) builder = builder.attachment(files.first().absolutePath)
            else if (files.size > 1) builder = builder.attachments(files.map { it.absolutePath })
        }
        builder.mail()
    }

}