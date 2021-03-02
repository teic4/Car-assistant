package com.example.carassistant.ui.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.carassistant.R
import com.example.carassistant.databinding.AlertDialogAddServiceBinding
import com.example.carassistant.notifications.broadcastreceivers.WinterChangeTiresBroadcastReceiver
import com.example.carassistant.databinding.FragmentNotificationsBinding
import com.example.carassistant.notifications.broadcastreceivers.SummerChangeTiresBroadcastReceiver
import com.example.carassistant.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: NotificationsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNotificationsBinding.bind(view)

        viewModel.checkCarSelected()

        setUpChangeTiresNotification()

        viewModel.refuelingLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvLastRefuel.text = it.date
            }
        }

        viewModel.serviceLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvServiceLast.text = it.date
            }
        }

        viewModel.bigServiceLiveData.observe(viewLifecycleOwner) {
            it?.let {
                binding.tvServiceBig.text = it.date
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.notificationEvents.collect { event ->
                when (event) {
                    is NotificationsViewModel.NotificationEvents.NavigateToSelectCarFragment -> {
                        findNavController().navigate(R.id.action_global_selectCarFragment)
                    }
                    is NotificationsViewModel.NotificationEvents.ShowMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                }.exhaustive
            }
        }

        binding.btnUpdateActivities.setOnClickListener {
            val layoutInflater = LayoutInflater.from(requireContext())
            val alertDialogAddServiceBinding = AlertDialogAddServiceBinding.inflate(layoutInflater)

            val alertDialog = AlertDialog.Builder(requireContext())
                .setView(alertDialogAddServiceBinding.root)
                .setPositiveButton("DONE") { _, _ ->
                    alertDialogAddServiceBinding.apply {
                        val serviceType = spinnerServiceType.selectedItem.toString()
                        val description = etDescription.text.toString()
                        val price = etPrice.text.toString().toFloatOrNull()
                        val date = "${etDay.text}.${etMonth.text}.${etYear.text}."
                        viewModel.onSaveServiceClick(serviceType, description, price, date)
                    }
                }
                .create()

            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        }


    }


    fun setUpChangeTiresNotification() {
        val alarmManager =
            requireContext().getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
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