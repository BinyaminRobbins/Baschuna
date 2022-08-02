package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntapps.bashcuna.other.JobApplicationsService
import com.syntapps.bashcuna.other.JobOffer
import kotlinx.coroutines.launch

class AvailableJobsViewModel : ViewModel() {
    private val _availableJobs = MutableLiveData<List<JobOffer?>>()
    val availableJobs: LiveData<List<JobOffer?>> = _availableJobs

    init {
        viewModelScope.launch {
            _availableJobs.value = JobApplicationsService.getAvailableJobs()
        }
    }
}