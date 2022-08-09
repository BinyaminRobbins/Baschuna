package com.syntapps.bashcuna.data

import android.util.Log
import com.google.firebase.auth.AuthResult
import com.google.firebase.crashlytics.FirebaseCrashlytics

data class AuthUser(
    val uid: String,
    val email: String?,
    val photoUrl: String?,
    val name: String?,
    val isNewUser: Boolean?
) {
    companion object {
        private const val TAG = "AuthUser"

        fun AuthResult.toAuthUser(): AuthUser? {

            return try {
                val uid = user!!.uid
                val email = user!!.email
                val name = user!!.displayName
                val photoUrl = user!!.photoUrl?.toString()
                val isNewUser = additionalUserInfo?.isNewUser

                AuthUser(uid, email, photoUrl, name, isNewUser)
            } catch (e: NullPointerException) {
                Log.e(TAG, "Tried to sign in user but FirebaseUser was null", e)
                FirebaseCrashlytics.getInstance()
                    .log("Tried to sign in user but FirebaseUser was null")
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            } catch (e: Exception) {
                Log.e(TAG, "Error signing in user", e)
                FirebaseCrashlytics.getInstance()
                    .log("Error signing in user")
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
    }
}