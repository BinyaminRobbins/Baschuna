package com.syntapps.bashcuna.other.utils

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

object NewProjectUtils {

    fun validatePos0(fieldCode: String?): Boolean {
        if (fieldCode == null) {
            return false
        }
        if (fieldCode.isNullOrBlank()) {
            return false
        }
        if (fieldCode.isNullOrEmpty()) {
            return false
        }
        return true
    }

    fun validatePos1(startTime: Timestamp?, endTime: Timestamp?): Boolean {
        if (startTime == null || endTime == null) {
            return false
        }
        if (startTime.toDate().after(endTime.toDate())) {
            return false
        }
        return true
    }

    fun validatePos2(coordinates: GeoPoint?): Boolean {
        if (coordinates == null) {
            return false
        }
        return true
    }

    fun validatePos3(description: String?): Boolean {
        if (description == null) {
            return false
        }
        if (description.trim().split("\\s".toRegex()).toTypedArray().size <= 3) {
            return false
        }
        return true
    }

    fun validatePos4(
        jobPaymentAmount: Long?,
        jobHireCount: Long?,
        jobPaymentMethods: MutableList<Int>?
    ): Boolean {
        if (jobPaymentAmount == null || jobHireCount == null || jobPaymentMethods == null) {
            return false
        }
        if (jobPaymentAmount <= 0 || jobHireCount <= 0) {
            return false
        }
        if (jobPaymentMethods.isEmpty()) {
            return false
        }
        return true
    }

    fun validatePos5(): Boolean {

        return true
    }
}