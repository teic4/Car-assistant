package com.example.carassistant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carassistant.databinding.ActivityMainBinding
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_CarAssistant)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.selectCarFragment, R.id.confirmCarFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }

                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }
            }
        }

        val navigation = SlidingRootNavBuilder(this)
            .withMenuLayout(R.layout.menu_left_drawer)
            .withToolbarMenuToggle(binding.toolbar)
            .withMenuOpened(false)
            .withRootViewScale(0.75f)
            .withRootViewElevation(30)
            .inject()


        val tvNotifications = findViewById<TextView>(R.id.tv_notifications)
        tvNotifications.setOnClickListener {
            navController.navigate(R.id.action_global_notificationSettings)
            navigation.closeMenu(true)
        }

    }
}