package com.syntapps.bashcuna.other

import com.google.firebase.Timestamp
import com.google.firebase.firestore.GeoPoint

data class JobOffer(
    var jobUserOfferingID: String? = null,
    var jobFieldCode: String? = null,
    var jobStartTime: Timestamp? = null,
    var jobEndTime: Timestamp? = null,
    var jobPaymentAmount: Int? = null,
    var jobPaymentMethods: List<Int>? = null,
    var jobLocation: String? = null,
    var jobGeoCoordinates: GeoPoint? = null, //for latitude and longitude of the location - to get distance from user
    var users: List<User>? = null,
    var jobDescription: String? = null,
    var jobIsClosed: Boolean = false,
    var jobHireCount: Int = 0,
)
