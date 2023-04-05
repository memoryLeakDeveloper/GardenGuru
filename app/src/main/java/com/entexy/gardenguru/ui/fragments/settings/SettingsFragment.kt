package com.entexy.gardenguru.ui.fragments.settings

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.data.language.Languages
import com.entexy.gardenguru.databinding.DialogCameraPermissionBinding
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

    private var isNotificationPermissionGranted = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initNotificationSwitch()
        initSpinner()
    }

    override fun onResume() {
        super.onResume()

        isNotificationPermissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }

        if (isNotificationPermissionGranted) {
            binding.swNotifications.isChecked = viewModel.isNotificationsEnabled()
        } else binding.swNotifications.isChecked = false

    }

    private fun initView() = binding.apply {
        header.apply {
            back.setOnClickListener {
                requireActivity().onBackPressed()
            }
            title.setText(R.string.settings)
        }
        btPrivacyPolicy.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(PRIVACY_POLICY_URL)).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
        btTermOfUse.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(TERM_OF_USE_URL)).apply { addFlags(Intent.FLAG_ACTIVITY_NEW_TASK) })
        }
        btDeveloperContact.setOnClickListener {
            if (checkCurrentDestination())
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

        swNotifications.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && !isNotificationPermissionGranted && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                pushNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                swNotifications.isChecked = false
            } else {
                viewModel.changeNotificationsPref(isChecked)
                tvNotifications.setText(if (isChecked) R.string.turn_off_notifications else R.string.turn_on_notifications)
            }
        }
    }

    private val pushNotificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (!granted) {
            val dialogHelper = DialogHelper()
            val dialogBinding = DialogCameraPermissionBinding.inflate(LayoutInflater.from(requireContext()))
            with(dialogBinding) {
                dialogBinding.tvDialogDescription.setText(R.string.require_notification_permissions_desc)
                btSettings.setOnClickListener {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                    requireActivity().startActivity(intent)
                    dialogHelper.hideDialog()
                }
            }
            dialogHelper.showDialog(dialogBinding.root)
        }
        isNotificationPermissionGranted = granted
    }

    private fun initSpinner() = binding.apply {
        val languages = Languages.values().map { resources.getString(it.longNameRes) }
        val initSpinnerValue = when (Locale.getDefault().language) {
            Languages.Russian.shortName -> {
                languages.indexOf(resources.getString(Languages.Russian.longNameRes))
            }
            else -> {
                languages.indexOf(resources.getString(Languages.English.longNameRes))
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
                            signOutFromGoogle()
                            if (checkCurrentDestination())
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
                            if (checkCurrentDestination())
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

    override fun onStop() {
        super.onStop()
        dialog.hideDialog()
        binding.spinnerLanguages.hidePopup()
    }

    private fun signOutFromGoogle() {
        App.user = null
        viewModel.clearUserDataPref()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GoogleAuthContract.client_id).requestEmail().build()
        GoogleSignIn.getClient(requireContext(), gso).signOut()
    }

    private fun checkCurrentDestination() = findNavController().currentDestination?.id == R.id.settingsFragment

    companion object {
        private const val PRIVACY_POLICY_URL =
            "https://docs.google.com/document/d/1kGUnptN58bFXPeQTpFTe0YPgk1nxUdtS7h6OJ9ShWs4/edit"
        private const val TERM_OF_USE_URL = "https://docs.google.com/document/d/1zaRDgrfNGsF_YOPKNs3UUoO8TyvIRsBcSf1pZQ6tDn0/edit"
    }

}
