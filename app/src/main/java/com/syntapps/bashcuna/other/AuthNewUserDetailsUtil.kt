package com.syntapps.bashcuna.other

import com.syntapps.bashcuna.data.CurrentUser

object AuthNewUserDetailsUtil {

    fun validatePos0(user: CurrentUser): Boolean {
        if (user.age == null || user.age!! < 14) return false
        if (user.gender == null) return false
        return true
    }

    fun validatePos1(user: CurrentUser): Boolean {
        if (user.role == null) return false
        if (user.role!!.isBlank() || user.role!!.isEmpty()) return false
        if (user.role != CurrentUser.ROLE_BOTH && user.role != CurrentUser.ROLE_EMPLOYER && user.role != CurrentUser.ROLE_WORKER) return false
        return true
    }

    fun validatePos2(user: CurrentUser): Boolean {
        if (user.favoriteFields.isNullOrEmpty()) return false
        return true
    }

    fun validatePos3(user: CurrentUser): Boolean {
        if (user.userDescription.isNullOrBlank() || user.userDescription.isNullOrEmpty()) return false
        if (user.userDescription!!.length < 10) return false
        return true
    }
}