package com.syntapps.bashcuna.ui.fragments.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.adapters.AuthDetailsAdapter
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthNewUserDetails : Fragment() {

    private val viewModel: AuthViewModel by activityViewModels()
    private lateinit var welcomeMsg: TextView
    private lateinit var profilePic: ImageView
    private lateinit var navController: NavController
    private lateinit var detailsViewPager: ViewPager2
    private lateinit var viewPagerAdapter: AuthDetailsAdapter
    private lateinit var tabDots: TabLayout

    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_new_user_details, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        welcomeMsg = view.findViewById(R.id.welcomeMsg)
        profilePic = view.findViewById(R.id.profilePic)

        viewModel.getCurrentUser()?.let {
            welcomeMsg.text = "שלום, ${it.firstName}!"
            Glide.with(profilePic)
                .load(it.profileUrl)
                .into(profilePic)
        }

        detailsViewPager = view.findViewById(R.id.detailsViewPager)
        detailsViewPager.isUserInputEnabled = false
        viewPagerAdapter = AuthDetailsAdapter(
            requireContext(), arrayListOf(
                AuthDetailsOne(), AuthDetailsTwo(), AuthDetailsThree()
            ), this
        )
        detailsViewPager.adapter = viewPagerAdapter

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_auth)

        tabDots = view.findViewById(R.id.tabDots)
        TabLayoutMediator(tabDots, detailsViewPager) { tab, position -> }.attach()

        fab = view.findViewById(R.id.next_button)
        fab.setOnClickListener {
            when (detailsViewPager.currentItem) {
                0 -> { //AuthDetailsOne
                    viewModel.getCurrentUser()?.let {
                        if (it.age != null && it.gender != null) {
                            detailsViewPager.currentItem++
                        }
                    }
                }
                1 -> {
                    viewModel.getCurrentUser()?.let {
                        if (it.getRole() != null) {
                            detailsViewPager.currentItem++
                        }
                    }
                }
                2 -> {

                }
            }
        }
    }
}