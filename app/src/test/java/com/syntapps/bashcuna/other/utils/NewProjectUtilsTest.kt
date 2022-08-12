package com.syntapps.bashcuna.other.utils

import com.google.common.truth.Truth.assertThat
import com.google.firebase.Timestamp
import org.junit.Test

class NewProjectUtilsTest {

    //Pos0
    @Test
    fun `null field code returns false`() {
        val result = NewProjectUtils.validatePos0(null)
        assertThat(result).isFalse()
    }

    @Test
    fun `blank field code returns false`() {
        val result = NewProjectUtils.validatePos0("")
        assertThat(result).isFalse()
    }

    @Test
    fun `empty field code returns false`() {
        val result = NewProjectUtils.validatePos0("  ")
        assertThat(result).isFalse()
    }

    //Pos1
    @Test
    fun `null start time returns false`() {
        val result = NewProjectUtils.validatePos1(null, Timestamp.now())
        assertThat(result).isFalse()
    }

    @Test
    fun `null end time returns false`() {
        val result = NewProjectUtils.validatePos1(Timestamp.now(), null)
        assertThat(result).isFalse()
    }

    @Test
    fun `start time after end time returns false`() {
        val currentTime = Timestamp.now()
        val futureTime = Timestamp(currentTime.seconds * 2, 0)
        val result = NewProjectUtils.validatePos1(futureTime, currentTime)
        assertThat(result).isFalse()
    }

    //Pos2
    @Test
    fun `geopoint is null`() {
        val result = NewProjectUtils.validatePos2(null)
        assertThat(result).isFalse()
    }

    //Pos3
    @Test
    fun `description is null`() {
        val result = NewProjectUtils.validatePos3(null)
        assertThat(result).isFalse()
    }

    @Test
    fun `description is to short`() {
        val result = NewProjectUtils.validatePos3("one two threewords")
        assertThat(result).isFalse()
    }

    //Pos4
    @Test
    fun `null payment amount returns false`() {
        val result = NewProjectUtils.validatePos4(null, 1, mutableListOf(0, 1))
        assertThat(result).isFalse()
    }

    @Test
    fun `zero payment amount returns false`() {
        val result = NewProjectUtils.validatePos4(0, 1, mutableListOf(0, 1))
        assertThat(result).isFalse()
    }

    @Test
    fun `minus payment amount returns false`() {
        val result = NewProjectUtils.validatePos4(-1, 1, mutableListOf(0, 1))
        assertThat(result).isFalse()
    }

    @Test
    fun `null hire count returns false`() {
        val result = NewProjectUtils.validatePos4(1, null, mutableListOf(0, 1))
        assertThat(result).isFalse()
    }

    @Test
    fun `zero hire count returns false`() {
        val result = NewProjectUtils.validatePos4(1, 0, mutableListOf(0, 1))
        assertThat(result).isFalse()
    }

    @Test
    fun `minus hire count returns false`() {
        val result = NewProjectUtils.validatePos4(1, -1, mutableListOf(0, 1))
        assertThat(result).isFalse()
    }

    @Test
    fun `null payment methods returns false`() {
        val result = NewProjectUtils.validatePos4(1, 1, null)
        assertThat(result).isFalse()
    }

    @Test
    fun `empty payment methods returns false`() {
        val result = NewProjectUtils.validatePos4(1, 1, mutableListOf())
        assertThat(result).isFalse()
    }
}