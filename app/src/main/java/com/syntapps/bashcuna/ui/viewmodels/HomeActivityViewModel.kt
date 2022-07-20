package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.BashcunaMainRepository
import com.syntapps.bashcuna.other.CurrentUser
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.other.WorkHireField
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

    var newJobOffer = JobOffer()

    val createNewProjectResult = mainRepository?.getCreateNewProjectResult()
    fun createNewProject() {
        mainRepository?.createNewProject(newJobOffer)
    }

    val currentPosition = MutableLiveData(0)//the position of the create new project flow
    val fragmentsAndPositions = mapOf(
        0 to R.id.newProjectTypeFragment,
        1 to R.id.newProjectDateTimeFragment,
        2 to R.id.newProjectLocationFragment,
        3 to R.id.newProjectDescriptionFragment,
        4 to R.id.newProjectWorkerPaymentsFragment,
        5 to R.id.newProjectSummary
    )

    val userLocation = mutableMapOf("LAT" to 31.8903, "LONG" to 35.0104)
    val lastLocationLiveData = MutableLiveData<Map<String, Double>>(userLocation)
}