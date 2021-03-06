package com.teammovil.pettracker.ui.petdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammovil.pettracker.databinding.ViewVaccineItemBinding
import java.text.SimpleDateFormat
import java.util.*

class VaccinesAdapter(private val vaccines: List<com.teammovil.domain.Vaccine>) : RecyclerView.Adapter<VaccinesAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewVaccineItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(vaccines[position])
    }

    override fun getItemCount()  = vaccines.size

    class ViewHolder(private val binding: ViewVaccineItemBinding) :
           RecyclerView.ViewHolder(binding.root) {

        fun bind(vaccine: com.teammovil.domain.Vaccine) {
            binding.txtVaccineName.text = vaccine.name

            var formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            binding.txtVaccineDate.text = formatter.format(vaccine.applicationDate)
        }

    }
}