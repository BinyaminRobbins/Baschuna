package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.FieldOptionsUtil
import com.syntapps.bashcuna.data.JobOffer
import com.syntapps.bashcuna.data.JobsCollectionUtil
import com.syntapps.bashcuna.other.returnObjects.CreateNewProjectResult
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val currentPosition = MutableLiveData(0)//the position of the create new project flow
    val fragmentsAndPositions = mapOf(
        0 to R.id.newProjectTypeFragment,
        1 to R.id.newProjectDateTimeFragment,
        2 to R.id.newProjectLocationFragment,
        3 to R.id.newProjectDescriptionFragment,
        4 to R.id.newProjectWorkerPaymentsFragment
    )


    var newJobOffer = JobOffer()

    private val _createJobResult = MutableLiveData<CreateNewProjectResult>()
    val createJobResult: LiveData<CreateNewProjectResult> = _createJobResult

    fun createNewJob() {
        viewModelScope.launch {
            val result = JobsCollectionUtil.createNewJob(newJobOffer)
            _createJobResult.value = result
        }
    }

    fun getFieldOptions() = FieldOptionsUtil.getFieldOptions()
}