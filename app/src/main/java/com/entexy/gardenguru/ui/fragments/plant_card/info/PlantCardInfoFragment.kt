package com.entexy.gardenguru.ui.fragments.plant_card.info

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.DialogChangePhotoBinding
import com.entexy.gardenguru.databinding.DialogRemovePlantBinding
import com.entexy.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.util.*

@AndroidEntryPoint
class PlantCardInfoFragment : BaseFragment<FragmentPlantCardInfoBinding>() {

    private val viewModel: PlantCardInfoViewModel by viewModels()

    private lateinit var plantData: PlantData

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        plantData = requireArguments().getParcelable(CARD_INFO_PLANT_DATA_KEY)!!

        initView()
    }

    private fun initView() {
        initPlantNameView(plantData)
        initPlantPhotoView(plantData)

        with(binding) {
            plantCaver.setImageByGlide(plantData.coverPhoto, null)
            plantName.text = plantData.getPlantName("ru")
            plantInfo.initView(plantData.getPlantDescription("ru"))
            eventsCalendar.initView(viewModel.predictEvents(plantData, arrayListOf()))
            careDifficult.initView(plantData.careComplexity, true)
            wheather.initView(plantData)
            careDescription.initView(plantData)
            pests.initView(plantData.pests)
            benefits.initView(plantData.benefits)

            buttonDelete.setOnClickListener {
                val dialogHelper = DialogHelper()

                val dialogBinding = DialogRemovePlantBinding.inflate(LayoutInflater.from(requireContext()))

                with(dialogBinding) {
                    val spannableText = SpannableString(
                        resources.getString(
                            R.string.dialog_want_to_delete_plant,
                            plantData.getPlantName("ru")
                        )
                    )
                    val spannableStartIndex = spannableText.indexOf(plantData.getPlantName("ru"))

                    spannableText.setSpan(
                        ForegroundColorSpan(resources.getColor(R.color.primary_green, requireContext().theme)),
                        spannableStartIndex, // start
                        spannableStartIndex + (plantData.getPlantName("ru")).length, // end
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
                    )
                    tvDialogDescription.text =
                        spannableText
                    btNo.setOnClickListener { dialogHelper.hideDialog() }
                    btYes.setOnClickListener {
                        lifecycleScope.launch {
                            viewModel.deletePlant(plantData.id).collect {
                                it.getResult(
                                    success = {
                                        dialogHelper.hideDialog()
                                        requireActivity().onBackPressed()
                                    },
                                    failure = {
                                        dialogHelper.hideDialog()
                                        root.showSnackBar(R.string.error_deleting)
                                    },
                                    loading = {
                                        dialogHelper.hideDialog()
                                        dialogHelper.showDialog(ProgressBar(requireContext()), false)
                                    }
                                )
                            }
                        }
                    }
                }
                dialogHelper.showDialog(dialogBinding.root)
            }
        }
    }

    private fun initPlantNameView(data: PlantData) = with(binding) {
        tvPlantName.text = plantData.getPlantName("ru")

        ivEditPlantName.setOnClickListener {
            containerPlantName.toGone()
            etPlantName.toVisible()
            etPlantName.setText(data.name)
        }

        etPlantName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (etPlantName.text?.isEmpty() == true) return@setOnEditorActionListener false
                lifecycleScope.launch {
                    val dialogHelper = DialogHelper()
                    viewModel.setPlantName(data.id, etPlantName.text.toString()).collect {
                        it.getResult(
                            success = {
                                dialogHelper.hideDialog()

                                scrollRoot.hideKeyboard()
                                tvPlantName.text = etPlantName.text.toString()
                                containerPlantName.toVisible()
                                etPlantName.toGone()
                            },
                            failure = {
                                etPlantName.hideKeyboard()
                                etPlantName.setText(plantData.getPlantName("ru"))
                                dialogHelper.hideDialog()
                                root.showSnackBar(R.string.error_update_data)
                            },
                            loading = {
                                dialogHelper.showDialog(ProgressBar(requireContext()), false)
                            }
                        )
                    }
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        etPlantName.addTextChangedListener {
            if (it?.isEmpty() == true) {
                etPlantName.setBackgroundResource(R.drawable.bg_edit_text_small_corners_with_error_stroke)
            } else {
                etPlantName.setBackgroundResource(R.drawable.bg_edit_text_small_corners_with_stroke)
            }
        }
    }

    private fun initPlantPhotoView(data: PlantData) = with(binding) {

        Glide.with(requireContext())
            .load(data.customPhoto ?: data.photo)
            .circleCrop()
            .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
            .into(plantIcon)

        plantIcon.setOnClickListener {
            val dialogBinding = DialogChangePhotoBinding.inflate(LayoutInflater.from(requireContext()))
            val dialogHelper = DialogHelper()
            with(dialogBinding) {
                if (data.customPhoto == null) {
                    tvDeletePhoto.toGone()
                    dividerDeletePhoto.toGone()
                }

                btCancel.setOnClickListener { dialogHelper.hideDialog() }
                tvCamera.setOnClickListener {
                    dialogHelper.hideDialog()

                    val photoFile: File = File(requireContext().filesDir, "${Date().time}.jpg").apply {
                        createNewFile()
                    }

                    tempPhotoUri = FileProvider.getUriForFile(
                        requireContext(), requireContext().packageName,
                        photoFile
                    )
                    takePicture.launch(tempPhotoUri)
                }
                tvGallery.setOnClickListener {
                    dialogHelper.hideDialog()
                    startForResultFromGallery.launch(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI))
                }
                tvDeletePhoto.setOnClickListener {
                    dialogHelper.hideDialog()
                    lifecycleScope.launch {
                        viewModel.deletePhoto(data.id, data.customPhoto ?: return@launch).collect {
                            it.getResult(
                                success = {
                                    plantData.customPhoto = null
                                    plantIcon.setCircleImageByGlide(plantData.photo)
                                    dialogHelper.hideDialog()
                                },
                                failure = {
                                    dialogHelper.hideDialog()
                                    root.showSnackBar(R.string.error_deleting)
                                },
                                loading = {
                                    dialogHelper.showDialog(ProgressBar(requireContext()))
                                }
                            )
                        }
                    }
                }
            }
            dialogHelper.showDialog(dialogBinding.root, gravity = Gravity.BOTTOM)
        }
    }

    private val startForResultFromGallery = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            if (result.data != null) {
                val selectedImageUri = result.data!!.data ?: return@registerForActivityResult

                loadPhoto(selectedImageUri)
            }
        }
    }

    private var tempPhotoUri: Uri? = null
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success == true) {
            loadPhoto(tempPhotoUri!!)
            tempPhotoUri = null
        }
    }

    private fun loadPhoto(uri: Uri) {
        val dialogHelper = DialogHelper()
        lifecycleScope.launch {
            viewModel.uploadPhoto(uri, requireContext(), plantData.id, plantData.customPhoto).collect { cloudResponse ->
                cloudResponse.getResult(
                    success = {
                        binding.plantIcon.setCircleImageByGlide(it.result)
                        plantData.customPhoto = it.result
                        dialogHelper.hideDialog()
                    },
                    failure = {
                        dialogHelper.hideDialog()
                        binding.root.showSnackBar(R.string.error_update_data)
                    },
                    loading = {
                        dialogHelper.showDialog(ProgressBar(requireContext()))
                    }
                )
            }
        }
    }

    companion object {
        const val CARD_INFO_PLANT_DATA_KEY = "plant-data-key"
    }
}