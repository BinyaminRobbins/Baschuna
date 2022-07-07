package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.data.BashcunaMainRepository
import com.syntapps.bashcuna.other.CurrentUser
import com.syntapps.bashcuna.other.JobOffer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeActivityViewModel : ViewModel() {

    private var mainRepository: BashcunaMainRepository? = null

    init {
        mainRepository = BashcunaMainRepository()
    }

    private val currentUserLiveData = mainRepository?.getCurrentUserLiveData()

    fun getUser(): MutableLiveData<CurrentUser?>? {
        return currentUserLiveData
    }

    fun buildUser() {
        mainRepository?.buildCurrentUser()
    }

    private val offeredJobsLiveData = mainRepository?.getOfferedJobsLiveData()
    fun getOfferedJobs(): MutableLiveData<MutableList<JobOffer?>>? {
        return offeredJobsLiveData
    }

    fun loadOfferedJobs() { //the jobs offere
        CoroutineScope(Dispatchers.IO).launch {
            mainRepository?.loadOfferedJobs()
        }
    }


    val futureProjects = mutableListOf<JobOffer?>()
    val futureProjectsLiveData = MutableLiveData(futureProjects)
    val pastProjects = mutableListOf<JobOffer?>()
    val pastProjectsLiveData = MutableLiveData(pastProjects)
}