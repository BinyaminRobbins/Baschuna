package com.syntapps.bashcuna.other

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.JobOffer.Companion.toJobOffer
import com.syntapps.bashcuna.other.constants.DatabaseFields
import com.syntapps.bashcuna.other.constants.JobsConstants
import kotlinx.coroutines.tasks.await

object JobApplicationsService {
    private const val TAG = "FirebaseProfileService"
    suspend fun getAvailableJobs(): List<JobOffer?>? {
        val db = Firebase.firestore
        return try {
            db.collection(DatabaseFields.Collection_Jobs.fieldName)
                .whereEqualTo(JobsConstants.JOB_CLOSED.fieldName, false)
                .limit(10)
                .get()
                .await()
                .documents
                .mapNotNull {
                    it.toJobOffer()
                }

        } catch (e: Exception) {
            Log.e(TAG, "Error getting job offer details", e)
            FirebaseCrashlytics.getInstance().log("Error getting job offer detail")
            //  FirebaseCrashlytics.getInstance().setCustomKey("user id", xpertSlug)
            FirebaseCrashlytics.getInstance().recordException(e)
            emptyList()
        }
    }
}