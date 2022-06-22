package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.adapters.AuthDetailsAdapter
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthNewUserDetails : Fragment() {

    private val viewmodel: AuthViewModel by activityViewModels()
    private lateinit var detailsViewPager: ViewPager2
    private lateinit var viewPagerAdapter: AuthDetailsAdapter

    private lateinit var fab: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_auth_new_user_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailsViewPager = view.findViewById(R.id.detailsViewPager)
        detailsViewPager.isUserInputEnabled = false
        viewPagerAdapter = AuthDetailsAdapter(
            requireContext(), arrayListOf(
                AuthDetailsOne(), AuthDetailsTwo(), AuthDetailsThree()
            ), this
        )
        detailsViewPager.adapter = viewPagerAdapter

        fab = view.findViewById(R.id.next_button)
        fab.setOnClickListener {
            when (detailsViewPager.currentItem) {
                0 -> { //AuthDetailsOne
                    viewmodel.setDetailsFilled(
                        viewmodel.getCurrentUser()?.age != null
                                &&
                                viewmodel.getCurrentUser()?.gender != null
                    )
                }
                1 -> {

                }
                2 -> {

                }
            }
            if (viewmodel.getDetailsFilled()) {
                detailsViewPager.setCurrentItem(detailsViewPager.currentItem + 1, true)
            }
        }
    }
}