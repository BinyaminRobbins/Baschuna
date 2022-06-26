package com.syntapps.bashcuna.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.AuthWithGoogleResult
import com.syntapps.bashcuna.other.CurrentUser

class BashcunaAuthRepository {

    private val currentUser = CurrentUser

    private val mAuth = Firebase.auth
    private val isUserConnected = MutableLiveData<AuthWithGoogleResult?>(null)

    fun getCurrentUser(): CurrentUser {
        return currentUser
    }

    fun checkIfUserConnectedInitially() {
        if (mAuth.currentUser != null) {
            mAuth.currentUser!!.email?.let { currentUser.setEmail(it) }
            mAuth.currentUser!!.displayName?.let { currentUser.setName(it) }
            setIsUserConnected(AuthWithGoogleResult(true))
        } else setIsUserConnected(AuthWithGoogleResult(false))
    }

    fun getIsUserConnected(): MutableLiveData<AuthWithGoogleResult?> {
        return isUserConnected
    }

    private fun setIsUserConnected(value: AuthWithGoogleResult?) {
        isUserConnected.postValue(value)
    }

    fun doAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult: AuthResult ->
                authResult.user?.email?.let { currentUser.setEmail(it) }
                authResult.user?.displayName?.let { currentUser.setName(it) }
                authResult.user?.photoUrl?.let { currentUser.profileUrl = it }
                setIsUserConnected(
                    AuthWithGoogleResult(
                        true,
                        isNewUser = (authResult.additionalUserInfo?.isNewUser == true)
                    )
                )
            }.addOnFailureListener {
                setIsUserConnected(AuthWithGoogleResult(false, errorMsg = it.localizedMessage))
            }
    }

}