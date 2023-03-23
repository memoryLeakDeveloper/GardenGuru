package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.databinding.FragmentLoginBinding
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.ifNotNull
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()
    private val googleAuth = registerForActivityResult(GoogleAuthContract()) { result ->
        result?.let { id ->
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.createUser(id).collect { response ->
                    handleCloudResponse(response, id)
                }
            }
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

    private fun handleCloudResponse(response: CloudResponse<Unit>, id: String) = response.getResult(
        success = {
            viewModel.saveNewToken(id)
            findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
        },
        failure = {
            bugger(it.exception?.stackTraceToString())
        },
        loading = {
            bugger("LOADING")
        }
    )

}