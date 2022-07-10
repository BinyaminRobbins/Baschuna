package com.syntapps.bashcuna.ui.fragments.home.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.JobOffer
import com.syntapps.bashcuna.other.WorkHireField
import com.syntapps.bashcuna.other.adapters.FieldsOptionsAdapter
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class NewProjectFragment : Fragment(), FieldsOptionsAdapter.OnFieldSelectedListener {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var fieldsRecyclerView: RecyclerView
    private lateinit var fieldsOptionsAdapter: FieldsOptionsAdapter
    private lateinit var fieldOptions: List<WorkHireField>

    private lateinit var descriptionText: EditText
    private lateinit var paymentAmountText: TextInputEditText
    private lateinit var numPplText: TextView
    private lateinit var minus: ImageButton
    private lateinit var plus: ImageButton

    private lateinit var currentOffer: JobOffer

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

        fieldsRecyclerView = view.findViewById(R.id.fieldRv)
        fieldOptions = viewModel.getFieldOptions()
        fieldsOptionsAdapter = FieldsOptionsAdapter(requireContext(), fieldOptions, this)
        fieldsRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        fieldsRecyclerView.adapter = fieldsOptionsAdapter

        descriptionText = view.findViewById(R.id.description_text)
        paymentAmountText = view.findViewById(R.id.payment_amount_field)

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
    }

    override fun onFieldSelected(field: WorkHireField) {
        currentOffer.jobFieldCode = field.fieldName
        updateJobLiveData()
    }

    private fun updateJobLiveData() {
        viewModel.newJobOfferLiveData.value = currentOffer
    }
}