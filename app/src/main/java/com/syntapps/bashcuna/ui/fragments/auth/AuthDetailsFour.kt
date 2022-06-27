package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthDetailsFour : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var description: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_details_four, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        description = view.findViewById(R.id.description_text)
        description.addTextChangedListener {
            viewModel.userDescriptionText = it.toString()
        }
    }
}