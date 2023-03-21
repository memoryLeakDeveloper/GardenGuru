package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.databinding.FragmentLoginBinding
import com.entexy.gardenguru.utils.ifNotNull
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()
    private val googleAuth = registerForActivityResult(GoogleAuthContract()) { result ->
        result?.let {
            viewModel.saveNewToken(it)
            findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
        } ?: run {
            //todo
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()
        initText()
        setListener()
    }

    private fun checkLogin() {
        GoogleSignIn.getLastSignedInAccount(requireContext())?.account.ifNotNull {
            findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
        }
    }

    private fun initText() = binding.apply {
        val span = SpannableString(getString(R.string.login_text))
        when (Locale.getDefault().language.toString()) {
            "ru" -> {
                span.setSpan(getClickableSpan(1), 58, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                span.setSpan(getClickableSpan(2), 84, 112, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textLogin.movementMethod = LinkMovementMethod.getInstance()
                textLogin.text = span
            }
            "en" -> {
                span.setSpan(getClickableSpan(1), 58, 81, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                span.setSpan(getClickableSpan(2), 84, 112, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                textLogin.movementMethod = LinkMovementMethod.getInstance()
                textLogin.text = span
            }
        }
    }

    private fun setListener() {
        binding.buttonLogin.root.setOnClickListener {
            googleAuth.launch("")
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