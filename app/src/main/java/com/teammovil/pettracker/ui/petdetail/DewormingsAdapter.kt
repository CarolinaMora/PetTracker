package com.teammovil.pettracker.ui.petdetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammovil.pettracker.databinding.ViewDewormingItemBinding
import java.text.SimpleDateFormat

import java.util.*

class DewormingsAdapter(private val dewormings: List<Date> ) :
        RecyclerView.Adapter <DewormingsAdapter.ViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewDewormingItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dewormings[position])
    }

    override fun getItemCount() = dewormings.size

    class ViewHolder(private val binding: ViewDewormingItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind (dewormingDate: Date) {
            var formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            binding.txtDewormingDate.text = formatter.format(dewormingDate)

        }
    }
}
