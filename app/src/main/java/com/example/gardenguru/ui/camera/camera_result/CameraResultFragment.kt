package com.example.gardenguru.ui.camera.camera_result

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentCameraResultBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CameraResultFragment : Fragment() {

    private lateinit var binding: FragmentCameraResultBinding
    private lateinit var viewModel: CameraResultViewModel

    private lateinit var uriResult: Uri

    companion object{
        const val CAMERA_RESULT_KEY = "camera_result"
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this)[CameraResultViewModel::class.java]
        binding = FragmentCameraResultBinding.inflate(inflater, container, false)

        uriResult = requireArguments().getParcelable(CAMERA_RESULT_KEY)!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            ivClose.setOnClickListener {
                requireContext().contentResolver.delete(uriResult, null, null)
                findNavController().popBackStack(R.id.timetableFragment,true)
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
                    viewModel.processImage(uriResult)

                    launch(Dispatchers.Main){
                        dialogHelper.hideDialog()
                        findNavController().navigate(R.id.action_cameraResultFragment_to_addingPlantFragment)
                    }
                }
            }
        }

    }
}
