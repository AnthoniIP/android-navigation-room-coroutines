package com.ipsoft.android_navigation_room_coroutines.data.repository

import com.ipsoft.android_navigation_room_coroutines.data.model.User
import com.ipsoft.android_navigation_room_coroutines.ui.registration.RegistrationViewModel
import com.ipsoft.android_navigation_room_coroutines.ui.registration.RegistrationViewParams

/**
 *
 *  Author:     Anthoni Ipiranga
 *  Project:    android-navigation-room-coroutines
 *  Date:       01/03/2021
 */

interface UserRepository {

    fun createUser(registrationViewParams: RegistrationViewParams)
    fun getUser(id: Long) : User
    fun login(username: String,password: String) : Long
}