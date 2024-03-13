package com.cursokotlin.codechallenge.presentation.persistance

import android.content.SharedPreferences
import androidx.core.content.edit
import com.cursokotlin.codechallenge.data.internal.User
import javax.inject.Inject

class SharedPreferenceManager @Inject constructor(
    private val sharedPreference: SharedPreferences,
){
    fun setUserLogged(user: User) {
        sharedPreference.edit{
            putString(LOGGED_EMAIL, user.email)
            apply()
        }
    }

    fun getCurrentUser(): User {
        val email = sharedPreference.getString(LOGGED_EMAIL, null)
        return User(email)
    }

    companion object {
        const val LOGGED_EMAIL = "LOGGED_EMAIL"
    }
}
