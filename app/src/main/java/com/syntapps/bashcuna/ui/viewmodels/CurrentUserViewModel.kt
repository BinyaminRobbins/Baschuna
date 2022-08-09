package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntapps.bashcuna.data.CurrentUser
import com.syntapps.bashcuna.data.CurrentUserUtil
import com.syntapps.bashcuna.data.JobOffer
import com.syntapps.bashcuna.data.JobsCollectionUtil
import kotlinx.coroutines.launch

class CurrentUserViewModel : ViewModel() {
    private val _currentUser = MutableLiveData<CurrentUser?>()
    val currentUser: LiveData<CurrentUser?> = _currentUser

    init {
        viewModelScope.launch {
            val user = CurrentUserUtil.buildCurrentUser()
            _currentUser.value = user
        }
    }

    private val _offeredJobs = MutableLiveData<List<JobOffer>?>()
    val offeredJobs: LiveData<List<JobOffer>?> = _offeredJobs

    fun loadOfferedJobs() { // the jobs that this user has created as an Employer
        viewModelScope.launch {
            val jobs = CurrentUserUtil.loadOfferedJobs()
            _offeredJobs.value = jobs
        }
    }

}