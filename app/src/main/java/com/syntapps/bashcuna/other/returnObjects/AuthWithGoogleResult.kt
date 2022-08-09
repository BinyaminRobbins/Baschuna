package com.syntapps.bashcuna.other.returnObjects

import com.syntapps.bashcuna.data.AuthUser

data class AuthWithGoogleResult(
    val isSuccess: Boolean,
    val errorMsg: String? = null,
    val authUser: AuthUser? = null
)
