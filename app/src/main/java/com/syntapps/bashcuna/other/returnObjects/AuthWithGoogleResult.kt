package com.syntapps.bashcuna.other.returnObjects

data class AuthWithGoogleResult(
    val isSuccess: Boolean,
    val errorMsg: String? = null,
    val isNewUser: Boolean = false
)
