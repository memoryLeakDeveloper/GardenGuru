package com.entexy.gardenguru.ui.fragments.settings

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.data.language.Languages
import com.entexy.gardenguru.databinding.DialogDeleteAccountBinding
import com.entexy.gardenguru.databinding.FragmentSettingsBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.showToastLong
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: SettingsViewModel by viewModels()
    private var dialog: DialogHelper = DialogHelper()
    private var progressDialog = DialogHelper()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initNotificationSwitch()
        initSpinner()
    }

    private fun initView() = binding.apply {
        header.apply {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            title.setText(R.string.settings)
        }
        btPrivacyPolicy.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL)))
        }
        btTermOfUse.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TERM_OF_USE_URL)))
        }
        btDeveloperContact.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment_to_supportFragment)
        }
        btnExit.setOnClickListener {
            handleExitFromAccountEvent()
        }
        tvDeleteAccount.setOnClickListener {
            val dialogBinding = DialogDeleteAccountBinding.inflate(LayoutInflater.from(requireContext()))
            dialog.showDialog(dialogBinding.apply {
                tvAccept.setOnClickListener {
                    handleDeleteUserEvent()
                }
                tvDecline.setOnClickListener {
                    dialog.hideDialog()
                }
            }.root)
        }
    }

    private fun initNotificationSwitch() = binding.apply {
        tvNotifications.setText(if (viewModel.isNotificationsEnabled()) R.string.turn_off_notifications else R.string.turn_on_notifications)
        swNotifications.isChecked = viewModel.isNotificationsEnabled()
        swNotifications.trackTintList =
            ColorStateList.valueOf(requireContext().getColor(if (viewModel.isNotificationsEnabled()) R.color.primary_green else R.color.gray4))
        swNotifications.thumbTintList =
            ColorStateList.valueOf(requireContext().getColor(if (viewModel.isNotificationsEnabled()) R.color.white else R.color.gray))
        swNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                swNotifications.trackTintList = ColorStateList.valueOf(requireContext().getColor(R.color.primary_green))
                swNotifications.thumbTintList = ColorStateList.valueOf(requireContext().getColor(R.color.white))
            } else {
                swNotifications.trackTintList = ColorStateList.valueOf(requireContext().getColor(R.color.gray4))
                swNotifications.thumbTintList = ColorStateList.valueOf(requireContext().getColor(R.color.gray))
            }
            viewModel.changeNotificationsPref(isChecked)
            tvNotifications.setText(if (isChecked) R.string.turn_off_notifications else R.string.turn_on_notifications)
        }
    }

    private fun initSpinner() = binding.apply {
        val languages = resources.getStringArray(R.array.languages).toList()
        val initSpinnerValue = when (Locale.getDefault().language) {
            Languages.Russian.shortName -> {
                languages.indexOf(Languages.Russian.longName)
            }
            else -> {
                languages.indexOf(Languages.English.longName)
            }
        }
        spinnerLanguages.initView(languages[initSpinnerValue], ArrayList(languages.toList()))
        spinnerLanguages.setValueListener { position, name ->
            bugger("position = $position, name = $name")
            //todo change system language
        }
    }

    private fun handleExitFromAccountEvent() {
        lifecycleScope.launch {
            viewModel.signOutUser().collect {
                withContext(Dispatchers.Main) {
                    it.getResult(
                        loading = {
                            progressDialog.showDialog(ProgressBar(requireContext()), false)
                        },
                        success = {
                            viewModel.tokenHelper.setToken(null)
                            signOutFromGoogle()
                            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
                        },
                        failure = {
                            requireContext().showToastLong(R.string.something_is_wrong)
                        }
                    )
                    progressDialog.hideDialog()
                    dialog.hideDialog()
                }
            }
        }
    }

    private fun handleDeleteUserEvent() {
        lifecycleScope.launch {
            signOutFromGoogle()
            viewModel.deleteUser().collect {
                withContext(Dispatchers.Main) {
                    it.getResult(
                        loading = {
                            progressDialog.showDialog(ProgressBar(requireContext()), false)
                        },
                        success = {
                            viewModel.tokenHelper.setToken(null)
                            findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
                        },
                        failure = {
                            requireContext().showToastLong(R.string.something_is_wrong)
                        }
                    )
                    progressDialog.hideDialog()
                    dialog.hideDialog()
                }
            }
        }
    }

    private fun signOutFromGoogle() {
        App.user = null
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GoogleAuthContract.client_id).requestEmail().build()
        GoogleSignIn.getClient(requireContext(), gso).signOut()
    }

    companion object {
        private const val PRIVACY_POLICY_URL =
            "https://docs.google.com/document/d/1kGUnptN58bFXPeQTpFTe0YPgk1nxUdtS7h6OJ9ShWs4/edit"
        private const val TERM_OF_USE_URL = "https://docs.google.com/document/d/1zaRDgrfNGsF_YOPKNs3UUoO8TyvIRsBcSf1pZQ6tDn0/edit"
    }

}
