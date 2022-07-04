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


}