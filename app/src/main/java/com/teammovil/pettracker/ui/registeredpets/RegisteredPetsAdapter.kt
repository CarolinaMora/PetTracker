package com.teammovil.pettracker.ui.registeredpets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.PetsItemBinding
import com.teammovil.domain.Pet

class RegisteredPetsAdapter(
    val listener: ((item: com.teammovil.domain.Pet) -> Unit)? = null
) : RecyclerView.Adapter<RegisteredPetsAdapter.ViewHolder>(){

    var items : List<com.teammovil.domain.Pet> = listOf()
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

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val binding = PetsItemBinding.bind(view)

        fun bind(pet: com.teammovil.domain.Pet){
            with(binding) {
                petTitle.text = pet.name
                petDescription.text = pet.description
                Glide.with(petPicture).load(pet.mainPhoto).into(petPicture)

                root.setOnClickListener {
                    listener?.invoke(pet)
                }
            }
        }
    }
}