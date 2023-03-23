package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
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
        setListener()
    }

    private fun checkLogin() {
        GoogleSignIn.getLastSignedInAccount(requireContext())?.account.ifNotNull {
            findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
        }
    }

    private fun setListener() = binding.apply {
        buttonLogin.root.setOnClickListener {
            googleAuth.launch("")
        }
        textPrivacyPolicy.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_privacyPolicyFragment)
        }
        textTermOfUse.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_termOfUseFragment)
        }
    }

}