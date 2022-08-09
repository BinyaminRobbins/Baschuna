package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.returnObjects.CreateNewProjectResult
import com.syntapps.bashcuna.ui.viewmodels.CurrentUserViewModel
import com.syntapps.bashcuna.ui.viewmodels.MainViewModel

class NewProjectBase : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()

    private lateinit var pb: ProgressBar
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
        pb = view.findViewById(R.id.pb)

        val controller = Navigation.findNavController(view)

        controller.addOnDestinationChangedListener { _, destination, _ ->
            when (destination) {
                controller.findDestination(R.id.newProjectTypeFragment) -> {
                    mainViewModel.currentPosition.postValue(0)
                }
                controller.findDestination(R.id.newProjectDateTimeFragment) -> {
                    mainViewModel.currentPosition.postValue(1)
                }
                controller.findDestination(R.id.newProjectLocationFragment) -> {
                    mainViewModel.currentPosition.postValue(2)
                }
                controller.findDestination(R.id.newProjectDescriptionFragment) -> {
                    mainViewModel.currentPosition.postValue(3)
                }
                controller.findDestination(R.id.newProjectWorkerPaymentsFragment) -> {
                    mainViewModel.currentPosition.postValue(4)
                }
            }
        }
        nextButton.setOnClickListener { _ ->
            val pos = mainViewModel.currentPosition.value
            val fragmentsAndPositions: Map<Int, Int> = mainViewModel.fragmentsAndPositions
            pos?.let { currentPosition ->
                if (fragmentsAndPositions.maxByOrNull { it.key }?.key == (currentPosition)) { // we are on the last step
                    nextButton.isClickable = false
                    createNewProject()
                    return@setOnClickListener
                }
                fragmentsAndPositions[currentPosition + 1]?.let { destination: Int ->
                    val result = checkDetailsFilled(currentPosition)
                    if (result.isSuccess) {
                        controller.navigate(
                            destination
                        )
                    } else {
                        if (result.result != null) {
                            Toast.makeText(
                                view.context,
                                result.result.toString(),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                view.context,
                                "something went wrong",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        }

        mainViewModel.createJobResult.observe(viewLifecycleOwner) {
            if (it.isSuccess) {
                pb.isVisible = false
                Snackbar.make(view, getString(R.string.created_project), Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.continue_button)) {
                        controller.popBackStack(R.id.employerJobsFragment, true)
                    }
                    .show()
            } else {
                if (it.result != null) {
                    pb.isVisible = false
                    Snackbar.make(view, it.result!!, Snackbar.LENGTH_SHORT)
                        .show()
                    controller.popBackStack(R.id.employerJobsFragment, true)
                }
            }

        }
    }

    private fun checkDetailsFilled(pos: Int): CreateNewProjectResult {
        val result = CreateNewProjectResult(false)
        result.isSuccess = when (pos) {
            0 -> mainViewModel.newJobOffer.jobFieldCode != null
            1 -> mainViewModel.newJobOffer.jobStartTime != null && mainViewModel.newJobOffer.jobEndTime != null
            2 -> mainViewModel.newJobOffer.jobGeoCoordinates != null
            3 -> mainViewModel.newJobOffer.jobDescription != null
            4 -> mainViewModel.newJobOffer.jobPaymentAmount != null && mainViewModel.newJobOffer.jobHireCount > 0 && !mainViewModel.newJobOffer.jobPaymentMethods.isNullOrEmpty()
            5 -> true
            else -> false
        }
        if (!result.isSuccess) result.result = getString(R.string.fill_all_fields)
        return result
    }

    private fun createNewProject() {
        pb.isVisible = true
        mainViewModel.createNewJob()
    }
}