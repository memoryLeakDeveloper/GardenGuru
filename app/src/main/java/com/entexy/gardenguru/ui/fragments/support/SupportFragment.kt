package com.entexy.gardenguru.ui.fragments.support

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.DialogSupportSuccessBinding
import com.entexy.gardenguru.databinding.FragmentSupportBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.ui.fragments.support.adapter.AdapterMode
import com.entexy.gardenguru.ui.fragments.support.adapter.AttachmentsRecyclerAdapter
import com.entexy.gardenguru.ui.fragments.support.adapter.FileModel
import com.entexy.gardenguru.ui.fragments.support.adapter.changeMode
import com.entexy.gardenguru.utils.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SupportFragment : BaseFragment<FragmentSupportBinding>() {

    private lateinit var attachmentsAdapter: AttachmentsRecyclerAdapter
    private val viewModel: SupportViewModel by viewModels()
    private val successDialog = DialogHelper()
    private val progressDialog = DialogHelper()
    private var descriptionError = false
    private val startForFileResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            result.data?.data?.let {
                viewModel.addFile(FileModel(result.data!!.data!!.copyToFile(requireContext()), false))
            } ?: run {
                return@registerForActivityResult
            }
        }
    }

    private val selectFileToDeleteCallback: (Int, Boolean) -> Unit = { position, isSelected ->
        viewModel.selectItem(position, isSelected)
    }

    @SuppressLint("NotifyDataSetChanged")
    private val changeAdapterModeCallback: () -> Unit = {
        attachmentsAdapter.mode = changeMode(attachmentsAdapter.mode)
        attachmentsAdapter.notifyDataSetChanged()
        binding.ivAddPhoto changeStateTo (attachmentsAdapter.mode == AdapterMode.Default && attachmentsAdapter.list.size < 10)
        changeDeleteButtonStateTo(attachmentsAdapter.mode == AdapterMode.Select)
        viewModel.modeChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeListPhotos()
        initAdapter()
        setListeners()
    }

    private fun initView() = binding.apply {
        header.back.setOnClickListener {
            requireActivity().onBackPressed()
        }
        header.title.setText(R.string.support)
        val themes = resources.getStringArray(R.array.support_themes)
        spinnerThemes.initView(resources.getString(R.string.request_subject), ArrayList(themes.toList()))
    }

    private fun setListeners() = binding.apply {
        etEmail.addTextChangedListener {
            changeEmailEditTextStateFocus()
        }
        etEmail.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                changeEmailEditTextStateFocus()
            } else {
                if (etEmail.text.isNullOrBlank()) {
                    changeEmailEditTextStateWithException(R.string.required_field)
                } else {
                    if (etEmail.text.toString().isEmailValid()) {
                        changeEmailEditTextStateFocus()
                    } else if (!etEmail.text.toString().isEmailValid()) {
                        changeEmailEditTextStateWithException(R.string.wrong_email)
                    } else if (!etEmail.text.toString().contains("@") || etEmail.text!!.filter { it == '@' }.length > 1) {
                        changeEmailEditTextStateWithException(R.string.wrong_email)
                    } else if (!etEmail.text.toString().isEmailLengthIsValid()) {
                        changeEmailEditTextStateWithException(R.string.max_email_count)
                    } else {
                        changeEmailEditTextStateFocus()
                    }
                }
            }
        }
        etDescription.addTextChangedListener {
            tvCount.text = getString(R.string.count, it?.count() ?: 0)
            if (it != null && it.count() > 20) {
                etDescription.setBackgroundResource(R.drawable.edit_text_background_error)
                tvCount.setTextColor(requireContext().getColor(R.color.pink))
                descriptionError = true
            } else {
                etDescription.setBackgroundResource(R.drawable.edit_text_background_focus)
                tvCount.setTextColor(requireContext().getColor(R.color.gray))
                tvDescriptionHint.toGone()
                descriptionError = false
            }
        }
        etDescription.setOnFocusChangeListener { editText, hasFocus ->
            if (!hasFocus) {
                if (!etDescription.text?.trim().isNullOrBlank() && ((etDescription.text?.trim()?.count() ?: 0) <= 20)) {
                    etDescription.setBackgroundResource(R.drawable.edit_text_background_unfocused)
                    tvDescriptionHint.toGone()
                } else {
                    etDescription.setBackgroundResource(R.drawable.edit_text_background_error)
                    if (etDescription.text?.trim().isNullOrBlank()) tvDescriptionHint.toVisible()
                    descriptionError = true
                }
            } else {
                tvDescriptionHint.toGone()
                editText.setBackgroundResource(R.drawable.edit_text_background_focus)
            }
        }
        btnSend.setOnClickListener {
            if (!checkValidInput()) return@setOnClickListener
            btnSend.isEnabled = false
            progressDialog.showDialog(ProgressBar(requireContext()), false)
            lifecycleScope.launch(Dispatchers.Main) {
                viewModel.sendFeedback(
                    etEmail.text.toString(),
                    spinnerThemes.spinnerValue,
                    etDescription.text.toString()
                ) { isSuccess, error ->
                    progressDialog.hideDialog()
                    btnSend.isEnabled = true
                    if (isSuccess) {
                        val dialogBinding = DialogSupportSuccessBinding.inflate(LayoutInflater.from(requireContext())).apply {
                            tvOk.setOnClickListener {
                                successDialog.hideDialog()
                                requireActivity().onBackPressed()
                            }
                        }
                        successDialog.showDialog(dialogBinding.root, cancelable = false)
                    } else {
                        requireView().showSnackBar(if (error?.contains("java.net.UnknownHostException") == true) R.string.internet_connection_error else R.string.something_is_wrong)
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun observeListPhotos() = binding.apply {
        viewModel.files.observe(viewLifecycleOwner) {
            attachmentsAdapter.list = it
            attachmentsAdapter.notifyDataSetChanged()
            ivAddPhoto changeStateTo (it.size < 10)
        }
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.filesSize.collect {
                tvFileSizeWarning.apply { if (it > 100) toVisible() else toGone() }
            }
        }
    }

    private fun initAdapter() = binding.apply {
        attachmentsAdapter = AttachmentsRecyclerAdapter(emptyList(), changeAdapterModeCallback, selectFileToDeleteCallback)
        rvFileAttachment.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvFileAttachment.adapter = attachmentsAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun checkValidInput() = binding.run {
        if (etEmail.text.toString().isEmpty()) {
            changeEmailEditTextStateWithException(R.string.required_field)
            return false
        } else if (!etEmail.text.toString().isEmailValid()) {
            changeEmailEditTextStateWithException(R.string.wrong_email)
            return false
        } else if (!etEmail.text.toString().isEmailLengthIsValid()) {
            changeEmailEditTextStateWithException(R.string.max_email_count)
            return false
        } else if (etDescription.text.isNullOrEmpty()) {
            etDescription.setBackgroundResource(R.drawable.edit_text_background_error)
            tvCount.setTextColor(requireContext().getColor(R.color.pink))
            descriptionError = true
            return false
        } else if (etDescription.text!!.trim().isEmpty()) {
            return false
        } else if (descriptionError) {
            return false
        } else if (attachmentsAdapter.mode != AdapterMode.Default) {
            attachmentsAdapter.mode = AdapterMode.SelectException
            attachmentsAdapter.notifyDataSetChanged()
            return false
        } else if (viewModel.filesSize.value > 100) {
            return false
        }
        true
    }

    private fun changeDeleteButtonStateTo(isActive: Boolean) = binding.btnDelete.apply {
        if (isActive) {
            toVisible()
            setOnClickListener {
                viewModel.deleteFiles()
                changeAdapterModeCallback.invoke()
            }
        } else {
            setOnClickListener(null)
            toGone()
        }
    }

    private infix fun ImageView.changeStateTo(isClickable: Boolean) {
        if (isClickable)
            setOnClickListener {
                if (requireActivity().checkAndVerifyStoragePermissions()) {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
                        type = "image/*"
                        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
                    }
                    startForFileResult.launch(intent)
                }
            }
        else
            setOnClickListener(null)
        Glide.with(context).load(if (isClickable) R.drawable.ic_add_photo else R.drawable.ic_add_photo_disabled)
            .transform(CenterCrop(), RoundedCorners(context.convertPxToDp(10F))).into(this)
    }

    private fun changeEmailEditTextStateWithException(@StringRes hint: Int) = binding.apply {
        etEmail.setBackgroundResource(R.drawable.edit_text_background_error)
        tvEmailHint.text = requireContext().getText(hint)
        tvEmailHint.toVisible()
    }

    private fun changeEmailEditTextStateFocus() = binding.apply {
        etEmail.setBackgroundResource(R.drawable.edit_text_background_focus)
        tvEmailHint.toGone()
    }

    override fun onStop() {
        super.onStop()
        binding.spinnerThemes.hidePopup()
        progressDialog.hideDialog()
    }
}