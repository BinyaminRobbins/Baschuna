package com.syntapps.bashcuna.ui.fragments.home.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.firebase.Timestamp
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.FieldOptionsUtil
import com.syntapps.bashcuna.data.JobOffer
import com.syntapps.bashcuna.other.adapters.ProjectsAdapter
import com.syntapps.bashcuna.ui.viewmodels.CurrentUserViewModel
import com.syntapps.bashcuna.ui.viewmodels.MainViewModel

class EmployerJobsFragment : Fragment() {

    private lateinit var toggleGroup: MaterialButtonToggleGroup
    private lateinit var extendedFab: ExtendedFloatingActionButton
    private lateinit var viewPager: ViewPager2
    private lateinit var toggleStateAdapter: ToggleStateAdapter
    private val mainViewModel: MainViewModel by activityViewModels()
    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_employer_jobs, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)

        viewPager = view.findViewById(R.id.projects_viewpager)
        toggleStateAdapter =
            ToggleStateAdapter(
                this,
                listOf(
                    ToggleStateFutureProjects(),
                    ToggleStatePastProjects()
                )
            )
        viewPager.adapter = toggleStateAdapter


        toggleGroup = view.findViewById(R.id.toggleGroup)
        toggleGroup.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (toggleButton.checkedButtonId) {
                R.id.upcoming_jobs -> {
                    viewPager.currentItem = 0
                }
                R.id.past_jobs -> {
                    viewPager.currentItem = 1
                }
            }
        }
        toggleGroup.check(R.id.upcoming_jobs)

        extendedFab = view.findViewById(R.id.extended_fab)
        extendedFab.setOnClickListener {
            navController.navigate(R.id.newProjectBase)
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> {
                        toggleGroup.check(R.id.upcoming_jobs)
                    }
                    1 -> {
                        toggleGroup.check(R.id.past_jobs)
                    }
                }
            }
        })
        mainViewModel.currentPosition.postValue(-1)

        currentUserViewModel.loadOfferedJobs()
    }
}

class ToggleStatePastProjects : Fragment() {
    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()

    private var data: MutableList<JobOffer?> = mutableListOf()
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_past_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUserViewModel.offeredJobs.observe(viewLifecycleOwner) { list ->
            list?.let {
                data.clear()
                val now = Timestamp.now().toDate()
                list.filter {
                    (it.jobStartTime?.toDate())!!.after(now)
                }
                data.addAll(list)
                rv.adapter?.notifyItemRangeInserted(0, data.size - 1)
            }
        }

        rv = view.findViewById(R.id.projectsRV)
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rv.adapter =
            ProjectsAdapter(requireContext(), FieldOptionsUtil.getFieldOptions(), this, data)
    }
}

class ToggleStateFutureProjects : Fragment() {

    private val currentUserViewModel: CurrentUserViewModel by activityViewModels()

    private var data: MutableList<JobOffer?> = mutableListOf()
    private lateinit var rv: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_future_projects, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentUserViewModel.offeredJobs.observe(viewLifecycleOwner) { list ->
            list?.let {
                data.clear()
                val now = Timestamp.now().toDate()
                list.filter {
                    (it.jobStartTime?.toDate())!!.before(now)
                }
                data.addAll(list)
                rv.adapter?.notifyItemRangeInserted(0, data.size - 1)
            }
        }

        rv = view.findViewById(R.id.projectsRV)
        rv.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        rv.adapter =
            ProjectsAdapter(requireContext(), FieldOptionsUtil.getFieldOptions(), this, data)
    }
}

class ToggleStateAdapter(
    fragment: Fragment,
    private val fragmentsArray: List<Fragment>
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return fragmentsArray.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsArray[position]
    }

}


