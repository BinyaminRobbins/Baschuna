package com.syntapps.bashcuna.ui.fragments.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
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
import com.syntapps.bashcuna.other.AuthNewUserDetailsUtil
import com.syntapps.bashcuna.other.adapters.AuthDetailsAdapter
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthNewUserDetails : Fragment() {

    private val authViewModel: AuthViewModel by activityViewModels()
    private lateinit var welcomeMsg: TextView
    private lateinit var profilePic: ImageView
    private lateinit var navController: NavController
    private lateinit var detailsViewPager: ViewPager2
    private lateinit var viewPagerAdapter: AuthDetailsAdapter
    private lateinit var tabDots: TabLayout
    private lateinit var progressBar: ProgressBar

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

        authViewModel.authUser.observe(viewLifecycleOwner) {
            it?.let {
                welcomeMsg.text = "שלום, ${it.name?.split("//s".toRegex())?.get(0)}!"
                Glide.with(profilePic)
                    .load(it.photoUrl)
                    .into(profilePic)
            }

        }

        authViewModel.createUserResult.observe(viewLifecycleOwner) {
            // TODO: 05/08/2022 fix this up to new scheme
            it?.let {
                if (it) {
                    navController.popBackStack()
                    navController.navigate(R.id.homeActivity)
                } else {
                    Toast.makeText(requireContext(), "Something went wrong...", Toast.LENGTH_SHORT)
                        .show()
                    progressBar.visibility = View.INVISIBLE
                    fab.isClickable = true
                }
            }
        }
        detailsViewPager = view.findViewById(R.id.detailsViewPager)
        detailsViewPager.isUserInputEnabled = false
        viewPagerAdapter = AuthDetailsAdapter(
            requireContext(), arrayListOf(
                AuthDetailsOne(), AuthDetailsTwo(), AuthDetailsThree(), AuthDetailsFour()
            ), this
        )
        detailsViewPager.adapter = viewPagerAdapter

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_auth)

        tabDots = view.findViewById(R.id.tabDots)
        TabLayoutMediator(tabDots, detailsViewPager) { _, _ -> }.attach()

        progressBar = view.findViewById(R.id.progress_bar)

        fab = view.findViewById(R.id.next_button)
        fab.setOnClickListener {
            when (detailsViewPager.currentItem) {
                0 -> { //AuthDetailsOne
                    authViewModel.currentUser.value?.let {
                        if (AuthNewUserDetailsUtil.validatePos0(it)) {
                            detailsViewPager.currentItem++
                        }
                    }
                }
                1 -> {
                    authViewModel.currentUser.value?.let {
                        if (AuthNewUserDetailsUtil.validatePos1(it)) {
                            detailsViewPager.currentItem++
                        }
                    }
                }
                2 -> {
                    authViewModel.currentUser.value?.let {
                        if (AuthNewUserDetailsUtil.validatePos2(it)) {
                            detailsViewPager.currentItem++
                        } else {
                            context?.let { itContext ->
                                Toast.makeText(
                                    itContext,
                                    "תבחרו תחומים מעודפים",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
                3 -> {
                    authViewModel.currentUser.value?.let {
                        if (AuthNewUserDetailsUtil.validatePos3(it)) {
                            //build completed user
                            authViewModel.createUserInBackendDatabase()
                            progressBar.visibility = View.VISIBLE
                            fab.isClickable = false
                        } else context?.let { itContext ->
                            Toast.makeText(
                                itContext,
                                "לא ניתן להמשיך – חובה להקליד משהו",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }
}