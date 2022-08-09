package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.CurrentUser
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel
import de.hdodenhof.circleimageview.CircleImageView

class AuthDetailsTwo : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()

    private lateinit var workerOption: CircleImageView
    private lateinit var employerOption: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_details_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workerOption = view.findViewById(R.id.worker_option)
        employerOption = view.findViewById(R.id.employer_option)

        workerOption.setOnClickListener {
            selectWorker()
        }
        employerOption.setOnClickListener {
            selectEmployer()
        }


    }

    private fun selectWorker() {
        workerOption.elevation = 2F
        employerOption.elevation = 0F
        workerOption.alpha = 1F
        employerOption.alpha = 0.6F
        authViewModel.updateRole(CurrentUser.ROLE_WORKER)
    }

    private fun selectEmployer() {
        workerOption.elevation = 0F
        employerOption.elevation = 2F
        workerOption.alpha = 0.6F
        employerOption.alpha = 1F
        authViewModel.updateRole(CurrentUser.ROLE_EMPLOYER)
    }
}