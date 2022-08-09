package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.MainViewModel

class NewProjectDescriptionFragment : Fragment() {

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var description: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        description = view.findViewById(R.id.description_text)
        description.addTextChangedListener {
            viewModel.newJobOffer.jobDescription = it.toString().trim()
        }

        if (viewModel.newJobOffer.jobDescription != null) {
            description.setText(viewModel.newJobOffer.jobDescription.toString())
        }
    }
}