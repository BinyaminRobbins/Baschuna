package com.syntapps.bashcuna.data

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.syntapps.bashcuna.other.constants.DatabaseFields

data class EmployerData(
    var employerName: String? = null,
    var employerProfilePhoto: String? = null
) {
    companion object {
        fun DocumentSnapshot.toEmployerData(): EmployerData? {
            return try {
                val employerName = getString(DatabaseFields.display_name.fieldName)
                val employerProfilePhoto = getString(DatabaseFields.profile_url.fieldName)
                if (employerName == null /*|| employerProfilePhoto == null*/) {
                    Log.w(TAG, "Error getting employer data")
                    FirebaseCrashlytics.getInstance()
                        .log("Error getting employer data - some data is null")
                    FirebaseCrashlytics.getInstance().setCustomKey("job_id", id)
                    return null
                }
                EmployerData(employerName, employerProfilePhoto)
            } catch (e: Exception) {
                Log.e(TAG, "Error getting employer data", e)
                FirebaseCrashlytics.getInstance().log("Error getting employer data")
                FirebaseCrashlytics.getInstance().setCustomKey("job_id", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }

        private const val TAG = "EmployerData"
    }
}
