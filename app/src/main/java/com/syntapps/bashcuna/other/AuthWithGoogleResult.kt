package com.syntapps.bashcuna.other

data class AuthWithGoogleResult(
    val isSuccess: Boolean,
    val errorMsg: String? = null,
    val isNewUser: Boolean = false
)
