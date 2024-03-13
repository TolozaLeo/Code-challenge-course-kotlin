package com.cursokotlin.codechallenge.presentation.ui.viewmodels

import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.codechallenge.data.internal.ServerError
import com.cursokotlin.codechallenge.data.internal.User
import com.cursokotlin.codechallenge.data.internal.getGenericError
import com.cursokotlin.codechallenge.data.repository.UserRepository
import com.cursokotlin.codechallenge.presentation.ui.viewmodels.uimodels.LoginUiModel
import com.cursokotlin.codechallenge.utils.Event
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
): ViewModel() {
    private val _uiState: MutableLiveData<LoginUiModel> = MutableLiveData()
    val uiState: LiveData<LoginUiModel>
        get() = _uiState

    fun setUpLogin() = viewModelScope.launch(Dispatchers.IO){
        val isUserLogged = userRepository.isUserLogged()

        if(isUserLogged)
            emitUiModel(navigateToHome = Event(Unit))
        else
            launchLoginIntent()
    }

    private suspend fun launchLoginIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
        )

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .build()

        emitUiModel(launchLogin = Event(signInIntent))
    }

    fun handleSuccessesLogin() = viewModelScope.launch(Dispatchers.IO){
        val currentEmail = FirebaseAuth.getInstance().currentUser?.email
        val currentUser = User(email = currentEmail)
        userRepository.setUserLogged(currentUser)
        emitUiModel(navigateToHome = Event(Unit))
    }

    fun handleFailedLogin(message: String) = viewModelScope.launch(Dispatchers.IO) {
        emitUiModel(showError = Event(getGenericError(message)))
    }

    private suspend fun emitUiModel(
        navigateToHome: Event<Unit?>? = null,
        launchLogin: Event<Intent?>? = null,
        showError: Event<ServerError?>? = null
    ) = withContext(Dispatchers.Main) {
        _uiState.value = LoginUiModel(
            navigateToHome = navigateToHome,
            launchLogin = launchLogin,
            showError = showError
        )
    }
}