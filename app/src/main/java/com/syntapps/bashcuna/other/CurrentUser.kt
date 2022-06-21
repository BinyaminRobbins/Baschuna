package com.syntapps.bashcuna.other

object CurrentUser {

    private var name: String? = null
    private var email: String? = null
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

}