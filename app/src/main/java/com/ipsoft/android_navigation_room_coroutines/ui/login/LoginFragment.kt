package com.ipsoft.android_navigation_room_coroutines.ui.login

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       26/02/2021
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ipsoft.android_navigation_room_coroutines.R
import com.ipsoft.android_navigation_room_coroutines.databinding.FragmentLoginBinding
import com.ipsoft.android_navigation_room_coroutines.extensions.dismissError
import com.ipsoft.android_navigation_room_coroutines.extensions.navigateWithAnimations


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by activityViewModels()

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val validationFields = initValidationFields()
        listenToAuthenticationStateEvent(validationFields)
        registerViewListeners()
        registerDeviceBackStackCallback()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initValidationFields() = mapOf(
        LoginViewModel.INPUT_USERNAME.first to binding.inputLayoutLoginUsername,
        LoginViewModel.INPUT_PASSWORD.first to binding.inputLayoutLoginPassword
    )

    private fun listenToAuthenticationStateEvent(validationFields: Map<String, TextInputLayout>) {
        viewModel.authenticationStateEvent.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                is LoginViewModel.AuthenticationState.Authenticated -> {
                    navController.popBackStack()
                }
                is LoginViewModel.AuthenticationState.InvalidAuthentication -> {
                    authenticationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }
        })
    }

    private fun registerViewListeners() {
        binding.buttonLoginSignIn.setOnClickListener {
            val username = binding.inputLoginUsername.text.toString()
            val password = binding.inputLoginPassword.text.toString()

            viewModel.authenticate(username, password)
        }

        binding.buttonLoginSignUp.setOnClickListener {
            navController.navigateWithAnimations(R.id.action_loginFragment_to_navigation)
        }

        binding.inputLoginUsername.addTextChangedListener {
            binding.inputLayoutLoginUsername.dismissError()
        }

        binding.inputLoginPassword.addTextChangedListener {
            binding.inputLayoutLoginPassword.dismissError()
        }
    }

    private fun registerDeviceBackStackCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            cancelAuthentication()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cancelAuthentication()
        return true
    }

    private fun cancelAuthentication() {
        viewModel.refuseAuthentication()
        navController.popBackStack(R.id.startFragment, false)
    }
}