package com.syntapps.bashcuna.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.HomeActivityViewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var viewModel: HomeActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProvider(this)[HomeActivityViewModel::class.java]
        setupNavController()
        viewModel.buildUser()
        viewModel.getUser().observe(this) {
            // TODO: 21/06/2022 Here is where we will load all the activity level ...
            //  ... info - related to the user (top profile img, etc.)
        }
    }

    private fun setupNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_home)
    }
}