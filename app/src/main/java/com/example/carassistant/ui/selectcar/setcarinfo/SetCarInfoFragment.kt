package com.example.carassistant.ui.selectcar.setcarinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.carassistant.R
import com.example.carassistant.databinding.FragmentSetCarInfoBinding
import com.example.carassistant.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class SetCarInfoFragment : Fragment(R.layout.fragment_set_car_info) {

    private var _binding: FragmentSetCarInfoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SetCarInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSetCarInfoBinding.bind(view)

        binding.btnConfirm.setOnClickListener {
            val day = binding.spinnerDay.selectedItem.toString()
            val month = binding.spinnerMonth.selectedItem.toString()
            viewModel.onConfirmClick(day, month)
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.setCarInfoEvents.collect { event ->
                when (event) {
                    SetCarInfoViewModel.SetCarInfoEvents.CarInfoSetUp -> {
                        val action =
                            SetCarInfoFragmentDirections.actionSetCarInfoFragmentToNotificationsFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}