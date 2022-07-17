package com.syntapps.bashcuna.ui.fragments.home.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.Timestamp
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.adapters.FieldsOptionsAdapter
import com.syntapps.bashcuna.other.constants.PaymentMethodCodes
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel
import java.util.*

class NewProjectFragment : Fragment(), FieldsOptionsAdapter.OnFieldSelectedListener {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var fieldsRecyclerView: RecyclerView
    private lateinit var fieldsOptionsAdapter: FieldsOptionsAdapter
    private lateinit var fieldOptions: List<WorkHireField>

    private lateinit var progressBar: ProgressBar
    private lateinit var finishedButton: Button
    private lateinit var descriptionText: EditText
    private lateinit var paymentAmountText: TextInputEditText
    private lateinit var numPplText: TextView
    private lateinit var minus: ImageButton
    private lateinit var plus: ImageButton

    private lateinit var currentOffer: JobOffer
    private lateinit var dateField: TextInputLayout
    private lateinit var startTimeField: TextInputLayout
    private lateinit var endTimeField: TextInputLayout

    private val dateCalendar = Calendar.getInstance()
    private val startCalendar = Calendar.getInstance()
    private val endCalendar = Calendar.getInstance()

    private lateinit var cashChip: Chip
    private lateinit var bitChip: Chip
    private lateinit var payboxChip: Chip

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentOffer = viewModel.newJobOffer

        descriptionText = view.findViewById(R.id.description_text)
        paymentAmountText = view.findViewById(R.id.payment_amount_field)

        progressBar = view.findViewById(R.id.pBar)
        finishedButton = view.findViewById(R.id.finishedButton)

        bitChip = view.findViewById(R.id.chip_bit)
        payboxChip = view.findViewById(R.id.chip_paybox)
        cashChip = view.findViewById(R.id.chip_cash)

        bitChip.setOnCheckedChangeListener { buttonView, isChecked ->
            PaymentMethodCodes.BIT.apply {
                if (isChecked) {
                    currentOffer.jobPaymentMethods?.add(this)
                    currentOffer.jobPaymentMethods?.sort()
                    updateJobLiveData()
                } else {
                    if (currentOffer.jobPaymentMethods?.contains(this) == true) {
                        currentOffer.jobPaymentMethods?.remove(this)
                        currentOffer.jobPaymentMethods?.sort()
                        updateJobLiveData()
                    }
                }
            }
        }
        cashChip.setOnCheckedChangeListener { buttonView, isChecked ->
            PaymentMethodCodes.CASH.apply {
                if (isChecked) {
                    currentOffer.jobPaymentMethods?.add(this)
                    currentOffer.jobPaymentMethods?.sort()
                    updateJobLiveData()
                } else {
                    if (currentOffer.jobPaymentMethods?.contains(this) == true) {
                        currentOffer.jobPaymentMethods?.remove(this)
                        currentOffer.jobPaymentMethods?.sort()
                        updateJobLiveData()
                    }
                }
            }
        }
        payboxChip.setOnCheckedChangeListener { buttonView, isChecked ->
            PaymentMethodCodes.PAYBOX.apply {
                if (isChecked) {
                    currentOffer.jobPaymentMethods?.add(this)
                    currentOffer.jobPaymentMethods?.sort()
                    updateJobLiveData()
                } else {
                    if (currentOffer.jobPaymentMethods?.contains(this) == true) {
                        currentOffer.jobPaymentMethods?.remove(this)
                        currentOffer.jobPaymentMethods?.sort()
                        updateJobLiveData()
                    }
                }
            }
        }

        numPplText = view.findViewById(R.id.num_people_text)
        minus = view.findViewById(R.id.btn_minus)
        minus.setOnClickListener {
            if (currentOffer.jobHireCount > 0) {
                currentOffer.jobHireCount--
                updateJobLiveData()
            }
        }
        plus = view.findViewById(R.id.btn_plus)
        plus.setOnClickListener {
            if (currentOffer.jobHireCount <= 4) {
                currentOffer.jobHireCount++
                updateJobLiveData()
            }
        }

        descriptionText.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank() || text.isNullOrEmpty()) {
                currentOffer.jobDescription = null
            } else currentOffer.jobDescription = text.toString()
            updateJobLiveData()
        }

        paymentAmountText.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank() || text.isNullOrEmpty()) {
                currentOffer.jobPaymentAmount = null
            } else currentOffer.jobPaymentAmount = text.toString().toInt()
            updateJobLiveData()
        }

        viewModel.newJobOfferLiveData.observe(viewLifecycleOwner) { offer ->
            numPplText.text = offer.jobHireCount.toString()
        }

        viewModel.createNewProjectResult?.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isSuccess) {
                    progressBar.isVisible = false
                    Navigation.findNavController(view).popBackStack(R.id.jobsFragment, true)
                } else {
                    Toast.makeText(view.context, it.result, Toast.LENGTH_SHORT).show()
                    progressBar.isVisible = false
                }
            }
        }

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

        finishedButton.setOnClickListener {
            createNewProject()
        }
    }

    override fun onFieldSelected(field: WorkHireField) {
        currentOffer.jobFieldCode = field.fieldName
        updateJobLiveData()
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


    override fun onDestroyView() {
        super.onDestroyView()
        progressBar.isVisible = false
    }

    private fun createNewProject() {
        if (
            currentOffer.jobStartTime != null
            &&
            currentOffer.jobEndTime != null
            &&
            currentOffer.jobFieldCode != null
            &&
            currentOffer.jobPaymentAmount != null
            &&
            !currentOffer.jobDescription.isNullOrEmpty()
            &&
            currentOffer.jobHireCount > 0
            &&
            !currentOffer.jobPaymentMethods.isNullOrEmpty()
        ) {
            viewModel.createNewProject()
            progressBar.isVisible = true
            Toast.makeText(view?.context, getString(R.string.creating_project), Toast.LENGTH_SHORT)
                .show()
        } else if (currentOffer.jobDescription.isNullOrEmpty()) {
            Toast.makeText(
                view?.context,
                getString(R.string.description_not_filled),
                Toast.LENGTH_SHORT
            ).show()
        } else if (currentOffer.jobPaymentAmount == null || currentOffer.jobPaymentAmount!! < 50) {
            Toast.makeText(
                view?.context,
                getString(R.string.payment_not_filled),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                view?.context,
                getString(R.string.missing_details),
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        viewModel.newJobOffer = JobOffer()
    }
}