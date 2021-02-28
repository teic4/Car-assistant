package com.example.carassistant.ui.findmechanic

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carassistant.R
import com.example.carassistant.data.entities.Mechanic
import com.example.carassistant.databinding.FragmentFindMechanicBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindMechanicFragment : Fragment(R.layout.fragment_find_mechanic) {

    private var _binding: FragmentFindMechanicBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFindMechanicBinding.bind(view)

        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?
        supportMapFragment?.getMapAsync { googleMap ->
            val splitLatLng = LatLng(43.508133, 16.440193)
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(splitLatLng, 13.5f))
        }

        val bottomSheet = requireActivity().findViewById<ConstraintLayout>(R.id.bottom_sheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isDraggable = true

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                        binding.root.setOnClickListener {
                            Log.d("TAG", "clcik")
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                        }
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        val imgViewMenu = requireActivity().findViewById<ImageView>(R.id.img_view_menu)

        imgViewMenu.setOnClickListener {
            when (bottomSheetBehavior.state) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                }
            }
        }

        val mechanicsList = mutableListOf<Mechanic>()
        mechanicsList.add(Mechanic("Grand Auto Split", "Ul. kralja Stjepana Drzislava 18", 5.0f))
        mechanicsList.add(Mechanic("Grand Auto Split", "Ul. kralja Stjepana Drzislava 18", 4.9f))
        mechanicsList.add(Mechanic("Grand Auto Split", "Ul. kralja Stjepana Drzislava 18", 2.0f))
        mechanicsList.add(Mechanic("Grand Auto Split", "Ul. kralja Stjepana Drzislava 18", 4.7f))

        val recViewMechanic = requireActivity().findViewById<RecyclerView>(R.id.rec_view_mechanics)
        val mechanicsAdapter = MechanicsAdapter()

        recViewMechanic.apply {
            adapter = mechanicsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            mechanicsAdapter.mechanics = mechanicsList
            mechanicsAdapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}