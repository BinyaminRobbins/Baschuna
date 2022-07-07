package com.syntapps.bashcuna.other

import com.google.firebase.Timestamp

data class JobOffer(
    val jobUserOfferingID: String,
    val jobFieldCode: String,
    val jobStartTime: Timestamp,
    val jobEndTime: Timestamp,
    val jobPaymentAmount: Int,
    val jobPaymentMethods: List<Int>,
    val jobLocation: String,
    val jobGeoCoordinates: Map<String, String>, //for latitude and longitude of the location - to get distance from user
    val users: List<User>,
    val jobUserCap: Int,
    var jobDescription: String,
    var jobIsClosed: Boolean = false
)
