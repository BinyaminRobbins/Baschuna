package com.syntapps.bashcuna.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.CurrentUser
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.other.constants.DatabaseFields
import com.syntapps.bashcuna.other.constants.JobsConstants

class BashcunaMainRepository {

    private val TAG = "MainRepoTAG"

    private var mAuth = Firebase.auth
    private var mDatabase = Firebase.firestore
    private var currentUser: CurrentUser? = null
    private var currentUserLiveData = MutableLiveData<CurrentUser?>()

    fun getCurrentUserLiveData(): MutableLiveData<CurrentUser?> {
        return currentUserLiveData
    }

    fun buildCurrentUser() {
        mAuth.currentUser?.uid?.let {
            mDatabase.collection(DatabaseFields.Collection_User.fieldName)
                .document(it)
                .get()
                .addOnSuccessListener { docSnapshot ->
                    if (docSnapshot != null && docSnapshot.exists()) {
                        currentUser = docSnapshot.toObject(CurrentUser::class.java)
                        currentUser?.aquireOtherParams(mAuth)
                        currentUserLiveData.value = currentUser
                    }
                }
                .addOnFailureListener {
                    Log.e(TAG, "buildCurrentUser: ${it.localizedMessage}")
                }

        }
    }

    private var offeredJobs = mutableListOf<JobOffer?>()
    private var offeredJobsLiveData = MutableLiveData(offeredJobs)
    fun getOfferedJobsLiveData(): MutableLiveData<MutableList<JobOffer?>> {
        return offeredJobsLiveData
    }

    fun loadOfferedJobs() {
        if (currentUser?.getRole() == CurrentUser.ROLE_WORKER) return@loadOfferedJobs
        mDatabase
            .collection(DatabaseFields.Collection_Jobs.fieldName)
            .whereEqualTo(JobsConstants.OFFERING_USER.fieldName, mAuth.currentUser?.uid)
            .whereEqualTo(JobsConstants.JOB_CLOSED.fieldName, true)
            .get()
            .addOnSuccessListener {
                if (it != null && !it.isEmpty) {
                    for (jobOffer in it.documents) {
                        offeredJobs.add(jobOffer.toObject(JobOffer::class.java))
                        offeredJobsLiveData.postValue(offeredJobs)
                    }
                }
            }
    }


}