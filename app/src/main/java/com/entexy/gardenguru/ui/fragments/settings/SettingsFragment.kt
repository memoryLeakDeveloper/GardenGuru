package com.entexy.gardenguru.ui.fragments.settings

import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.language.Languages
import com.entexy.gardenguru.databinding.DialogDeleteAccountBinding
import com.entexy.gardenguru.databinding.FragmentSettingsBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.bugger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: SettingsViewModel by viewModels()
    private var dialog: DialogHelper = DialogHelper()

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
            lifecycleScope.launch {
                viewModel.deleteUser().collect {
                    it.getResult(
                        loading = {
                            bugger("loading")
                        },
                        success = {
                            bugger("success")

                        },
                        failure = {
                            bugger("failure")

                        }
                    )
                }
            }
//            findNavController().popBackStack(R.id.loginFragment, false)
        }
        tvDeleteAccount.setOnClickListener {
            val dialogBinding = DialogDeleteAccountBinding.inflate(LayoutInflater.from(requireContext()))
            dialog.showDialog(dialogBinding.apply {
                tvAccept.setOnClickListener {
                    //todo delete account
                    dialog.hideDialog()
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

    companion object {
        private const val PRIVACY_POLICY_URL =
            "https://docs.google.com/document/d/1kGUnptN58bFXPeQTpFTe0YPgk1nxUdtS7h6OJ9ShWs4/edit"
        private const val TERM_OF_USE_URL = "https://docs.google.com/document/d/1zaRDgrfNGsF_YOPKNs3UUoO8TyvIRsBcSf1pZQ6tDn0/edit"
    }


}
