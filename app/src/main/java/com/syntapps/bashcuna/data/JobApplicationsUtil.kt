package com.syntapps.bashcuna.data

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.data.EmployerData.Companion.toEmployerData
import com.syntapps.bashcuna.data.JobOffer.Companion.toJobOffer
import com.syntapps.bashcuna.other.constants.DatabaseFields
import com.syntapps.bashcuna.other.constants.JobsConstants
import com.syntapps.bashcuna.other.returnObjects.CreateNewProjectResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object JobApplicationsUtil {
    private const val TAG = "FirebaseJobApplicationService"
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

    suspend fun getEmployerData(employerId: String): EmployerData? {
        val db = Firebase.firestore
        return try {
            db.collection(DatabaseFields.Collection_Users.fieldName)
                .document(employerId)
                .get()
                .await()
                .toEmployerData()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting employer info", e)
            FirebaseCrashlytics.getInstance().log("Error getting employer info")
            //  FirebaseCrashlytics.getInstance().setCustomKey("user id", xpertSlug)
            FirebaseCrashlytics.getInstance().recordException(e)
            null
        }
    }
}