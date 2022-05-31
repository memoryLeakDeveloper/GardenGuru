package com.example.gardenguru.utils

import android.Manifest
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object Extensions {

    fun Context.getPrefs(): SharedPreferences = getSharedPreferences(PrefsKeys.PREFS, Context.MODE_PRIVATE)

    fun ImageView.setDrawable(drawable: Int) = this.setImageDrawable(ContextCompat.getDrawable(context, drawable))

    fun TextView.setString(stringRes: Int) {
        this.text = context.getString(stringRes)
    }

    fun Calendar.toDmyString() =
        SimpleDateFormat(Constance.dmyDatePattern, Locale.ENGLISH).format(this.time) ?: ""

    fun Activity.checkAndVerifyStoragePermissions(): Boolean {
        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {

            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                Constance.GALLERY_PERMISSION_REQUEST_KODE
            )
            return false
        }
        return true
    }

    fun Activity.checkAndVerifyCameraPermissions(): Boolean {
        val permission = ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.CAMERA
                ),
                Constance.GALLERY_PERMISSION_REQUEST_KODE
            )
            return false
        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun Uri.copyToFile(context: Context): File {
        var fileName = ""

        this.let { returnUri ->
            context.contentResolver.query(returnUri, null, null, null)
        }?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor.moveToFirst()
            fileName = cursor.getString(nameIndex)
        }

        val iStream: InputStream = context.contentResolver.openInputStream(this)!!
        val outputDir: File = context.cacheDir!!
        val outputFile = File(outputDir, fileName)

        iStream.use { input ->
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

        iStream.close()
        return outputFile
    }
}
