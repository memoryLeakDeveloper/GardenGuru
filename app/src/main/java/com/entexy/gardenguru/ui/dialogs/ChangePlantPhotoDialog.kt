package com.entexy.gardenguru.ui.dialogs

import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View.OnClickListener
import com.entexy.gardenguru.databinding.DialogChangePhotoBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.toGone

class ChangePlantPhotoDialog {

    private val dialogHelper = DialogHelper()

    fun showDialog(
        activity: Activity,
        isCustomPhotoExist: Boolean,
        cameraClickListener: OnClickListener,
        galleryClickListener: OnClickListener,
        deletePhotoClickListener: OnClickListener,
    ) {
        val dialogBinding = DialogChangePhotoBinding.inflate(LayoutInflater.from(activity))
        with(dialogBinding) {
            if (!isCustomPhotoExist) {
                tvDeletePhoto.toGone()
                dividerDeletePhoto.toGone()
            }

            btCancel.setOnClickListener { dialogHelper.hideDialog() }
            tvCamera.setOnClickListener {
                dialogHelper.hideDialog()
                cameraClickListener.onClick(it)
            }
            tvGallery.setOnClickListener {
                dialogHelper.hideDialog()
                galleryClickListener.onClick(it)
            }
            tvDeletePhoto.setOnClickListener {
                dialogHelper.hideDialog()
                deletePhotoClickListener.onClick(it)
            }
        }
        dialogHelper.showDialog(dialogBinding.root, gravity = Gravity.BOTTOM)
    }
}