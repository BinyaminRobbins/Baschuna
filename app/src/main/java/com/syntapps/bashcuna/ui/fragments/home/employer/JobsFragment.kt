package com.syntapps.bashcuna.ui.fragments.home.employer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.syntapps.bashcuna.R

class JobsFragment : Fragment() {

    private lateinit var toggleGroup: MaterialButtonToggleGroup
    private lateinit var extendedFab: ExtendedFloatingActionButton
    private lateinit var viewPager: ViewPager2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_jobs, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toggleGroup = view.findViewById(R.id.toggleGroup)
        toggleGroup.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            when (toggleButton.checkedButtonId) {
                R.id.upcoming_jobs -> {
                    viewPager.currentItem++
                }
                R.id.past_jobs -> {
                    viewPager.currentItem--
                }
            }
        }
        extendedFab = view.findViewById(R.id.extended_fab)
        extendedFab.setOnClickListener {

        }

        viewPager = view.findViewById(R.id.projects_viewpager)
        viewPager.adapter =
            ToggleStateAdapter(this, listOf(ToggleStateFutureProjects(), ToggleStatePastProjects()))
        viewPager.isUserInputEnabled = true
    }


    inner class ToggleStateAdapter(
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

    inner class ToggleStatePastProjects : Fragment() {

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

            rv = view.findViewById(R.id.projectsRV)
        }
    }

    inner class ToggleStateFutureProjects : Fragment() {

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

            rv = view.findViewById(R.id.projectsRV)
        }
    }

}