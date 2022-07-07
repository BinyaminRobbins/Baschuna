package com.syntapps.bashcuna.other.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.other.User
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProjectsAdapter(
    private val fragment: Fragment,
    private val jobOffers: MutableList<JobOffer?>
) : RecyclerView.Adapter<ProjectsAdapter.mViewHolder>() {

    inner class mViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var peopleRV: RecyclerView = itemView.findViewById(R.id.people_rv)
        private var timeText: TextView = itemView.findViewById(R.id.time_text)
        private var moneyText: TextView = itemView.findViewById(R.id.money_text)
        private var locationText: TextView = itemView.findViewById(R.id.location_text)
        private var fieldText: TextView = itemView.findViewById(R.id.field_text)

        @SuppressLint("SetTextI18n")
        fun setData(offer: JobOffer) {
            timeText.text =
                offer.jobStartTime.toDate().time.toString() + " - " + offer.jobEndTime.toDate().time.toString()
            moneyText.text = offer.jobPaymentAmount.toString()
            locationText.text = offer.jobLocation
            fieldText.text =
                offer.jobFieldCode // TODO: 06/07/2022 here we need to store in local db the fields and codes - then we will get the info by code
            peopleRV.adapter = PeopleAdapter(offer.users)
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