package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.databinding.FragmentLoginBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.ifNotNull
import com.entexy.gardenguru.utils.showToastLong
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
            loginUser(it)
        } ?: run {
            requireContext().showToastLong(R.string.something_is_wrong)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkLogin()
        setListener()
    }

    private fun checkLogin() {
        viewModel.tokenHelper.getToken().ifNotNull {
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

    private fun loginUser(id: String) = lifecycleScope.launch(Dispatchers.IO) {
        viewModel.loginUser(id)?.let { uid ->
            viewModel.createUser(uid).collect { response ->
                handleCloudResponse(response, uid)
            }
        } ?: run {
            requireContext().showToastLong(R.string.something_is_wrong)
        }

    }

    private suspend fun handleCloudResponse(response: CloudResponse<Unit>, id: String) = withContext(Dispatchers.Main) {
        response.getResult(
            success = {
                viewModel.saveNewToken(id)
                findNavController().navigate(R.id.action_loginFragment_to_timetableFragment)
            },
            failure = {
                bugger(it.exception?.stackTraceToString())
            },
            loading = {
                loadingDialog.showDialog(ProgressBar(requireContext()), false)
            }
        )
        loadingDialog.hideDialog()
    }

}