package com.entexy.gardenguru.ui.fragments.settings.support

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.DialogSupportSuccessBinding
import com.entexy.gardenguru.databinding.FragmentSupportBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.checkAndVerifyStoragePermissions
import com.entexy.gardenguru.utils.copyToFile
import com.entexy.gardenguru.utils.toGone
import com.entexy.gardenguru.utils.toVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SupportFragment : BaseFragment<FragmentSupportBinding>() {

    private lateinit var attachmentsAdapter: AttachmentsRecyclerAdapter
    private val viewModel: SupportViewModel by viewModels()
    private val startForFileResult = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        if (result == null) return@registerForActivityResult
        val file = result.copyToFile(requireContext())
        val fileSizeInMB = file.length() / (1024 * 1024)
        if (viewModel.files.value.size > 10) {
            binding.tvMaxFileAttachment.toVisible()
        } else if (fileSizeInMB > 100) {
            binding.tvFileSizeWarning.toVisible()
        } else {
            viewModel.addFile(file)
            binding.tvMaxFileAttachment.toGone()
            binding.tvFileSizeWarning.toGone()
        }
    }
    private val addPhotoCallback: () -> Unit = {
        if (requireActivity().checkAndVerifyStoragePermissions()) {
            startForFileResult.launch("")
        }
    }
    private val dialogHelper = DialogHelper()
    private var descriptionError = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeListPhotos()
        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.support)
            }
            val themes = resources.getStringArray(R.array.support_themes)
            spinnerThemes.initView(resources.getString(R.string.request_subject), ArrayList(themes.toList()))
            spinnerThemes.setValueListener { _: Int, _: String ->
//                checkAndActivateBtn()
            }
            etEmail.addTextChangedListener {
                val isMailValid =
                    !etEmail.text.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()
                if (!isMailValid) {
                    etEmail.setBackgroundResource(R.drawable.edit_text_background_error)
                    tvEmailHint.toVisible()
                } else {
                    etEmail.setBackgroundResource(R.drawable.edit_text_background_focus)
                    tvEmailHint.toGone()
                }
//                checkAndActivateBtn()
            }
            etEmail.setOnFocusChangeListener { editText, hasFocus ->
                if (!hasFocus)
                    editText.setBackgroundResource(R.drawable.edit_text_background_unfocused)
                else
                    editText.setBackgroundResource(R.drawable.edit_text_background_focus)
            }
            etDescription.addTextChangedListener {
                tvCount.text = getString(R.string.count, it?.count() ?: 0)
                if (it != null && it.count() > 2000) {
                    etDescription.setBackgroundResource(R.drawable.edit_text_background_error)
                    tvCount.setTextColor(requireContext().getColor(R.color.pink))
                    descriptionError = true
                } else {
                    etDescription.setBackgroundResource(R.drawable.edit_text_background_focus)
                    tvCount.setTextColor(requireContext().getColor(R.color.gray))
                    descriptionError = false
                }
            }
            etDescription.setOnFocusChangeListener { editText, hasFocus ->
                if (!hasFocus)
                    editText.setBackgroundResource(R.drawable.edit_text_background_unfocused)
                else
                    editText.setBackgroundResource(R.drawable.edit_text_background_focus)
            }

            btnSend.setOnClickListener {
                btnSend.isEnabled = false
                dialogHelper.showDialog(ProgressBar(requireContext()), false)
                viewModel.sendFeedback(etEmail.text.toString(), spinnerThemes.spinnerValue!!, etDescription.text.toString()) {
                    dialogHelper.hideDialog()
                    btnSend.isEnabled = true
                    if (it) {
                        val dialogBinding = DialogSupportSuccessBinding.inflate(LayoutInflater.from(requireContext()))
                        dialogHelper.showDialog(dialogBinding.root, cancelListener = {
                            requireActivity().onBackPressed()
                        })
                    } else {
                        Toast.makeText(requireContext(), R.string.something_is_wrong, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            btnAddPhoto.setOnClickListener {
                if (requireActivity().checkAndVerifyStoragePermissions()) {
                    startForFileResult.launch("image/*")
                }
            }
            iniAdapter()
        }
    }

    private fun observeListPhotos() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.files.collect {
                attachmentsAdapter.updateData(it.toList())
            }
        }
    }

    private fun iniAdapter() = binding.apply {
        attachmentsAdapter = AttachmentsRecyclerAdapter(emptyList(), addPhotoCallback)
        rvFileAttachment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvFileAttachment.adapter = attachmentsAdapter
    }

    private fun checkAndActivateBtn() {
        with(binding) {
            val isFieldsPopulated =
                !etEmail.text.isNullOrBlank() && !etDescription.text.isNullOrBlank() && spinnerThemes.spinnerValue != null

            if (isFieldsPopulated) {
                btnSend.isEnabled = true
                btnSend.setBackgroundResource(R.drawable.button_green)
            } else {
                btnSend.isEnabled = false
                btnSend.setBackgroundResource(R.drawable.button_green_stroke)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.removeAllFiles()
    }

}