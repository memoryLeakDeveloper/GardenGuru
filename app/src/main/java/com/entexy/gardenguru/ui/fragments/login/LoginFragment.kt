package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.App
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.databinding.FragmentLoginBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()
    private var loadingDialog = DialogHelper()
    private val googleAuth = registerForActivityResult(GoogleAuthContract()) { result ->
        result?.let {
            loadingDialog.showDialog(ProgressBar(requireContext()), false)
            loginUser(it)
        } ?: run {
            requireView().showSnackBar(R.string.something_is_wrong)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLogin()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
    }

    private fun checkLogin() = lifecycleScope.launch {
        if (viewModel.isUserAuthorized() && App.user != null)
            if (checkCurrentDestination())
                findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
    }

    private fun setListener() = binding.apply {
        buttonLogin.root.setOnClickListener {
            googleAuth.launch("")
        }
        textPrivacyPolicy.setOnClickListener {
            if (checkCurrentDestination())
                findNavController().navigate(R.id.action_loginFragment_to_privacyPolicyFragment)
        }
        textTermOfUse.setOnClickListener {
            if (checkCurrentDestination())
                findNavController().navigate(R.id.action_loginFragment_to_termOfUseFragment)
        }
    }

    private fun loginUser(id: String) = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.loginUser(id)?.let { uid ->
            viewModel.createUser(uid).collect { response ->
                handleCloudResponse(response)
            }
        } ?: run {
            withContext(Dispatchers.Main) {
                requireView().showSnackBar(R.string.something_is_wrong)
            }
        }
    }

    private suspend fun handleCloudResponse(response: CloudResponse<Unit>) = withContext(Dispatchers.Main) {
        response.getResult(
            success = {
                if (checkCurrentDestination())
                    findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
            },
            failure = {
                bugger(it.exception?.stackTraceToString())
            },
            loading = {
            }
        )
        loadingDialog.hideDialog()
    }

    private fun checkCurrentDestination() = findNavController().currentDestination?.id == R.id.loginFragment
}