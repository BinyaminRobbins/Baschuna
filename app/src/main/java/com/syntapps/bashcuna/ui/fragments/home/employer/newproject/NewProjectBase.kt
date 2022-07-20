package com.syntapps.bashcuna.ui.fragments.home.employer.newproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.returnObjects.CreateNewProjectResult
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class NewProjectBase : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()

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

        val controller: NavController? =
            activity?.let { Navigation.findNavController(it, R.id.nav_host_fragment_new_project) }

        controller?.addOnDestinationChangedListener { _, destination, _ ->
            when (destination) {
                controller.findDestination(R.id.newProjectTypeFragment) -> {
                    viewModel.currentPosition.postValue(0)
                }
                controller.findDestination(R.id.newProjectDateTimeFragment) -> {
                    viewModel.currentPosition.postValue(1)
                }
                controller.findDestination(R.id.newProjectLocationFragment) -> {
                    viewModel.currentPosition.postValue(2)
                }
                controller.findDestination(R.id.newProjectDescriptionFragment) -> {
                    viewModel.currentPosition.postValue(3)
                }
                controller.findDestination(R.id.newProjectWorkerPaymentsFragment) -> {
                    viewModel.currentPosition.postValue(4)
                }
            }
        }
        nextButton.setOnClickListener {
            val pos = viewModel.currentPosition.value
            val fragmentsAndPositions: Map<Int, Int> = viewModel.fragmentsAndPositions
            pos?.let { currentPosition ->
                fragmentsAndPositions[currentPosition + 1]?.let { destination: Int ->
                    val result = checkDetailsFilled(currentPosition)
                    if (result.isSuccess) {
                        controller?.navigate(
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
                            Toast.makeText(view.context, "something went wrong", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }
    }

    private fun checkDetailsFilled(pos: Int): CreateNewProjectResult {
        val result = CreateNewProjectResult(false)
        result.isSuccess = when (pos) {
            0 -> viewModel.newJobOffer.jobFieldCode != null
            1 -> viewModel.newJobOffer.jobStartTime != null && viewModel.newJobOffer.jobEndTime != null
            2 -> viewModel.newJobOffer.jobGeoCoordinates != null
            3 -> viewModel.newJobOffer.jobDescription != null
            4 -> viewModel.newJobOffer.jobPaymentAmount != null && viewModel.newJobOffer.jobHireCount > 0 && !viewModel.newJobOffer.jobPaymentMethods.isNullOrEmpty()

            else -> false
        }
        if (!result.isSuccess) result.result = getString(R.string.fill_all_fields)
        return result
    }
}