package com.syntapps.bashcuna.data

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.DocumentSnapshot
import com.syntapps.bashcuna.other.WorkHireField

data class CurrentUser(
    val userId: String,
    val name: String,
    val email: String,
    var age: Int? = null,
    var gender: Int? = null,
    val profileImg: String? = null,
    var role: String? = null,
    var favoriteFields: MutableList<WorkHireField>? = mutableListOf(),
    var userDescription: String? = null,
    var accountCreationDate: Timestamp? = Timestamp.now(),
) {
    companion object {
        private const val TAG = "CurrentUserData"
        const val GENDER_MAN = 0
        const val GENDER_WOMAN = 1
        const val ROLE_WORKER = "WORKER"
        const val ROLE_BOTH = "BOTH"
        const val ROLE_EMPLOYER = "EMPLOYER"

        fun DocumentSnapshot.toCurrentUser(): CurrentUser? {
            return try {
                val name = getString("name")!!
                val email = getString("email")!!
                val age = getLong("age")?.toInt()
                val gender = getLong("gender")?.toInt()
                val profileImg = getString("profileImg")
                val role = getString("role")
                val favoriteFields = get("favouriteFields") as MutableList<WorkHireField>?
                val userDescription = getString("description")
                val accountCreationDate = getTimestamp("accountCreationDate")

                CurrentUser(
                    userId = id,
                    name = name,
                    email = email,
                    age = age,
                    gender = gender,
                    profileImg = profileImg,
                    role = role,
                    favoriteFields = favoriteFields,
                    userDescription = userDescription,
                    accountCreationDate = accountCreationDate
                )


            } catch (e: Exception) {
                Log.e(TAG, "Error building CurrentUser", e)
                FirebaseCrashlytics.getInstance().log("Error building CurrentUser")
                FirebaseCrashlytics.getInstance().setCustomKey("user_id", id)
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }

        fun AuthUser.toCurrentUser(): CurrentUser? {
            return try {
                val id = uid
                val name = name!!
                val email = email!!
                val photoUrl = photoUrl!!

                CurrentUser(userId = id, name = name, email = email, profileImg = photoUrl)
            } catch (e: Exception) {
                Log.e(TAG, "Error converting AuthUser to CurrentUser", e)
                FirebaseCrashlytics.getInstance().log("Error converting AuthUser to CurrentUser")
                FirebaseCrashlytics.getInstance().recordException(e)
                null
            }
        }
    }

    fun setFavouriteFields(fields: List<WorkHireField>) {
        favoriteFields?.clear()
        for (item in fields) {
            if (item.isSelected) {
                favoriteFields?.add(item)
            }
        }
    }

}