package com.syntapps.bashcuna.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.syntapps.bashcuna.data.BashcunaMainRepository
import com.syntapps.bashcuna.other.CurrentUser

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


}