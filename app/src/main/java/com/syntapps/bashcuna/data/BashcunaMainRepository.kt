package com.syntapps.bashcuna.data

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.CurrentUser

class BashcunaMainRepository {

    private var mAuth = Firebase.auth
    private var currentUser: CurrentUser? = null

    private fun setCurrentUser(currentUser: CurrentUser) {
        this.currentUser = currentUser
    }

    fun getCurrentUser(): CurrentUser? {
        return currentUser
    }

    fun buildCurrentUser() {
        mAuth.currentUser?.let {
            currentUser?.setEmail(it.email!!)
            currentUser?.setName(it.displayName!!)
        }
    }


}