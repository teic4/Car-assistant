package com.example.carassistant.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.carassistant.R
import com.example.carassistant.data.room.Repository
import com.example.carassistant.notifications.broadcastreceivers.WinterChangeTiresBroadcastReceiver
import com.example.carassistant.databinding.FragmentNotificationsBinding
import com.example.carassistant.notifications.broadcastreceivers.SummerChangeTiresBroadcastReceiver
import com.example.carassistant.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@RequiresApi(Build.VERSION_CODES.N)
@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)

        viewModel.checkCarSelected()

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notificationEvents.collect { event ->
                when (event) {
                    NotificationsViewModel.NotificationEvents.NavigateToSelectCarFragment -> {
                        findNavController().navigate(R.id.action_global_selectCarFragment)
                    }
                }.exhaustive
            }
        }
    }


    fun setUpChangeTiresNotification() {
        val alarmManager = requireContext().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
        val intentWinter = Intent(requireContext(), WinterChangeTiresBroadcastReceiver::class.java)
        val pendingIntentWinter = PendingIntent.getBroadcast(requireContext(), 14, intentWinter, 0)
        viewModel.setWinterChangeTiresAlarm(alarmManager, pendingIntentWinter)

        val intentSummer = Intent(requireContext(), SummerChangeTiresBroadcastReceiver::class.java)
        val pendingIntentSummer = PendingIntent.getBroadcast(requireContext(), 10, intentSummer, 0)
        viewModel.setSummerChangeTiresAlarm(alarmManager, pendingIntentSummer)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}