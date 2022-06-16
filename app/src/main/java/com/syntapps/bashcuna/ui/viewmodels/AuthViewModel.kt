package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.data.BashcunaAuthRepository

class AuthViewModel : ViewModel() {

    private var authRepository: BashcunaAuthRepository? = null

    init {
        authRepository = BashcunaAuthRepository()
    }

    private val isUserLoggedIn = MutableLiveData<Boolean>()
    fun getIsUserLoggedIn(): MutableLiveData<Boolean> {
        return isUserLoggedIn
    }

    private fun setIsUserLoggedIn(value: Boolean?) {
        value?.let {
            isUserLoggedIn.postValue(it)
        }
    }

    fun checkIfUserLoggedIn() {
        setIsUserLoggedIn(authRepository?.checkIfUserLoggedIn())
    }

    fun loginUser(email: String, password: String) {
        setIsUserLoggedIn(authRepository?.loginUser(email, password)?.value)
    }

    private val errorMsg = authRepository?.getErrorMsg()

    fun getErrorMsg(): MutableLiveData<String>? {
        return errorMsg
    }


}