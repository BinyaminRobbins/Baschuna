package com.syntapps.bashcuna.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class BashcunaAuthRepository {

    private val mAuth = Firebase.auth

    private var loginResult = MutableLiveData<Boolean?>()
    private var errorMsg = MutableLiveData<String>()

    fun getErrorMsg(): MutableLiveData<String> {
        return errorMsg
    }

    fun checkIfUserLoggedIn(): Boolean {
        return mAuth.currentUser != null
    }

    fun loginUser(email: String, password: String): MutableLiveData<Boolean?> {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    loginResult.postValue(true)
                } else {
                    loginResult.postValue(false)
                    errorMsg.postValue("Login Failed - user not recognized")
                }
            }
        return loginResult
    }
}