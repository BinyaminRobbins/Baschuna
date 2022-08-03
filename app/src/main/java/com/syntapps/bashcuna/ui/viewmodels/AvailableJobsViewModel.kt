package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntapps.bashcuna.data.EmployerData
import com.syntapps.bashcuna.other.JobApplicationsService
import com.syntapps.bashcuna.other.JobOffer
import kotlinx.coroutines.launch

class AvailableJobsViewModel : ViewModel() {
    private val _availableJobs = MutableLiveData<List<JobOffer?>?>()
    val availableJobs: LiveData<List<JobOffer?>?> = _availableJobs

    private val _employerInfo = MutableLiveData<EmployerData?>()
    val employerInfo: LiveData<EmployerData?> = _employerInfo

    init {
        viewModelScope.launch {
            val jobs = JobApplicationsService.getAvailableJobs()
            _availableJobs.value = jobs
        }
    }

    fun getEmployerData(employerId: String) {
        viewModelScope.launch {
            _employerInfo.value = JobApplicationsService.getEmployerData(employerId)
        }
    }
}