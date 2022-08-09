package com.syntapps.bashcuna.other

import com.google.common.truth.Truth.assertThat
import com.syntapps.bashcuna.data.CurrentUser
import org.junit.Test

class AuthNewUserDetailsUtilTest {

    //pos 0
    @Test
    fun `user age is null returns false`() {
        val user = CurrentUser(
            userId = "id",
            name = "name",
            email = "email",
            age = 10,
            gender = CurrentUser.GENDER_MAN
        )
        val result = AuthNewUserDetailsUtil.validatePos0(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user age is lessthan 14 returns false`() {
        val user = CurrentUser(
            userId = "id",
            name = "name",
            email = "email",
            age = 10,
            gender = CurrentUser.GENDER_MAN
        )
        val result = AuthNewUserDetailsUtil.validatePos0(user)
        assertThat(result).isFalse()
    }

    //pos1
    @Test
    fun `user role is null returns false`() {
        val user = CurrentUser(userId = "id", name = "name", email = "email", role = null)
        val result = AuthNewUserDetailsUtil.validatePos1(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user role is empty returns false`() {
        val user = CurrentUser(userId = "id", name = "name", email = "email", role = "")
        val result = AuthNewUserDetailsUtil.validatePos1(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user role is blank returns false`() {
        val user = CurrentUser(userId = "id", name = "name", email = "email", role = " ")
        val result = AuthNewUserDetailsUtil.validatePos1(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user role is not valid returns false`() {
        val user = CurrentUser(userId = "id", name = "name", email = "email", role = "BUILDER")
        val result = AuthNewUserDetailsUtil.validatePos1(user)
        assertThat(result).isFalse()
    }

    //pos2
    @Test
    fun `user favorite fields is empty returns false`() {
        val user = CurrentUser(
            userId = "id",
            name = "name",
            email = "email",
            favoriteFields = mutableListOf()
        )
        val result = AuthNewUserDetailsUtil.validatePos2(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user favorite fields is null returns false`() {
        val user = CurrentUser(userId = "id", name = "name", email = "email", favoriteFields = null)
        val result = AuthNewUserDetailsUtil.validatePos2(user)
        assertThat(result).isFalse()
    }

    //pos3
    @Test
    fun `user description is null returns false`() {
        val user =
            CurrentUser(userId = "id", name = "name", email = "email", userDescription = null)
        val result = AuthNewUserDetailsUtil.validatePos3(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user description is empty returns false`() {
        val user = CurrentUser(userId = "id", name = "name", email = "email", userDescription = "")
        val result = AuthNewUserDetailsUtil.validatePos3(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user description is blank returns false`() {
        val user =
            CurrentUser(userId = "id", name = "name", email = "email", userDescription = "  ")
        val result = AuthNewUserDetailsUtil.validatePos3(user)
        assertThat(result).isFalse()
    }

    @Test
    fun `user description length less than 10 chars returns false`() {
        val user =
            CurrentUser(userId = "id", name = "name", email = "email", userDescription = " short ")
        val result = AuthNewUserDetailsUtil.validatePos3(user)
        assertThat(result).isFalse()
    }
}