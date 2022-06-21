package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.data.BashcunaAuthRepository
import com.syntapps.bashcuna.other.AuthWithGoogleResult

class AuthViewModel : ViewModel() {

    private var authRepository: BashcunaAuthRepository? = null

    init {
        authRepository = BashcunaAuthRepository()
    }

    fun getIsUserConnected(): MutableLiveData<AuthWithGoogleResult?> {
        return authRepository?.getIsUserConnected()!!
    }

    fun checkIfUserConnectedInitially() {
        authRepository?.checkIfUserConnectedInitially()!!
    }

    fun doAuthWithGoogle(idToken: String) {
        authRepository?.doAuthWithGoogle(idToken)
    }

}