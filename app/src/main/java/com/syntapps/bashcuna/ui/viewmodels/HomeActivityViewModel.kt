package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.data.BashcunaMainRepository
import com.syntapps.bashcuna.other.CurrentUser

class HomeActivityViewModel : ViewModel() {

    private var mainRepository: BashcunaMainRepository = BashcunaMainRepository()

    private val currentUserLiveData = MutableLiveData<CurrentUser?>()
    fun buildUser() {
        mainRepository.buildCurrentUser()
        currentUserLiveData.postValue(mainRepository.getCurrentUser())
    }

    fun getUser(): MutableLiveData<CurrentUser?> {
        return currentUserLiveData
    }

}