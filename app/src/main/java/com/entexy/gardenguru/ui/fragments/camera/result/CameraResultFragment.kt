package com.entexy.gardenguru.ui.fragments.camera.result

import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.FragmentCameraResultBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CameraResultFragment : BaseFragment<FragmentCameraResultBinding>() {

    private val viewModel: CameraResultViewModel by viewModels()

    private lateinit var uriResult: Uri

    companion object {
        const val CAMERA_RESULT_KEY = "camera_result"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        uriResult = requireArguments().getParcelable(CAMERA_RESULT_KEY)!!

        with(binding) {
            ivClose.setOnClickListener {
                requireContext().contentResolver.delete(uriResult, null, null)
                findNavController().popBackStack(R.id.timetableFragment, true)
            }

            ivBack.setOnClickListener {
                requireContext().contentResolver.delete(uriResult, null, null)
                requireActivity().onBackPressed()
            }

            Glide.with(requireContext())
                .load(uriResult)
                .into(ivCameraResult)

            ivConfirmBtn.setOnClickListener {
                val dialogHelper = DialogHelper()

                val progressView = ProgressBar(requireContext())
                dialogHelper.showDialog(progressView, false)

                CoroutineScope(Dispatchers.IO).launch {
                    val recognitionResult: List<PlantData> = viewModel.processImage(
                        requireContext(),
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uriResult)
                    )

                    launch(Dispatchers.Main) {
                        dialogHelper.hideDialog()
                        findNavController().navigate(R.id.action_cameraResultFragment_to_addingPlantFragment)
                    }
                }
            }
        }

    }
}
