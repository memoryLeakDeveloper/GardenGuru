package com.entexy.gardenguru.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.OpenableColumns
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toFile
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

fun Context.getPrefs(): SharedPreferences = getSharedPreferences(PrefsKeys.PREFS, Context.MODE_PRIVATE)

fun ImageView.setDrawable(drawable: Int) = this.setImageDrawable(ContextCompat.getDrawable(context, drawable))

fun TextView.setString(stringRes: Int) {
    this.text = context.getString(stringRes)
}

fun Calendar.toDmyString() = SimpleDateFormat(Constance.dmyDatePattern, Locale.ENGLISH).format(this.time) ?: ""

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

fun Uri.copyToFile(context: Context): File {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
    } else { //todo test
        val file = this.toFile()
        val outputFile = File(context.cacheDir, Date().toString())
        file.copyTo(outputFile, true)
        return file
    }
}

fun Context.showToastLong(text: String) = Toast.makeText(this, text, Toast.LENGTH_LONG).show()

fun Context.showToastShort(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.showToastLong(@StringRes id: Int) = Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show()

fun Context.showToastShort(@StringRes id: Int) = Toast.makeText(this, getString(id), Toast.LENGTH_SHORT).show()

fun Any?.ifNull(block: () -> Unit) = run {
    if (this == null) {
        block()
    }
}

fun Any?.ifNotNull(block: () -> Unit) = run {
    if (this != null) {
        block()
    }
}

fun Context.convertPxToDp(px: Float) = (px * resources.displayMetrics.density).toInt()
