package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.Timestamp
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel
import java.util.*

class NewProjectDateTimeFragment : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()
    private lateinit var currentOffer: JobOffer

    private lateinit var dateField: TextInputLayout
    private lateinit var startTimeField: TextInputLayout
    private lateinit var endTimeField: TextInputLayout

    private val dateCalendar = Calendar.getInstance()
    private val startCalendar = Calendar.getInstance()
    private val endCalendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project_date_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentOffer = viewModel.newJobOffer

        dateField = view.findViewById(R.id.date_field)
        startTimeField = view.findViewById(R.id.startTime_field)
        endTimeField = view.findViewById(R.id.endTime_field)

        dateField.setStartIconOnClickListener {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.MONTH, Calendar.DECEMBER)

            val constraintsBuilder =
                CalendarConstraints.Builder()
                    .setValidator(DateValidatorPointForward.now())
                    .setEnd(calendar.timeInMillis)

            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.choose_date))
                    .setTheme(R.style.Theme_Baschuna_DatePicker)
                    .setCalendarConstraints(constraintsBuilder.build())
                    .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
                    .build()
            datePicker.show(parentFragmentManager, null)
            datePicker.addOnPositiveButtonClickListener {
                dateField.hint = datePicker.headerText
                dateCalendar.timeInMillis = it
                fillCalendarsWithDate(
                    dateCalendar[Calendar.YEAR],
                    dateCalendar[Calendar.MONTH],
                    dateCalendar[Calendar.DAY_OF_MONTH]
                )
                currentOffer.jobStartTime = Timestamp(dateCalendar.time)
                updateJobLiveData()
            }
        }
        startTimeField.setStartIconOnClickListener {
            if (currentOffer.jobStartTime != null) {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setHour(12)
                        .setMinute(30)
                        .setTheme(R.style.Theme_Baschuna_TimePicker)
                        .build()


                picker.addOnPositiveButtonClickListener {
                    if (currentOffer.jobEndTime != null) {
                        currentOffer.jobEndTime = null
                        endTimeField.hint = "--:--"
                        updateJobLiveData()
                    }
                    val min: String = if (picker.minute == 0) "00" else picker.minute.toString()
                    startTimeField.hint = "${picker.hour}:$min"
                    startCalendar[Calendar.HOUR_OF_DAY] = picker.hour
                    startCalendar[Calendar.MINUTE] = picker.minute
                    currentOffer.jobStartTime = Timestamp(startCalendar.time)
                    updateJobLiveData()
                }
                picker.show(parentFragmentManager, null)
            } else { //if date has not yet been chosen
                Toast.makeText(
                    view.context,
                    getString(R.string.date_before_time),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        endTimeField.setStartIconOnClickListener {
            if (currentOffer.jobStartTime != null) {
                val picker =
                    MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_24H)
                        .setTheme(R.style.Theme_Baschuna_TimePicker)
                        .build()


                picker.addOnPositiveButtonClickListener {
                    if (picker.hour < startCalendar.get(Calendar.HOUR_OF_DAY)) {
                        Toast.makeText(
                            view.context,
                            getString(R.string.info_not_suitable),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@addOnPositiveButtonClickListener
                    } else if (picker.hour == startCalendar.get(Calendar.HOUR_OF_DAY)) {
                        if (picker.minute < startCalendar.get(Calendar.MINUTE)) {
                            Toast.makeText(
                                view.context,
                                getString(R.string.info_not_suitable),
                                Toast.LENGTH_SHORT
                            ).show()
                            return@addOnPositiveButtonClickListener
                        }
                    }
                    val min: String = if (picker.minute == 0) "00" else picker.minute.toString()
                    endTimeField.hint = "${picker.hour}:$min"
                    endCalendar[Calendar.HOUR_OF_DAY] = picker.hour
                    endCalendar[Calendar.MINUTE] = picker.minute
                    currentOffer.jobEndTime = Timestamp(endCalendar.time)
                    updateJobLiveData()
                }
                picker.show(parentFragmentManager, null)
            } else { //if date has not yet been chosen
                Toast.makeText(
                    view.context,
                    getString(R.string.fill_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.currentPosition.observe(viewLifecycleOwner) {
            if (it == 2) {
                findNavController().navigate(R.id.newProjectLocationFragment)
            }
        }
    }

    private fun updateJobLiveData() {
        viewModel.newJobOfferLiveData.value = currentOffer
    }

    private fun fillCalendarsWithDate(year: Int, month: Int, day: Int) {
        startCalendar.also {
            it[Calendar.YEAR] = year
            it[Calendar.MONTH] = month
            it[Calendar.DAY_OF_MONTH] = day
        }
        endCalendar.also {
            it[Calendar.YEAR] = year
            it[Calendar.MONTH] = month
            it[Calendar.DAY_OF_MONTH] = day
        }
    }
}