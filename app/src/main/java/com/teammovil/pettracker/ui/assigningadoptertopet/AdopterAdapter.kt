package com.teammovil.pettracker.ui.assigningadoptertopet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.ViewAdopterItemBinding
import com.teammovil.domain.Adopter

class AdopterAdapter(val listener: ((item: com.teammovil.domain.Adopter) -> Unit)? = null) :
    RecyclerView.Adapter<AdopterAdapter.ViewHolder>() {

    var items : List<com.teammovil.domain.Adopter> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_adopter_item,parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ViewAdopterItemBinding.bind(view)

        fun bind(adopter: com.teammovil.domain.Adopter) {
            with(binding){
                actvName.text = adopter.name
                actvFistLastName.text = adopter.firstLastName
                actvSecondLastName.text = adopter.secondLastName
                actvAdopterMail.text = adopter.email
                actvAdopterPhone.text = adopter.phone
                actvAdopterAddress.text = adopter.address

                root.setOnClickListener { listener?.invoke(adopter) }
            }

        }
    }

}