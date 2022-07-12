package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.BashcunaAuthRepository
import com.syntapps.bashcuna.other.returnObjects.AuthWithGoogleResult
import com.syntapps.bashcuna.other.CurrentUser
import com.syntapps.bashcuna.other.WorkHireField

class AuthViewModel : ViewModel() {

    private var authRepository: BashcunaAuthRepository? = null

    init {
        authRepository = BashcunaAuthRepository()
    }

    var userDescriptionText: String? = null

    fun getIsUserConnected(): MutableLiveData<AuthWithGoogleResult?> {
        return authRepository?.getIsUserConnected()!!
    }

    fun checkIfUserConnectedInitially() {
        authRepository?.checkIfUserConnectedInitially()!!
    }

    fun doAuthWithGoogle(idToken: String) {
        authRepository?.doAuthWithGoogle(idToken)
    }

    fun getCurrentUser(): CurrentUser? {
        return authRepository?.getCurrentUser()
    }

    private val fieldOptions = listOf(
        WorkHireField("בייביסיטינג", R.drawable.syt_field_babysitting),
        WorkHireField("עבודת גינה/בחוץ", R.drawable.syt_field_gardening),
        WorkHireField("בישול", R.drawable.syt_field_cooking),
        WorkHireField("קניות", R.drawable.syt_field_shopping),
        WorkHireField("משלוחים", R.drawable.syt_field_delivery),
        WorkHireField("שיפוצים", R.drawable.syt_field_renovations)
    )

    fun getFieldOptions(): List<WorkHireField> {
        return fieldOptions
    }

    private val isUserBuilt = authRepository?.getIsUserBuilt()
    fun getIsUserBuilt(): MutableLiveData<Boolean?>? {
        return isUserBuilt
    }

    fun buildUser() {
        authRepository?.buildUser()
    }
}