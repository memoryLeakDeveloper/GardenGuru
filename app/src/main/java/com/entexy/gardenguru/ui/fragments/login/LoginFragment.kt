package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.auth.UserEmailHelper
import com.entexy.gardenguru.databinding.FragmentLoginBinding
import com.entexy.gardenguru.utils.Extensions.getPrefs
import com.entexy.gardenguru.utils.PrefsKeys
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstLaunch = requireContext().getPrefs().getBoolean(PrefsKeys.FIRST_APP_LAUNCH, true)
        if (firstLaunch) {
            requireContext().getPrefs().edit().putBoolean(PrefsKeys.FIRST_APP_LAUNCH, false).apply()
            findNavController().navigate(R.id.action_loginFragment_to_onboardingFragment)
        } else {
            checkLogin()

            initText()
            setListener()

            binding.buttonLogin.root.setOnClickListener {
                findNavController().navigate(R.id.addingPlantFragment)
            }
        }
    }

    private fun checkLogin() {
        //todo check if login
        UserEmailHelper.Base(requireContext().getPrefs()).setEmail("kostya@planx.one")

        findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
    }

    private fun initText() {
        val span = SpannableString(getString(R.string.login_text))
        when (Locale.getDefault().language.toString()) {
            "ru" -> {
                span.apply {
                    setSpan(getClickableSpan(1), 58, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(getClickableSpan(2), 84, 112, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                binding.textLogin.movementMethod = LinkMovementMethod.getInstance()
                binding.textLogin.text = span
            }
            "en" -> {
                span.apply {
                    setSpan(getClickableSpan(1), 58, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                    setSpan(getClickableSpan(2), 84, 112, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                }
                binding.textLogin.movementMethod = LinkMovementMethod.getInstance()
                binding.textLogin.text = span
            }
        }
    }

    private fun setListener() {
        binding.buttonLogin.root.setOnClickListener {
            //TODO
            Toast.makeText(requireContext(), "TODO ME", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getClickableSpan(pos: Int) = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = true
        }

        override fun onClick(widget: View) {
            findNavController().navigate(if (pos == 1) R.id.termOfUseFragment else R.id.privacyPolicyFragment)
        }
    }


}