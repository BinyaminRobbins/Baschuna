package com.syntapps.bashcuna.ui.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R

class AuthActivity : AppCompatActivity() {
    private var keepSplashOnScreen = true
    private val delay = 500L

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition { keepSplashOnScreen }
        Handler(Looper.getMainLooper()).postDelayed({ keepSplashOnScreen = false }, delay)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val navController: NavController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.navigate(R.id.loginFragment)


    }
}