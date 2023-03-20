package com.example.gardenguru.ui.fragments.settings.support

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.DialogSupportSuccessBinding
import com.example.gardenguru.databinding.FragmentSupportBinding
import com.example.gardenguru.ui.customview.DialogHelper
import com.example.gardenguru.utils.Extensions.checkAndVerifyStoragePermissions
import com.example.gardenguru.utils.Extensions.copyToFile
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SupportFragment : BaseFragment<FragmentSupportBinding>() {

    private val viewModel: SupportViewModel by viewModels()

    private lateinit var attachmentsAdapter: AttachmentsRecyclerAdapter

    private val startForFileResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val fileUri = result.data!!.data ?: return@registerForActivityResult

                    val file = fileUri.copyToFile(requireContext())
                    val fileSizeInMB = file.length() / (1024 * 1024)

                    if (viewModel.files.value!!.size > 10) {
                        binding.tvMaxFileAttachment.visibility = View.VISIBLE
                    } else if (fileSizeInMB > 100) {
                        binding.tvFileSizeWarning.visibility = View.VISIBLE
                    } else {
                        viewModel.addFile(file)
                        attachmentsAdapter.notifyItemInserted(attachmentsAdapter.itemCount - 1)
                        binding.tvMaxFileAttachment.visibility = View.GONE
                        binding.tvFileSizeWarning.visibility = View.GONE
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.support)
            }

            val themes = resources.getStringArray(R.array.support_themes)
            spinnerThemes.initView(resources.getString(R.string.request_subject), null, ArrayList(themes.toList()))
            spinnerThemes.setValueListener { _: Int, _: String ->
                checkAndActivateBtn()
            }

            etEmail.addTextChangedListener {
                val isMailValid = !etEmail.text.isNullOrBlank() &&
                        android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()
                if (!isMailValid) {
                    etEmail.setBackgroundResource(R.drawable.bg_edit_text_error)
                } else {
                    etEmail.setBackgroundResource(R.drawable.bg_edit_text)
                }

                checkAndActivateBtn()
            }

            etDescription.addTextChangedListener {
                checkAndActivateBtn()
            }

            btSend.setOnClickListener {
                btSend.isEnabled = false
                val dialogHelper = DialogHelper()

                val progressView = ProgressBar(requireContext())
                dialogHelper.showDialog(progressView, false)

                viewModel.sendFeedback(etEmail.text.toString(), spinnerThemes.spinnerValue!!, etDescription.text.toString()) {
                    dialogHelper.hideDialog()
                    btSend.isEnabled = true
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

            initAttachments()
        }
    }

    private fun initAttachments() {
        with(binding) {
            btAttachFile.setOnClickListener {
                if (requireActivity().checkAndVerifyStoragePermissions()) {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "*/*"
                    intent.addCategory(Intent.CATEGORY_OPENABLE)

                    startForFileResult.launch(intent)
                }
            }

            attachmentsAdapter = AttachmentsRecyclerAdapter(viewModel)
            rvFileAttachment.layoutManager = LinearLayoutManager(requireContext())
            rvFileAttachment.adapter = attachmentsAdapter
        }
    }

    private fun checkAndActivateBtn() {
        with(binding) {
            val isFieldsPopulated =
                !etEmail.text.isNullOrBlank() && !etDescription.text.isNullOrBlank() && spinnerThemes.spinnerValue != null

            if (isFieldsPopulated) {
                btSend.isEnabled = true
                btSend.setBackgroundResource(R.drawable.button_green)
            } else {
                btSend.isEnabled = false
                btSend.setBackgroundResource(R.drawable.button_green_stroke)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel.removeAllFiles()
    }
}
