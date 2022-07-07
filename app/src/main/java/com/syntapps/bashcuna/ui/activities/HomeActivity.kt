package com.syntapps.bashcuna.ui.activities

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.Timestamp
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.other.CurrentUser
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class HomeActivity : AppCompatActivity() {

    private val TAG = "HomeActivityTAG"

    private lateinit var navController: NavController
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var topAppBar: MaterialToolbar
    private var menu: Menu? = null

    private fun setupNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        setupNavController()

        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)
        topAppBar.setNavigationOnClickListener {
            Log.i(TAG, "Nav Icon Clicked")
            // TODO: 07/07/2022 nav icon click
        }

        viewModel.getUser()?.observe(this) {
            val actionView: View? = menu?.findItem(R.id.menu_item_profile)?.actionView
            val profileImage = actionView?.findViewById<CircleImageView>(R.id.profileImageView)
            profileImage?.let { it1 ->
                Glide
                    .with(this)
                    .load(it?.profileUrl)
                    .into(it1)
            }

            if (it?.getRole() == CurrentUser.ROLE_EMPLOYER || it?.getRole() == CurrentUser.ROLE_BOTH) {
                viewModel.loadOfferedJobs()
                viewModel.getOfferedJobs()?.observe(this) {
                    viewModel.futureProjects.clear()
                    viewModel.pastProjects.clear()
                    it.forEach { it_jobOffer ->
                        val jobEndDate = it_jobOffer?.jobEndTime?.toDate()
                        try {
                            if (jobEndDate?.compareTo(getDate())!! <= 0) {
                                viewModel.pastProjects.add(it_jobOffer)
                            } else if (jobEndDate > getDate()) {
                                viewModel.futureProjects.add(it_jobOffer)
                            }
                        } catch (e: NullPointerException) {
                            Log.e("JobsFragmentTAG", "value is null")
                            Log.e("JobsFragmentTAG", e.message.toString())
                        }
                    }
                }
            } else if (it?.getRole() == CurrentUser.ROLE_WORKER) {

            }


            // TODO: 21/06/2022 Here is where we will load all the activity level ...
            //  ... info - related to the user (top profile img, etc.)
        }

        viewModel.buildUser()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i(TAG, "menu function")
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        this.menu = menu
        return true
    }

    private fun getDate(): Date {
        return Timestamp.now().toDate()
    }
}