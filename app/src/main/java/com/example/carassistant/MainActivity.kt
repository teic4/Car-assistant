package com.example.carassistant

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.carassistant.databinding.ActivityMainBinding
import com.example.carassistant.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

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

        val tvJoinCommunity = findViewById<TextView>(R.id.tv_join_community)

        viewModel.carLiveData.observe(this) { car->
            tvJoinCommunity.setOnClickListener {
                Log.d("TAG", "aghdf")
                viewModel.onJoinCommunityClick(car)
            }
        }

        val tvAboutUs = findViewById<TextView>(R.id.tv_about_us)
        tvAboutUs.setOnClickListener {
            Snackbar.make(binding.root, "Our app is still in alpha version...", Snackbar.LENGTH_SHORT).show()
        }

        val tvNightMode = findViewById<TextView>(R.id.tv_night_mode)
        tvNightMode.setOnClickListener {
            Snackbar.make(binding.root, "Our app is still in alpha version...", Snackbar.LENGTH_SHORT).show()
        }


        lifecycleScope.launchWhenStarted {
            viewModel.mainActivityEvents.collectLatest { event ->
                when(event) {
                    is MainActivityViewModel.MainActivityEvents.JoinCommunity -> {
                        Log.d("TAG", "${event.url}")
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(event.url)))
                    }
                }.exhaustive


            }
        }

    }
}