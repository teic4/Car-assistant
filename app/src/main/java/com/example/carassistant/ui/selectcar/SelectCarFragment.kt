package com.example.carassistant.ui.selectcar

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.carassistant.R
import com.example.carassistant.databinding.FragmentSelectCarBinding
import com.example.carassistant.util.exhaustive
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SelectCarFragment : Fragment(R.layout.fragment_select_car) {

    private var _binding: FragmentSelectCarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SelectCarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSelectCarBinding.bind(view)

        viewModel.getAllCarCompanies()

        binding.apply {
            spinnerCarCompany.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.getAllCarModels(binding.spinnerCarCompany.selectedItem.toString())
                        binding.spinnerModel.isEnabled =
                            binding.spinnerCarCompany.selectedItem.toString() != "Car Company"
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

            btnConfirmCar.setOnClickListener {
                viewModel.onSaveCarClick(binding.spinnerModel.selectedItem.toString())
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.carCompaniesStateFlow.collect {
                val spinnerAdapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    mutableListOf("Car Company")
                )
                spinnerAdapter.addAll(it)
                binding.spinnerCarCompany.adapter = spinnerAdapter
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.selectCarEvents.collect { event ->
                when(event) {
                    is SelectCarViewModel.SelectCarEvents.ShowMessage -> {
                        Snackbar.make(binding.root, event.message, Snackbar.LENGTH_SHORT).show()
                    }
                    is SelectCarViewModel.SelectCarEvents.ConfirmCar -> {
                        val action =
                            SelectCarFragmentDirections.actionSelectCarFragmentToConfirmCarFragment(event.car)
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }

        viewModel.carNamesLiveData.observe(viewLifecycleOwner) {
            Log.d("TAG", "$it")
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mutableListOf("Car")
            )
            adapter.addAll(it)
            binding.spinnerModel.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}