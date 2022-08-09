package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shawnlin.numberpicker.NumberPicker
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.CurrentUser
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthDetailsOne : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var agePicker: NumberPicker
    private lateinit var emailText: TextView
    private lateinit var nameText: TextView
    private lateinit var manRadioButton: RadioButton
    private lateinit var womanRadioButton: RadioButton


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_details_one, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailText = view.findViewById(R.id.emailText)
        nameText = view.findViewById(R.id.nameText)

        authViewModel.currentUser.observe(viewLifecycleOwner) {
            it?.let {
                emailText.text = it.email
                nameText.text = it.name
            }
        }

        agePicker = view.findViewById(R.id.age_picker)
        agePicker.setOnValueChangedListener { _, _, newVal ->
            authViewModel.updateAge(newVal)
        }

        manRadioButton = view.findViewById(R.id.man_button)
        womanRadioButton = view.findViewById(R.id.woman_button)

        manRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) authViewModel.updateGender(CurrentUser.GENDER_MAN)
        }
        womanRadioButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) authViewModel.updateGender(CurrentUser.GENDER_WOMAN)
        }

    }

}
