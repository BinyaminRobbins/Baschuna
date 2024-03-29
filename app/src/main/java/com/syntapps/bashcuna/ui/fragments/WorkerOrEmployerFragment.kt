package com.syntapps.bashcuna.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.CurrentUser
import com.syntapps.bashcuna.ui.viewmodels.CurrentUserViewModel

class WorkerOrEmployerFragment : Fragment() {

    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employer_or_worker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        currentUserViewModel.currentUser.observe(viewLifecycleOwner) {
            when (it?.role) {
                CurrentUser.ROLE_EMPLOYER -> {
                    navigateAsEmployer()
                }
                CurrentUser.ROLE_WORKER -> {
                    navigateAsWorker()
                }
                CurrentUser.ROLE_BOTH -> {
                    AlertDialog.Builder(context, R.style.MyDialogTheme)
                        .setTitle(getString(R.string.choose_role))
                        .setMessage(getString(R.string.choose_role_msg))
                        .setPositiveButton(
                            getString(R.string.employer)
                        ) { dialog, which ->
                            navigateAsEmployer()
                        }
                        .setNeutralButton(getString(R.string.worker)) { dialog, which ->
                            navigateAsWorker()
                        }.setOnDismissListener {
                            navigateAsEmployer() //Employer is default if user can be employer
                        }.show()
                }
            }
        }

    }

    private fun navigateAsEmployer() {
        navController.popBackStack()
        navController.navigate(R.id.employerFragments)
    }

    private fun navigateAsWorker() {
        navController.popBackStack()
        navController.navigate(R.id.workerFragments)
    }
}