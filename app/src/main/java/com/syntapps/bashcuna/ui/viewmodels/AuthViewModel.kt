package com.syntapps.bashcuna.ui.viewmodels

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntapps.bashcuna.data.*
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.returnObjects.AuthWithGoogleResult
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val TAG = "AuthViewModel"

    private val _isUserConnectedInitially = MutableLiveData<Boolean>()
    val isUserConnectedInitially: LiveData<Boolean> = _isUserConnectedInitially

    init {
        viewModelScope.launch {
            val isConnected = AuthUserUtil.checkIfUserConnectedInitially()
            _isUserConnectedInitially.value = isConnected
        }
    }

    private val _authWithGoogleResult = MutableLiveData<AuthWithGoogleResult>()
    val authWithGoogleResult: LiveData<AuthWithGoogleResult> = _authWithGoogleResult

    private val _authUser = MutableLiveData<AuthUser?>()
    val authUser: LiveData<AuthUser?> = _authUser

    private val _currentUser = MutableLiveData<CurrentUser?>()
    val currentUser: LiveData<CurrentUser?> = _currentUser

    fun googleAuth(data: Intent?) {
        viewModelScope.launch {
            val idToken = AuthUserUtil.getSignedInAccountFromIntent(data)
            idToken?.let {
                viewModelScope.launch {
                    val result: AuthWithGoogleResult = AuthUserUtil.doAuthWithGoogle(it)
                    _authWithGoogleResult.value = result
                    _authUser.value = result.authUser
                    _currentUser.value = CurrentUserUtil.fromAuthUser(result.authUser)
                }
            }
        }
    }

    fun updateDescriptionText(description: String?) {
        _currentUser.value?.userDescription = description
    }

    fun updateAge(newAge: Int?) {
        _currentUser.value?.age = newAge
    }

    fun updateGender(gender: Int?) {
        _currentUser.value?.gender = gender
    }

    fun updateRole(role: String?) {
        _currentUser.value?.role = role
    }

    fun updateUserFavoriteFields(list: List<WorkHireField>) {
        _currentUser.value?.setFavouriteFields(list)
    }

    private val _createUserResult = MutableLiveData<Boolean?>()
    val createUserResult: LiveData<Boolean?> = _createUserResult

    fun createUserInBackendDatabase() {
        viewModelScope.launch {
            currentUser.value?.let {
                _createUserResult.value = AuthUserUtil.createUserInBackend(it)
            }
        }
    }

    fun getFieldOptions(): List<WorkHireField> {
        return FieldOptionsUtil.getFieldOptions()
    }

    private val _reloadFirebaseUserStatus = MutableLiveData<Boolean?>()
    val reloadFirebaseUserStatus: LiveData<Boolean?> = _reloadFirebaseUserStatus

    fun reloadFirebaseUser() {
        viewModelScope.launch {
            val result = AuthUserUtil.reloadFirebaseUser()
            _reloadFirebaseUserStatus.value = result
        }
    }

    private val _logoutUserStatus = MutableLiveData<Boolean?>()
    val logoutUserStatus: LiveData<Boolean?> = _logoutUserStatus

    fun logoutUser() {
        val result = AuthUserUtil.logoutUser()
        _logoutUserStatus.value = result
    }

    /* private val _checkUserExistsInBackend = MutableLiveData<Boolean?>()
     val checkUserExistsInBackend: LiveData<Boolean?> = _checkUserExistsInBackend

     fun checkExistsAndNotDisabled() {
         viewModelScope.launch {
             val result = AuthUserUtil.checkUserDocExists()
             _checkUserExistsInBackend.value = result
         }
     }*/
}