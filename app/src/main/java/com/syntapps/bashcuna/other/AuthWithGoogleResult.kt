package com.syntapps.bashcuna.other

data class AuthWithGoogleResult(
    val isSuccess: Boolean,
    val currentUser: CurrentUser? = null,
    val errorMsg: String? = null
)
