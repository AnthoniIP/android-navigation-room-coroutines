package com.ipsoft.android_navigation_room_coroutines.ui.profile

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       26/02/2021
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ipsoft.android_navigation_room_coroutines.R
import com.ipsoft.android_navigation_room_coroutines.databinding.FragmentProfileBinding
import com.ipsoft.android_navigation_room_coroutines.ui.login.LoginViewModel


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        viewModel.authenticationStateEvent.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.Authenticated -> {
                    binding.textProfileWelcome.text = getString(R.string.profile_text_welcome, viewModel.username)
                }
                LoginViewModel.AuthenticationState.Unauthenticated -> {
                    navController.navigate(R.id.loginFragment)
                }
            }
        })
    }

}