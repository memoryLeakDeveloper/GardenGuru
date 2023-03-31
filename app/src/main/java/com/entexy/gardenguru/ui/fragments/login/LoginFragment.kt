package com.entexy.gardenguru.ui.fragments.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.App.Companion.firebaseAuth
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.CloudResponse
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.data.auth.GoogleAuthContract
import com.entexy.gardenguru.databinding.FragmentLoginBinding
import com.entexy.gardenguru.utils.bugger
import com.entexy.gardenguru.utils.ifNotNull
import com.entexy.gardenguru.utils.showToastLong
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val viewModel: LoginViewModel by viewModels()
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
        bugger("LoginFragment ${viewModel.tokenHelper.getToken()}")
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
        val uid = loginInFirebase(id)
        viewModel.createUser(uid).collect { response ->
            handleCloudResponse(response, uid)
        }
    }

    private suspend fun loginInFirebase(idToken: String): String {
        var userId = ""
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    userId = firebaseAuth.currentUser!!.uid
                }
            }.await()
        return userId
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
                bugger("LOADING")
            }
        )
    }

}