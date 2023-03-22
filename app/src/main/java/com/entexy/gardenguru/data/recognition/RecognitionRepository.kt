package com.entexy.gardenguru.data.recognition

import android.content.Context
import android.graphics.Bitmap
import com.entexy.gardenguru.domain.repository.RecognitionRepository
import org.pytorch.IValue
import org.pytorch.LiteModuleLoader
import org.pytorch.torchvision.TensorImageUtils
import java.io.File
import java.io.FileOutputStream

class RecognitionRepositoryImpl : RecognitionRepository {

    override fun recognizePlant(context: Context, bitmap: Bitmap): List<String> {
        val indexesOfPlants = recognize(context, bitmap)
        val results = mutableListOf<String>()

        indexesOfPlants?.forEach {
            results.add(PlantsRecognitionResult.results[it])
        }
        return results
    }

    private fun recognize(context: Context, bitmap: Bitmap): IntArray? {
        val module = LiteModuleLoader.load(assetFilePath(context, "garden440int.ptl"))
        val NO_MEAN_RGB = floatArrayOf(0.485f, 0.456f, 0.406f)
        val NO_STD_RGB = floatArrayOf(0.229f, 0.224f, 0.225f)

        val inputTensor = TensorImageUtils.bitmapToFloat32Tensor(
            bitmap, NO_MEAN_RGB, NO_STD_RGB
        )
        val outputTensor = module.forward(IValue.from(inputTensor)).toTensor()
        return outputTensor.dataAsIntArray
    }

    private fun assetFilePath(context: Context, assetName: String): String? {
        val file = File(context.filesDir, assetName)
        if (file.exists() && file.length() > 0) {
            return file.absolutePath
        }
        context.assets.open(assetName).use { `is` ->
            FileOutputStream(file).use { os ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (`is`.read(buffer).also { read = it } != -1) {
                    os.write(buffer, 0, read)
                }
                os.flush()
            }
            return file.absolutePath
        }
    }
}