package com.syntapps.bashcuna.ui.fragments.home.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class EmployerFragments : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()

    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragments_employer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_employers)

        bottomNavView = view.findViewById(R.id.bottom_navigation)
        bottomNavView.setOnItemSelectedListener {
            val destination: Int = when (it.itemId) {
                R.id.employer_navigation_charts -> {
                    R.id.employerChartsFragment
                }
                R.id.employer_navigation_jobs -> {
                    R.id.employerJobsFragment
                }
                R.id.employer_navigation_chats -> {
                    //todo create fragment
                    0
                }
                R.id.employer_navigation_notifications -> {
                    //todo create fragment
                    0
                }
                else -> return@setOnItemSelectedListener false
            }
            navController.popBackStack()
            navController.navigate(destination)
            true
        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val id = when (destination.id) {
                R.id.employerChartsFragment -> {
                    R.id.employer_navigation_charts
                }
                R.id.employerJobsFragment -> {
                    R.id.employer_navigation_jobs
                }
                else -> return@addOnDestinationChangedListener
            }
            if (bottomNavView.selectedItemId != id) {
                bottomNavView.menu.findItem(R.id.employer_navigation_jobs).isChecked = true
            }
        }
    }
}