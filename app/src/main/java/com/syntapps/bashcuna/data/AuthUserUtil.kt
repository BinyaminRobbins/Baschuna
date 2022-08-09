package com.syntapps.bashcuna.data

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.data.AuthUser.Companion.toAuthUser
import com.syntapps.bashcuna.other.constants.DatabaseFields
import com.syntapps.bashcuna.other.returnObjects.AuthWithGoogleResult
import kotlinx.coroutines.tasks.await

object AuthUserUtil {

    private const val TAG = "AuthUserUtil"

    suspend fun checkIfUserConnectedInitially(): Boolean {
        val mAuth = Firebase.auth
        return mAuth.currentUser != null
    }

    suspend fun getSignedInAccountFromIntent(data: Intent?): String? {
        return try {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            task.await().idToken
        } catch (e: Exception) {
            Log.i(TAG, "Error getting signed in account from intent")
            FirebaseCrashlytics.getInstance().log("Error getting signed in account from intent")
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    suspend fun doAuthWithGoogle(idToken: String): AuthWithGoogleResult {
        val mAuth = Firebase.auth

        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authUser = mAuth.signInWithCredential(credential)
                .await()
                .toAuthUser()

            AuthWithGoogleResult(true, authUser = authUser)
        } catch (e: Exception) {
            AuthWithGoogleResult(false, e.localizedMessage)

        }
    }

    suspend fun createUserInBackend(user: CurrentUser): Boolean {
        val db = Firebase.firestore
        val mAuth = Firebase.auth
        if (mAuth.currentUser?.uid == user.userId) {
            return try {
                db
                    .collection(DatabaseFields.Collection_Users.fieldName)
                    .document(user.userId)
                    .set(user)
                    .await()

                true
            } catch (e: Exception) {
                Log.e(TAG, "Error creating user in Firestore", e)
                FirebaseCrashlytics.getInstance().log("Error creating user in Firestore")
                FirebaseCrashlytics.getInstance()
                    .setCustomKey("attempt_create_with_id", user.userId)
                FirebaseCrashlytics.getInstance().recordException(e)
                false
            }
        } else {
            Log.w(TAG, "Error creating user in Firestore - auth uid and user id don't match")
            FirebaseCrashlytics.getInstance()
                .log("Error creating user in Firestore - auth uid and user id don't match")
            FirebaseCrashlytics.getInstance()
                .setCustomKey("attempt_create_with_id", user.userId)
            return false
        }
    }

    suspend fun reloadFirebaseUser(): Boolean {
        val mAuth = Firebase.auth
        return try {
            mAuth.currentUser?.reload()?.await()

            true
        } catch (e: FirebaseAuthInvalidUserException) {
            Log.e(TAG, "FirebaseAuthInvalidUserException - ${e.localizedMessage}")
            FirebaseCrashlytics.getInstance().recordException(e)
            false
        } catch (e: Exception) {
            Log.e(TAG, "reloadFirebaseUser() error - ${e.localizedMessage}")
            false
        }
    }

    fun logoutUser(): Boolean {
        val mAuth = Firebase.auth
        return try {
            mAuth.signOut()
            true
        } catch (e: Exception) {
            Log.e(TAG, "Error logging out user - ${e.localizedMessage}")
            FirebaseCrashlytics.getInstance().log("Error logging out user")
            FirebaseCrashlytics.getInstance().recordException(e)
            false
        }
    }

/*
    suspend fun checkUserDocExists(): Boolean {
        val db = Firebase.firestore
        val mAuth = Firebase.auth
        if (mAuth.currentUser == null) {
            Log.w(TAG, "Auth CurrentUser == null")
            return false
        }
        return try {
            db.collection(DatabaseFields.Collection_Users.fieldName)
                .document(mAuth.currentUser!!.uid)
                .get()
                .await()
                .exists()
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if user doc exists", e)
            FirebaseCrashlytics.getInstance()
                .log("Error checking if user doc exists")
            FirebaseCrashlytics.getInstance()
                .setCustomKey("attempt_check_doc_exists_with_id", mAuth.currentUser!!.uid)
            FirebaseCrashlytics.getInstance()
                .recordException(e)
            false
        }
    }
*/
}