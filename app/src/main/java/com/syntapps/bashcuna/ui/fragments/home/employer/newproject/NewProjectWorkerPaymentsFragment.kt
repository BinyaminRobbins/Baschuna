package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.chip.Chip
import com.google.android.material.textfield.TextInputEditText
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.constants.PaymentMethodCodes
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class NewProjectWorkerPaymentsFragment : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var paymentAmountText: TextInputEditText
    private lateinit var numPplText: TextView
    private lateinit var minus: ImageButton
    private lateinit var plus: ImageButton

    private lateinit var cashChip: Chip
    private lateinit var bitChip: Chip
    private lateinit var payboxChip: Chip

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project_worker_payments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        paymentAmountText = view.findViewById(R.id.payment_amount_field)

        paymentAmountText.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank() || text.isNullOrEmpty()) {
                viewModel.newJobOffer.jobPaymentAmount = null
            } else viewModel.newJobOffer.jobPaymentAmount = text.toString().toInt()
        }

        numPplText = view.findViewById(R.id.num_people_text)
        minus = view.findViewById(R.id.btn_minus)
        minus.setOnClickListener {
            if (viewModel.newJobOffer.jobHireCount > 0) {
                viewModel.newJobOffer.jobHireCount--
                numPplText.text = viewModel.newJobOffer.jobHireCount.toString()
            }
        }
        plus = view.findViewById(R.id.btn_plus)
        plus.setOnClickListener {
            if (viewModel.newJobOffer.jobHireCount <= 4) {
                viewModel.newJobOffer.jobHireCount++
                numPplText.text = viewModel.newJobOffer.jobHireCount.toString()
            }
        }

        bitChip = view.findViewById(R.id.chip_bit)
        payboxChip = view.findViewById(R.id.chip_paybox)
        cashChip = view.findViewById(R.id.chip_cash)

        bitChip.setOnCheckedChangeListener { buttonView, isChecked ->
            PaymentMethodCodes.BIT.apply {
                if (isChecked) {
                    viewModel.newJobOffer.jobPaymentMethods?.add(this)
                    viewModel.newJobOffer.jobPaymentMethods?.sort()
                } else {
                    if (viewModel.newJobOffer.jobPaymentMethods?.contains(this) == true) {
                        viewModel.newJobOffer.jobPaymentMethods?.remove(this)
                        viewModel.newJobOffer.jobPaymentMethods?.sort()
                    }
                }
            }
        }
        cashChip.setOnCheckedChangeListener { buttonView, isChecked ->
            PaymentMethodCodes.CASH.apply {
                if (isChecked) {
                    viewModel.newJobOffer.jobPaymentMethods?.add(this)
                    viewModel.newJobOffer.jobPaymentMethods?.sort()
                } else {
                    if (viewModel.newJobOffer.jobPaymentMethods?.contains(this) == true) {
                        viewModel.newJobOffer.jobPaymentMethods?.remove(this)
                        viewModel.newJobOffer.jobPaymentMethods?.sort()
                    }
                }
            }
        }
        payboxChip.setOnCheckedChangeListener { buttonView, isChecked ->
            PaymentMethodCodes.PAYBOX.apply {
                if (isChecked) {
                    viewModel.newJobOffer.jobPaymentMethods?.add(this)
                    viewModel.newJobOffer.jobPaymentMethods?.sort()
                } else {
                    if (viewModel.newJobOffer.jobPaymentMethods?.contains(this) == true) {
                        viewModel.newJobOffer.jobPaymentMethods?.remove(this)
                        viewModel.newJobOffer.jobPaymentMethods?.sort()
                    }
                }
            }
        }

        if (viewModel.newJobOffer.jobPaymentAmount != null) {
            paymentAmountText.setText(viewModel.newJobOffer.jobPaymentAmount!!)
        }
        if (viewModel.newJobOffer.jobHireCount > 0) {
            numPplText.text = viewModel.newJobOffer.jobHireCount.toString()
        }
        if (!viewModel.newJobOffer.jobPaymentMethods.isNullOrEmpty()) {
            if (viewModel.newJobOffer.jobPaymentMethods!!.contains(PaymentMethodCodes.BIT)) {
                bitChip.isChecked = true
            }
            if (viewModel.newJobOffer.jobPaymentMethods!!.contains(PaymentMethodCodes.PAYBOX)) {
                payboxChip.isChecked = true
            }
            if (viewModel.newJobOffer.jobPaymentMethods!!.contains(PaymentMethodCodes.CASH)) {
                cashChip.isChecked = true
            }
        }

    }
}