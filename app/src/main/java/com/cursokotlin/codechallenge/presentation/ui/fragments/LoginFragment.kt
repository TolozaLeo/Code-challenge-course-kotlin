package com.cursokotlin.codechallenge.presentation.ui.fragments


import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.cursokotlin.codechallenge.R
import com.cursokotlin.codechallenge.databinding.FragmentLoginBinding
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.LoginViewModel
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { result ->

        if(result.resultCode == Activity.RESULT_OK)
            viewModel.handleSuccessesLogin()
        else
            viewModel.handleFailedLogin(getString(R.string.sign_in_failed))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.setUpLogin()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner){uiModel ->
            uiModel.navigateToHome?.getContentIfNotHandled()?.let {
                navigateToHome()
            }

            uiModel.launchLogin?.getContentIfNotHandled()?.let {
                signInLauncher.launch(it)
            }

            uiModel.showError?.getContentIfNotHandled()?.let { error ->
                showSnackbar(error.message)
                showSnackbar(error.code.toString())
            }
        }
    }

    private fun navigateToHome() {
        val action = LoginFragmentDirections.actionLoginFragmentToCharactersFragment()
        findNavController().navigate(action)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}