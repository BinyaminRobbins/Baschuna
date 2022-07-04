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
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel
import de.hdodenhof.circleimageview.CircleImageView


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
}