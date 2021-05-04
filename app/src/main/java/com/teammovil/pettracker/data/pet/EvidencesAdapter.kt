package com.teammovil.pettracker.data.pet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teammovil.pettracker.databinding.ViewEvidenceItemBinding
import com.teammovil.domain.Evidence
import java.text.SimpleDateFormat
import java.util.*

/*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.ViewVaccineItemBinding
import com.teammovil.domain.Vaccine
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
 */

class EvidencesAdapter(private val evidences: List<com.teammovil.domain.Evidence>) :
    RecyclerView.Adapter<EvidencesAdapter.ViewHolder> () {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewEvidenceItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(evidences[position])
    }

    override fun getItemCount() = evidences.size

    class ViewHolder(private val binding: ViewEvidenceItemBinding ):
        RecyclerView.ViewHolder (binding.root){

        fun bind (evidence: com.teammovil.domain.Evidence) {
            var formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            binding.txtEvidenceDate.text = formatter.format(evidence.date)
            binding.txtEvidenceDescription.text = evidence.comment
            Glide
                .with(binding.root.context)
                .load(evidence.media)
                .into(binding.imgEvidencePhoto)
        }
    }
}