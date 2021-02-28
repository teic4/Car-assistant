package com.example.carassistant.ui.selectcar.confirmcar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.carassistant.R
import com.example.carassistant.databinding.FragmentConfirmCarBinding
import com.example.carassistant.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class ConfirmCarFragment : Fragment(R.layout.fragment_confirm_car) {

    private var _binding: FragmentConfirmCarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ConfirmCarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentConfirmCarBinding.bind(view)

        binding.apply {
            viewModel.car.apply {
                tvModel.text = nazivModela
                tvYear.text = godina
                tvEngine.text = motor
                tvEnginePower.text = snagaMotora
                tvEngineCapacity.text = litri
            }

            btnConfirm.setOnClickListener {
                viewModel.onConfirmClicked()
            }

            btnGoBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.confirmCarEvents.collect { event ->
                when (event) {
                    ConfirmCarViewModel.ConfirmCarEvents.CarConfirmed -> {
                        val action =
                            ConfirmCarFragmentDirections.actionConfirmCarFragmentToSetCarInfoFragment()
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