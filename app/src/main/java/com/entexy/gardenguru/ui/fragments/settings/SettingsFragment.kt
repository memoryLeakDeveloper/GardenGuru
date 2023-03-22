package com.entexy.gardenguru.ui.fragments.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentSettingsBinding
import com.entexy.gardenguru.utils.toDmyString
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SettingsFragment : BaseFragment<FragmentSettingsBinding>() {

    private val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSubscription()

        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.settings)
            }

            btPrivacyPolicy.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_privacyPolicyFragment)
            }

            btTermOfUse.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_termOfUseFragment)
            }

            btDeveloperContact.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_supportFragment)
            }

            btExit.setOnClickListener {
                findNavController().popBackStack(R.id.loginFragment, false)
            }

            val languages = resources.getStringArray(R.array.languages)
            spinnerLanguages.initView(languages[0], ArrayList(languages.toList()), false)
        }
    }

    private fun initSubscription() {
        with(binding) {
            tvSubscriptionPaidUpTo.text = resources.getString(R.string.paid_up_to_date, Calendar.getInstance().toDmyString())
        }
    }


}
