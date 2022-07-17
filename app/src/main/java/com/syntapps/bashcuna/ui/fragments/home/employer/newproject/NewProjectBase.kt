package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class NewProjectBase : Fragment(), View.OnClickListener {

    interface NextButtonClicked {
        fun next(): Boolean
    }

    private val viewModel: HomeActivityViewModel by activityViewModels()
    private lateinit var nextButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_new_project_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nextButton = view.findViewById(R.id.next_button)
        nextButton.setOnClickListener(this)

        val pb: ProgressBar = view.findViewById(R.id.pb)

        viewModel.currentPosition.observe(viewLifecycleOwner) {
            if (it == 4) {
                // TODO: 17/07/2022 make sure its the last pos
                pb.isVisible = true
            }
        }
    }

    override fun onClick(v: View?) {
        if (v?.id == nextButton.id) {
            when (viewModel.currentPosition.value) {
                0 -> {
                    if (viewModel.newJobOffer.jobFieldCode != null) {
                        viewModel.currentPosition.postValue(1)
                    }
                }
                1 -> {
                    if (viewModel.newJobOffer.jobStartTime != null && viewModel.newJobOffer.jobEndTime != null) {
                        viewModel.currentPosition.postValue(2)
                    }
                }
            }
        }
    }

}