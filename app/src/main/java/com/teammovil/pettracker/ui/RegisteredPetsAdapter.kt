package com.teammovil.pettracker.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.PetsItemBinding
import com.teammovil.pettracker.domain.Pet

class RegisteredPetsAdapter() : RecyclerView.Adapter<RegisteredPetsAdapter.ViewHolder>(){

    var items: List<Pet> = ArrayList()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pets_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = PetsItemBinding.bind(view)

        fun bind(pet: Pet){
            binding.petTitle.text = pet.name
            binding.petDescription.text = pet.description
            Glide.with(binding.petPicture).load(pet.mainPhoto).into(binding.petPicture)
        }
    }
}