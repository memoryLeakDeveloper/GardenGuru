package com.entexy.gardenguru.ui.dialogs

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.view.LayoutInflater
import com.entexy.gardenguru.databinding.DialogCameraPermissionBinding
import com.entexy.gardenguru.ui.customview.DialogHelper

class CameraPermissionDialog {

    private val dialogHelper = DialogHelper()

    fun showDialog(context: Context){
        val dialogBinding = DialogCameraPermissionBinding.inflate(LayoutInflater.from(context))
        with(dialogBinding){
            btSettings.setOnClickListener{
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
                dialogHelper.hideDialog()
            }
        }
        dialogHelper.showDialog(dialogBinding.root)
    }
}