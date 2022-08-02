package com.syntapps.bashcuna.ui.fragments.home.worker

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.maps.model.LatLng
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.ui.viewmodels.AvailableJobsViewModel
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel
import com.syntapps.bashcuna.ui.viewmodels.LocationViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class WorkerAvailableJobsFragment : Fragment() {
    private lateinit var viewModel: AvailableJobsViewModel
    private val homeActivityViewModel: HomeActivityViewModel by activityViewModels()
    private val locationViewModel: LocationViewModel by activityViewModels()

    private lateinit var viewpager2: ViewPager2
    private val jobOffersData = mutableListOf<JobOffer>()
    private lateinit var counterText: TextView
    private var currentLocation: LatLng? = null
    private lateinit var spinKit: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worker_available_jobs, container, false)
    }

    @SuppressLint("LogConditional")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewpager2 = view.findViewById(R.id.availableJobsViewpager)
        counterText = view.findViewById(R.id.counter_text)
        spinKit = view.findViewById(R.id.spin_kit)

        locationViewModel.userLocation.observe(viewLifecycleOwner) {
            currentLocation = it
            Log.i("location update", "currentLocation set to : ${it.latitude}, ${it.longitude}")
        }

        currentLocation = locationViewModel.userLocation.value

        viewModel = ViewModelProvider(this)[AvailableJobsViewModel::class.java]
        viewModel.availableJobs.observe(viewLifecycleOwner) {
            it?.forEach { offer ->
                offer?.let {
                    spinKit.isVisible = false
                    viewpager2.adapter = AvailableJobsAdapter()
                    jobOffersData.add(offer)
                    viewpager2.adapter?.notifyItemInserted(jobOffersData.indexOf(offer))
                }
            }
        }

        viewpager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                counterText.text = "$position/${jobOffersData.size}"
            }
        })


    }

    inner class AvailableJobsAdapter :
        RecyclerView.Adapter<AvailableJobsAdapter.JobsViewHolder>() {

        inner class JobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val profileImage: CircleImageView =
                itemView.findViewById(R.id.profile_image)
            private val nameText: TextView = itemView.findViewById(R.id.name_text)
            private var moreAboutTextView: TextView =
                itemView.findViewById(R.id.more_about_text)
            private val timeLengthText: TextView = itemView.findViewById(R.id.time_length_text)
            private val distanceFromUser: TextView =
                itemView.findViewById(R.id.distance_from_user_text)
            private val fieldNameText: TextView = itemView.findViewById(R.id.field_name_text)
            private val descriptionText: TextView = itemView.findViewById(R.id.description_text)
            private val paymentAmountText: TextView =
                itemView.findViewById(R.id.payment_amount_text)
            private val paymentMethodRV: RecyclerView =
                itemView.findViewById(R.id.payment_method_rv)
            private val dateText: TextView =
                itemView.findViewById(R.id.date_text)

            @SuppressLint("SetTextI18n", "SimpleDateFormat")
            fun setData(offer: JobOffer) {
                val userOfferingID = offer.jobUserOfferingID

                moreAboutTextView.text = offer.jobDescription

                val startCalendar = Calendar.getInstance()
                startCalendar.timeInMillis = offer.jobStartTime?.toDate()?.time!!
                val endCalendar = Calendar.getInstance()
                endCalendar.timeInMillis = offer.jobEndTime?.toDate()?.time!!
                val result =
                    endCalendar[Calendar.HOUR_OF_DAY] - startCalendar[Calendar.HOUR_OF_DAY]
                timeLengthText.text = "$result ${getString(R.string.hours)}"

                for (item in homeActivityViewModel.getFieldOptions()) {
                    if (item.fieldName == offer.jobFieldCode) {
                        fieldNameText.text = item.fieldName
                    }
                }

                val distanceBetween = FloatArray(2)
                currentLocation?.let {
                    offer.jobGeoCoordinates?.let { geopoint ->
                        Location.distanceBetween(
                            it.latitude,
                            it.longitude,
                            geopoint.latitude,
                            geopoint.longitude,
                            distanceBetween
                        )
                    }
                }
                val distanceInKM = distanceBetween[0].toDouble() / 1000
                distanceFromUser.text =
                    "${String.format("%.2f", distanceInKM)} ${getString(R.string.kilometerAbr)}"

                descriptionText.text = offer.jobDescription
                val sFormat = SimpleDateFormat("dd/MMM/yyyy")
                dateText.text = sFormat.format(startCalendar.time)


            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
            val v =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.available_job_item, parent, false)
            return JobsViewHolder(v)
        }

        override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
            holder.setData(jobOffersData[position])
        }

        override fun getItemCount(): Int {
            return jobOffersData.size
        }
    }
}