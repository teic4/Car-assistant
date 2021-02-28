package com.example.carassistant.ui.mycar

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carassistant.R
import com.example.carassistant.databinding.FragmentMyCarBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCarFragment : Fragment(R.layout.fragment_my_car) {

    private var _binding: FragmentMyCarBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MyCarViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMyCarBinding.bind(view)

        viewModel.car.observe(viewLifecycleOwner) {
            binding.apply {
                it.apply {
                    tvBrand.text = brand
                    tvEngine.text = motor
                    tvModel.text = nazivModela
                    tvPower.text = snagaMotora
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}