package com.syntapps.bashcuna.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.adapters.AuthDetailsAdapter

class AuthNewUserDetails : Fragment() {

    private lateinit var detailsViewPager: ViewPager2
    private lateinit var viewPagerAdapter: AuthDetailsAdapter

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
        viewPagerAdapter = AuthDetailsAdapter(
            requireContext(), arrayListOf(
                AuthDetailsOne(), AuthDetailsTwo(), AuthDetailsThree()
            ), this
        )
    }
}