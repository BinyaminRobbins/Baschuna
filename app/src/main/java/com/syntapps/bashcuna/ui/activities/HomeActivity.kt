package com.syntapps.bashcuna.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.syntapps.bashcuna.R

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        Navigation.findNavController(this, R.id.nav_host_fragment_home).also {
            it.navigate(R.id.chartsFragment)
            it.popBackStack()
        }
    }
}