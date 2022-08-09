package com.syntapps.bashcuna.data

import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.WorkHireField

object FieldOptionsUtil {

    private const val TAG = "FieldOptionsUtil"

    fun getFieldOptions(): List<WorkHireField> {
        return listOf(
            WorkHireField("בייביסיטינג", R.drawable.syt_field_babysitting),
            WorkHireField("עבודת גינה/בחוץ", R.drawable.syt_field_gardening),
            WorkHireField("בישול", R.drawable.syt_field_cooking),
            WorkHireField("קניות", R.drawable.syt_field_shopping),
            WorkHireField("משלוחים", R.drawable.syt_field_delivery),
            WorkHireField("שיפוצים", R.drawable.syt_field_renovations)
        )
    }
}