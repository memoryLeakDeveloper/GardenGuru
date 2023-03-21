package com.entexy.gardenguru.data.auth

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

class GoogleAuthContract : ActivityResultContract<String, String?>() {

    override fun createIntent(context: Context, input: String): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(client_id).requestEmail().build()
        return GoogleSignIn.getClient(context, gso).signInIntent
    }

    override fun parseResult(resultCode: Int, intent: Intent?) = if (resultCode == Activity.RESULT_OK) {
        runCatching {
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            task.getResult(ApiException::class.java).idToken
        }.getOrNull()
    } else {
        null
    }

    companion object {
        const val client_id = "173536080304-slh28sj96h57hcumrs37cgrcp8u5e6ib.apps.googleusercontent.com"
    }

}