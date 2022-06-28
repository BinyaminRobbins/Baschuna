package com.syntapps.bashcuna.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.syntapps.bashcuna.other.CurrentUser
import com.syntapps.bashcuna.other.database_fields.DatabaseFields

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
        mDatabase.collection(DatabaseFields.Collection_User.fieldName)
            .whereEqualTo(DatabaseFields.field_uid.fieldName, mAuth.currentUser?.uid)
            .limit(1)
            .get()
            .addOnSuccessListener {
                if (it != null && !it.isEmpty) {
                    currentUser = it.documents[0].toObject(CurrentUser::class.java)
                    currentUser?.aquireOtherParams(mAuth)
                    currentUserLiveData.value = currentUser
                }
            }
            .addOnFailureListener {
                Log.e(TAG, "buildCurrentUser: ${it.localizedMessage}")
            }


    }


}