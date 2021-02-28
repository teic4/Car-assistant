package com.example.carassistant.ui.findmechanic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carassistant.data.entities.Mechanic
import com.example.carassistant.databinding.MechanicItemBinding

class MechanicsAdapter : RecyclerView.Adapter<MechanicsAdapter.MechanicsViewHolder>() {

    var mechanics = mutableListOf<Mechanic>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MechanicsViewHolder {
        val binding = MechanicItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MechanicsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MechanicsViewHolder, position: Int) {
        val mechanic = mechanics[position]
        holder.bind(mechanic)
    }

    override fun getItemCount() = mechanics.size

    inner class MechanicsViewHolder(val binding: MechanicItemBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(mechanic: Mechanic) {
            binding.apply {
                mechanic.apply {
                    tvMechanicName.text = name
                    tvAddress.text = address
                    tvRating.text = rating.toString()
                }
            }

        }

        override fun onClick(v: View?) {
            //onClick listener (odvedi usera na lokaciju na mapi)
        }

    }
}