package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.shawnlin.numberpicker.NumberPicker
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthDetailsOne : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var agePicker: NumberPicker
    private lateinit var emailText: TextView
    private lateinit var nameText: TextView


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
        viewModel.getCurrentUser()?.let {
            emailText.text = it.getEmail()
            nameText.text = it.getDisplayName()
        }

        agePicker = view.findViewById(R.id.age_picker)
        agePicker.setOnValueChangedListener { picker, oldVal, newVal ->
            viewModel.getCurrentUser()?.age = newVal
        }

    }

}