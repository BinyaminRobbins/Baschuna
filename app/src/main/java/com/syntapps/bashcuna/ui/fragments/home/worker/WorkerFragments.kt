package com.syntapps.bashcuna.ui.fragments.home.worker

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

class WorkerFragments : Fragment() {

    private val viewModel: HomeActivityViewModel by activityViewModels()
    private lateinit var bottomNavView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragments_worker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController =
            Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_workers)

        bottomNavView = view.findViewById(R.id.bottom_navigation)
        bottomNavView.setOnItemSelectedListener {
            val destination: Int = when (it.itemId) {
                R.id.worker_navigation_charts -> {
                    R.id.workerChartsFragment
                }
                R.id.worker_navigation_work -> {
                    R.id.workerAvailableJobsFragment
                }
                R.id.worker_navigation_schedule -> {
                    //todo create fragment
                    0
                }
                R.id.worker_navigation_chats -> {
                    //todo create fragment
                    0
                }
                R.id.worker_navigation_notifications -> {
                    //todo create fragment
                    0
                }
                else -> return@setOnItemSelectedListener false
            }
            navController.popBackStack()
            navController.navigate(destination)
            true
        }
    }
}