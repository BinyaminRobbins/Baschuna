package com.syntapps.bashcuna.other

object CurrentUser {

    val GENDER_MAN = 0
    val GENDER_WOMAN = 1

    var uid: String? = null
    private var name: String? = null
    private var email: String? = null
    var age: Int? = null
    var gender: Int? = null

    var firstName: String? = null

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

}