package com.syntapps.bashcuna.other

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class WorkHireField(
    var fieldName: String? = null,
    @Exclude
    @JvmField
    var fieldIcon: Int? = null,
    @Exclude
    @JvmField
    var isSelected: Boolean = false
)