package com.syntapps.bashcuna.other

import android.net.Uri

object CurrentUser {

    val GENDER_MAN = 0
    val GENDER_WOMAN = 1
    val ROLE_WORKER = "WORKER"
    val ROLE_EMPLOYER = "EMPLOYER"

    var uid: String? = null
    private var name: String? = null
    private var email: String? = null
    var age: Int? = null
    var gender: Int? = null
    var firstName: String? = null
    var profileUrl: Uri? = null
    private var role: String? = null

    fun setName(name: String?) {
        name?.let {
            this.name = it
            firstName = name.split("\\s+".toRegex())[0]

        }
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun getEmail(): String? {
        return email
    }

    fun getDisplayName(): String? {
        return name
    }

    fun getRole() = role
    fun setRole(role: String) {
        if (role == ROLE_WORKER || role == ROLE_EMPLOYER) {
            this.role = role
        }
    }

}