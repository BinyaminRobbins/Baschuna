package com.syntapps.bashcuna.other

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.IgnoreExtraProperties
import com.syntapps.bashcuna.data.EmployerData
import com.syntapps.bashcuna.other.constants.JobsConstants

@IgnoreExtraProperties
data class JobOffer(
    var jobUserOfferingID: String? = null,
    var jobFieldCode: String? = null,
    var jobStartTime: Timestamp? = null,
    var jobEndTime: Timestamp? = null,
    var jobPaymentAmount: Long? = null,
    var jobPaymentMethods: MutableList<Int>? = mutableListOf(),
    var jobLocation: String? = null,
    var jobGeoCoordinates: GeoPoint? = null, //for latitude and longitude of the location - to get distance from user
    var users: List<User>? = null,
    var jobDescription: String? = null,
    var jobIsClosed: Boolean = false,
    var jobHireCount: Long = 0,
    @Exclude
    @JvmField
    var jobID: String? = null,
    @Exclude
    @JvmField
    var employerData: EmployerData? = null,
) {
    companion object {
        fun DocumentSnapshot.toJobOffer(): JobOffer? {
            try {
                val jobUserOfferingID = getString(JobsConstants.OFFERING_USER.fieldName)
                val jobFieldCode = getString("jobFieldCode")
                val jobStartTime = getTimestamp("jobStartTime")
                val jobEndTime = getTimestamp("jobEndTime")
                val jobPaymentAmount = getLong("jobPaymentAmount")
                val jobPaymentMethods = get("jobPaymentMethods") as MutableList<Int>?
                val jobLocation = getString("jobLocation")
                val jobGeoCoordinates = getGeoPoint("jobGeoCoordinates")
                val users = get("users") as List<User>?
                val jobDescription = getString("jobDescription")
                val jobIsClosed = getBoolean("jobIsClosed")!!
                val jobHireCount = getLong("jobHireCount")!!
                val jobID = id

                return JobOffer(
                    jobUserOfferingID,
                    jobFieldCode,
                    jobStartTime,
                    jobEndTime,
                    jobPaymentAmount,
                    jobPaymentMethods,
                    jobLocation,
                    jobGeoCoordinates,
                    users,
                    jobDescription,
                    jobIsClosed,
                    jobHireCount,
                    jobID
                )
            } catch (e: Exception) {
                Log.e(TAG, "Error converting job offer", e)
                FirebaseCrashlytics.getInstance().log("Error converting job offer")
                FirebaseCrashlytics.getInstance().setCustomKey("job_id", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                return null
            }
        }

        private const val TAG = "JobOffer"
    }
}
