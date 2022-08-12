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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.data.JobOffer
import com.syntapps.bashcuna.other.UserLocationServices
import com.syntapps.bashcuna.ui.viewmodels.CurrentUserViewModel
import com.syntapps.bashcuna.ui.viewmodels.LocationViewModel
import com.syntapps.bashcuna.ui.viewmodels.MainViewModel
import de.hdodenhof.circleimageview.CircleImageView


class HomeActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var mainViewModel: MainViewModel
    private lateinit var currentUserViewModel: CurrentUserViewModel
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var topAppBar: MaterialToolbar
    private var menu: Menu? = null

    private lateinit var bottomNav: BottomNavigationView

    private fun setupNavController() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_home)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        locationViewModel = ViewModelProvider(this)[LocationViewModel::class.java]
        currentUserViewModel = ViewModelProvider(this)[CurrentUserViewModel::class.java]

        topAppBar = findViewById(R.id.topAppBar)
        setSupportActionBar(topAppBar)

        setupNavController()

        currentUserViewModel.currentUser.observe(this) {
            it?.let {
                val actionView: View? = menu?.findItem(R.id.menu_item_profile)?.actionView
                val profileImage = actionView?.findViewById<CircleImageView>(R.id.profileImageView)
                profileImage?.let { it1 ->
                    Glide
                        .with(this)
                        .load(it.profileImg)
                        .into(it1)
                }
                return@observe
            }
            Log.i("HomeActivityTAG","current usr is null")

        }

        mainViewModel.currentPosition.observe(this) { pos ->
            if (pos == -1) mainViewModel.newJobOffer = JobOffer()
        }

        aquireLocation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_menu, menu)
        this.menu = menu
        return true
    }

    private fun aquireLocation() {
        val userLocationServices = UserLocationServices(this, this)
        locationViewModel.getUserLocation(userLocationServices)
    }
}