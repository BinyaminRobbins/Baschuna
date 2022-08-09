package com.syntapps.bashcuna.data

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.constants.DatabaseFields
import com.syntapps.bashcuna.other.returnObjects.CreateNewProjectResult
import kotlinx.coroutines.tasks.await

object JobsCollectionUtil {
    private const val TAG = "JobsCollectionUtil"

    suspend fun createNewJob(jobOffer: JobOffer): CreateNewProjectResult {
        val db = Firebase.firestore
        return try {
            db.collection(DatabaseFields.Collection_Jobs.fieldName)
                .add(jobOffer)
                .await()
            CreateNewProjectResult(true, null)
        } catch (e: Exception) {
            Log.e(TAG, "Error creating new project")
            FirebaseCrashlytics.getInstance().log("Error creating new project")
            FirebaseCrashlytics.getInstance().recordException(e)
            CreateNewProjectResult(false, e.localizedMessage)
        }
    }

}