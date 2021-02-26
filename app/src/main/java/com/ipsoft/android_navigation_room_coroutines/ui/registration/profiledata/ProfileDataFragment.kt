package com.ipsoft.android_navigation_room_coroutines.ui.registration.profiledata

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
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.ipsoft.android_navigation_room_coroutines.R
import com.ipsoft.android_navigation_room_coroutines.databinding.FragmentProfileDataBinding
import com.ipsoft.android_navigation_room_coroutines.extensions.dismissError
import com.ipsoft.android_navigation_room_coroutines.extensions.navigateWithAnimations
import com.ipsoft.android_navigation_room_coroutines.ui.registration.RegistrationViewModel


class ProfileDataFragment : Fragment() {

    private var _binding: FragmentProfileDataBinding? = null
    private val binding get() = _binding!!

    private val registrationViewModel: RegistrationViewModel by activityViewModels()

    private val navController: NavController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val validationFields = initValidationFields()
        listenToRegistrationStateEvent(validationFields)
        registerViewListeners()
        registerDeviceBackStackCallback()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initValidationFields() = mapOf(
        RegistrationViewModel.INPUT_NAME.first to binding.inputLayoutProfileDataName,
        RegistrationViewModel.INPUT_BIO.first to binding.inputLayoutProfileDataBio
    )

    private fun listenToRegistrationStateEvent(validationFields: Map<String, TextInputLayout>) {
        registrationViewModel.registrationStateEvent.observe(viewLifecycleOwner, { registrationState ->
            when (registrationState) {
                is RegistrationViewModel.RegistrationState.CollectCredentials -> {
                    val name = binding.inputProfileDataName.text.toString()
                    val directions = ProfileDataFragmentDirections
                        .actionProfileDataFragmentToChooseCredentialsFragment(name)

                    navController.navigateWithAnimations(directions)
                }
                is RegistrationViewModel.RegistrationState.InvalidProfileData -> {
                    registrationState.fields.forEach { fieldError ->
                        validationFields[fieldError.first]?.error = getString(fieldError.second)
                    }
                }
            }
        })
    }

    private fun registerViewListeners() {
        binding.buttonProfileDataNext.setOnClickListener {
            val name = binding.inputProfileDataName.text.toString()
            val bio = binding.inputProfileDataBio.text.toString()

            registrationViewModel.collectProfileData(name, bio)
        }

        binding.inputProfileDataName.addTextChangedListener {
            binding.inputLayoutProfileDataName.dismissError()
        }

        binding.inputProfileDataBio.addTextChangedListener {
            binding.inputLayoutProfileDataBio.dismissError()
        }
    }

    private fun registerDeviceBackStackCallback() {
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            cancelRegistration()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        cancelRegistration()
        return true
    }

    private fun cancelRegistration() {
        registrationViewModel.userCancelledRegistration()
        navController.popBackStack(R.id.loginFragment, false)
    }
}