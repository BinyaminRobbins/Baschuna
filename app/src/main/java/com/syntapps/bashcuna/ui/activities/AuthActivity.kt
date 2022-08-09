package com.syntapps.bashcuna.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R
import com.syntapps.bashcuna.ui.viewmodels.AuthViewModel

class AuthActivity : AppCompatActivity() {
    private lateinit var viewModel: AuthViewModel
    private lateinit var navController: NavController

    private var keepSplashOnScreen = true
    private val delay = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_auth)
        //check initially if the user is logged in to trigger the observer
        viewModel.isUserConnectedInitially.observe(this) {
            it?.let {
                if (it) {
                    viewModel.reloadFirebaseUser()
                } else {
                    navigateToAuth()
                }
            }
        }

        viewModel.reloadFirebaseUserStatus.observe(this) {
            it?.let {
                if (it) {
                    navigateToHome()
                } else {
                    Toast.makeText(
                        this,
                        "Error with credential - logging out now",
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.logoutUser()
                }
            }
        }
        viewModel.logoutUserStatus.observe(this) {
            it?.let {
                if (it) {
                    navigateToAuth()
                } else {
                    Toast.makeText(
                        this,
                        "Error logging you out - please restart the app",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun navigateToHome() {
        navController.navigate(R.id.homeActivity)
        finish()
        navController.popBackStack()
    }

    private fun navigateToAuth() {
        navController.navigate(R.id.authFragment)
        navController.popBackStack()
    }

}