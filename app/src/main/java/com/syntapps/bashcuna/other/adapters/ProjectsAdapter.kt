package com.syntapps.bashcuna.other.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.location.Geocoder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.JobOffer
import com.syntapps.bashcuna.other.User
import com.syntapps.bashcuna.other.WorkHireField
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ProjectsAdapter(
    private val mContext: Context,
    private val jobFields: List<WorkHireField>,
    private val fragment: Fragment,
    private val jobOffers: MutableList<JobOffer?>
) : RecyclerView.Adapter<ProjectsAdapter.mViewHolder>() {

    private fun getCalendar(date: Date?): Calendar? {
        date?.let {
            val calendar = Calendar.getInstance()
            calendar.time = date
            return calendar
        }
        return null
    }

    private fun getCalendarMinute(c: Calendar?): String {
        c?.let {
            val minute = c.get(Calendar.MINUTE)
            Log.i("MINUTE", minute.toString())
            if (minute < 10) { //i.e single digits
                val newMinute = "0$minute"
                Log.i("MINUTE", "new minute: " + newMinute.toInt().toString())
                return newMinute
            } else return minute.toString()
        }
        return "-1"
    }

    private fun getCalendarHour(c: Calendar?): String {
        c?.let {
            val hour = c.get(Calendar.HOUR_OF_DAY)
            if (hour < 10) { //i.e single digits
                val newHour = "0$hour"
                Log.i("MINUTE", "new minute: " + newHour.toInt().toString())
                return newHour
            } else return hour.toString()
        }
        return "-1"
    }

    inner class mViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var peopleRV: RecyclerView = itemView.findViewById(R.id.people_rv)
        private var timeText: TextView = itemView.findViewById(R.id.time_text)
        private var moneyText: TextView = itemView.findViewById(R.id.money_text)
        private var locationText: TextView = itemView.findViewById(R.id.location_text)
        private var fieldIcon: ImageView = itemView.findViewById(R.id.field_icon)
        private var fieldText: TextView = itemView.findViewById(R.id.field_text)
        private var dateText: TextView = itemView.findViewById(R.id.date_text)
        private var todayText: TextView = itemView.findViewById(R.id.today_text)

        @SuppressLint("SetTextI18n")
        fun setData(offer: JobOffer) {
            val startCalendar = getCalendar(offer.jobStartTime?.toDate())
            val endCalendar = getCalendar(offer.jobEndTime?.toDate())

            val currentDate = Calendar.getInstance()

            startCalendar?.let {
                if (currentDate[Calendar.YEAR] == startCalendar[Calendar.YEAR]
                    && currentDate[Calendar.MONTH] == startCalendar[Calendar.MONTH]
                ) {
                    if (currentDate[Calendar.DAY_OF_MONTH] == startCalendar[Calendar.DAY_OF_MONTH]) {
                        todayText.text = "Today, "
                        todayText.isVisible = true
                    } else if (currentDate[Calendar.DAY_OF_MONTH] == (startCalendar[Calendar.DAY_OF_MONTH] + 1)) {
                        todayText.text = "Yesterday, "
                        todayText.isVisible = true
                    } else if (currentDate[Calendar.DAY_OF_MONTH] == (startCalendar[Calendar.DAY_OF_MONTH] - 1)) {
                        todayText.text = "Tomorrow, "
                        todayText.isVisible = true
                    } else {
                        todayText.clearComposingText()
                        todayText.isVisible = false
                    }
                }

                val sFormat = SimpleDateFormat("E, MMM d", Locale.getDefault())
                dateText.text = sFormat.format(it.time)

                timeText.text =
                    "${getCalendarHour(it)}:${getCalendarMinute(it)}" +
                            "  -  " +
                            "${getCalendarHour(endCalendar)}:${getCalendarMinute(endCalendar)}"
            }

            moneyText.text = "₪${offer.jobPaymentAmount.toString()}"
            offer.jobGeoCoordinates?.let {
                val address = Geocoder(mContext).getFromLocation(it.latitude, it.longitude, 1)[0]
                locationText.text = "${address.thoroughfare} ${address.subThoroughfare}"
            }

            fieldText.text =
                offer.jobFieldCode // TODO: 06/07/2022 here we need to store in local db the fields and codes - then we will get the info by code

            for (field in jobFields){
                if(field.fieldName == offer.jobFieldCode){
                    Glide
                        .with(mContext)
                        .load(field.fieldIcon)
                        .into(fieldIcon)
                    break
                }
            }

            peopleRV.adapter = offer.users?.let { PeopleAdapter(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.project_card_item, parent, false)
        return mViewHolder(v)
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        jobOffers[position]?.let {
            holder.setData(it)
        }
    }

    override fun getItemCount(): Int {
        return jobOffers.size
    }

    inner class PeopleAdapter(
        private val users: List<User>
    ) : RecyclerView.Adapter<PeopleAdapter.PeopleViewHolder>() {

        inner class PeopleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val profileView: CircleImageView = itemView.findViewById(R.id.user_profileImg)
            private val userNameText: TextView = itemView.findViewById(R.id.user_name)

            fun setData(user: User) {
                userNameText.text = user.fullName
                CoroutineScope(Dispatchers.Main).launch {
                    fragment.context?.let {
                        Glide
                            .with(it)
                            .load(user.profileUrl)
                            .into(profileView)
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
            val v =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.project_user_item, parent, false)
            return PeopleViewHolder(v)
        }

        override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
            holder.setData(users[position])
        }

        override fun getItemCount(): Int {
            return users.size
        }

    }
}