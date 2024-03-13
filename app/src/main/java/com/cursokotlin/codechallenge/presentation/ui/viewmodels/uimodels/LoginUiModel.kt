package com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels

import android.content.Intent
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.utils.Event

data class LoginUiModel(
    val navigateToHome: Event<Unit?>? = null,
    val launchLogin: Event<Intent?>? = null,
    val showError: Event<ServerError?>? = null
)
