package com.syntapps.bashcuna.data

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.data.CurrentUser.Companion.toCurrentUser
import com.syntapps.bashcuna.data.JobOffer.Companion.toJobOffer
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.constants.DatabaseFields
import com.syntapps.bashcuna.other.constants.JobsConstants
import kotlinx.coroutines.tasks.await

object CurrentUserUtil {
    private const val TAG = "FirebaseCurrentUserService"

    suspend fun buildCurrentUser(): CurrentUser? {
        val db = Firebase.firestore
        val mAuth = Firebase.auth
        return try {
            mAuth.currentUser?.let {
                db.collection(DatabaseFields.Collection_Users.fieldName)
                    .document(it.uid)
                    .get()
                    .await()
                    .toCurrentUser()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting CurrentUser info", e)
            FirebaseCrashlytics.getInstance().log("Error getting CurrentUser info")
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }

    suspend fun setFavoriteFields(userId: String, favouriteFields: MutableList<WorkHireField>) {
        val db = Firebase.firestore
        db.collection(DatabaseFields.Collection_Users.fieldName)
            .document(userId)
            .update("favouriteFields", favouriteFields)
            .addOnCompleteListener {
                if (it.isCanceled || !it.isSuccessful) {
                    Log.e(TAG, "Error setting favourite fields", it.exception)
                    FirebaseCrashlytics.getInstance().setCustomKey("user id", userId)
                    it.exception?.let { error ->
                        FirebaseCrashlytics.getInstance().recordException(error)
                    }
                }
            }
    }

    suspend fun loadOfferedJobs(): List<JobOffer>? {
        val db = Firebase.firestore
        val mAuth = Firebase.auth
        return try {
            mAuth.currentUser?.uid?.let { id ->
                db.collection(DatabaseFields.Collection_Jobs.fieldName)
                    .whereEqualTo(JobsConstants.OFFERING_USER.fieldName, id)
                    .orderBy("jobStartTime")
                    .get()
                    .await()
                    .mapNotNull {
                        it.toJobOffer()
                    }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error loading job offers", e)
            FirebaseCrashlytics.getInstance().log("Error getting job offers")
            mAuth.currentUser?.uid?.let { id ->
                FirebaseCrashlytics.getInstance().setCustomKey("user_jobs_toload", id)
            }
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }

    suspend fun fromAuthUser(authUser: AuthUser?): CurrentUser? {
        return try {
            authUser?.toCurrentUser()
        } catch (e: Exception) {
            null
        }

    }
}

/*
.addSnapshotListener { value, error ->
    if (error != null) {
        Log.w(CurrentUserUtil.TAG, "Listen failed.", error)
        return@addSnapshotListener
    }
    value?.let {
        if (offeredJobs.isNullOrEmpty()) {
            for (jobOffer in it.documents) {
                offeredJobs.add(jobOffer.toObject<JobOffer>())
                offeredJobsLiveData.postValue(offeredJobs)
            }
        } else {
            for (jobOffer in it.documentChanges) {
                if (jobOffer.type == DocumentChange.Type.ADDED) {
                    offeredJobs.add(jobOffer.document.toObject())
                    offeredJobsLiveData.postValue(offeredJobs)
                }
            }
        }
    }
}*/
