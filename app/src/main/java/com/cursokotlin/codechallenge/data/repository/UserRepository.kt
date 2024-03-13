package com.cursokotlin.codechallenge.data.repository

import com.cursokotlin.codechallenge.data.internal.User
import com.cursokotlin.codechallenge.presentation.persistance.SharedPreferenceManager
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val sharedPreferenceManager: SharedPreferenceManager,
){
    fun setUserLogged(user: User) = sharedPreferenceManager.setUserLogged(user)

    fun isUserLogged() = sharedPreferenceManager.getCurrentUser().email != null

}
