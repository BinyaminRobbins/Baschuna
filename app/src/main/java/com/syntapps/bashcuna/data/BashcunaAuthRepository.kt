package com.syntapps.bashcuna.data

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.AuthWithGoogleResult
import com.syntapps.bashcuna.other.CurrentUser

class BashcunaAuthRepository {

    private val currentUser = CurrentUser

    private val mAuth = Firebase.auth
    private val isUserConnected = MutableLiveData<AuthWithGoogleResult?>(null)

    private fun setCurrentUser(user: FirebaseUser?): CurrentUser {
        user?.email?.let { currentUser.setEmail(it) }
        user?.displayName?.let { currentUser.setName(it) }
        return currentUser
    }

    fun checkIfUserConnectedInitially() {
        if (mAuth.currentUser != null) {
            mAuth.currentUser!!.email?.let { currentUser.setEmail(it) }
            mAuth.currentUser!!.displayName?.let { currentUser.setName(it) }
            setIsUserConnected(AuthWithGoogleResult(true, currentUser))
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
            .addOnSuccessListener {
                setIsUserConnected(AuthWithGoogleResult(true, setCurrentUser(it.user)))
            }.addOnFailureListener {
                setIsUserConnected(AuthWithGoogleResult(false, errorMsg = it.localizedMessage))
            }
    }

}