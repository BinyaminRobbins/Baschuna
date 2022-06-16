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
        viewModel.getIsUserLoggedIn().observe(this) {
            if (it) { //user is logged in
                navigateToHome()
            } else if (!it) { //user not logged in (we use "!it" b/c answer can be null)
                if (navController.currentDestination != navController.findDestination(R.id.loginFragment)) {
                    navigateToLogin()
                }
            }
        }
        trackErrorMsg()
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_auth)

        viewModel.checkIfUserLoggedIn()
    }

    private fun navigateToHome() {
        navController.navigate(R.id.homeActivity)
        finish()
        navController.popBackStack()
    }

    private fun navigateToLogin() {
        navController.navigate(R.id.loginFragment)
    }

    private fun trackErrorMsg() {
        viewModel.getErrorMsg()?.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}