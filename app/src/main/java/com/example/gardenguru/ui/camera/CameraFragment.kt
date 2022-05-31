package com.example.gardenguru.ui.camera

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentCameraBinding
import com.example.gardenguru.ui.camera.camera_result.CameraResultFragment
import com.example.gardenguru.utils.Constance
import com.example.gardenguru.utils.Extensions.checkAndVerifyCameraPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class CameraFragment : Fragment() {

    private lateinit var binding: FragmentCameraBinding

    private var imageCapture: ImageCapture? = null
    private var cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

    private val startForResultFromGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val selectedImageUri = result.data!!.data ?: return@registerForActivityResult

                    findNavController().navigate(
                        R.id.action_cameraFragment_to_cameraResultFragment,
                        bundleOf(CameraResultFragment.CAMERA_RESULT_KEY to selectedImageUri)
                    )
                }
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireActivity().checkAndVerifyCameraPermissions() && requireActivity().checkAndVerifyCameraPermissions()) {
            startCamera()
        }

        with(binding) {
            ivCameraBtn.setOnClickListener {
                takePhoto()
            }

            ivChangeCamera.setOnClickListener {
                cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA) {
                    ivChangeCamera.animate().rotation(0F).duration = 300
                    CameraSelector.DEFAULT_BACK_CAMERA
                } else {
                    ivChangeCamera.animate().rotation(180F).duration = 300
                    CameraSelector.DEFAULT_FRONT_CAMERA
                }

                try {
                    startCamera()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            ivClose.setOnClickListener {
                requireActivity().onBackPressed()
            }

            ivGallery.setOnClickListener {
                val intent =
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )

                startForResultFromGallery.launch(intent)
            }
        }

    }

    private fun takePhoto() {
        // Get a stable reference of the modifiable image capture use case
        val imageCapture = imageCapture ?: return

        // Create time stamped name and MediaStore entry.
        val name = Date().toString()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        val metadata = ImageCapture.Metadata()
        metadata.isReversedHorizontal = cameraSelector == CameraSelector.DEFAULT_FRONT_CAMERA

        // Create output options object which contains file + metadata
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).setMetadata(metadata)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    e.printStackTrace()
                    Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    findNavController().navigate(
                        R.id.action_cameraFragment_to_cameraResultFragment,
                        bundleOf(CameraResultFragment.CAMERA_RESULT_KEY to output.savedUri)
                    )
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.cameraSurface.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()


            // Select back camera as a default
            val cameraSelector = cameraSelector

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture
                )

            } catch (e: Exception) {
                e.printStackTrace()
            }

        }, ContextCompat.getMainExecutor(requireContext()))


    }
}
