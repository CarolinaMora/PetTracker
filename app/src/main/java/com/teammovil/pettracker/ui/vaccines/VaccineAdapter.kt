package com.teammovil.pettracker.ui.vaccines

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.SimpleHorizontalItemViewBinding

class VaccineAdapter(
    val listener: OnClickListenerItem?
):  RecyclerView.Adapter<VaccineAdapter.VaccineViewHolder>(){

    var items: MutableList<VaccineView> = ArrayList()
                set(value){
                    field = value
                    notifyDataSetChanged()
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VaccineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_horizontal_item_view, parent, false)
        return VaccineViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: VaccineViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    fun deleteItem (position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateAdd (item: VaccineView){
        val vaccine = items.find { it.id == item.id }
        if(vaccine!=null){
            val index = items.indexOf(vaccine)
            items.set(index, item)
        }
        else{
            items.add(item)
        }
        notifyDataSetChanged()
    }

    inner class VaccineViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = SimpleHorizontalItemViewBinding.bind(view)

        fun bind(item: VaccineView, position: Int){
            item.id = position
            with(binding){
                itemText1.text = item.name
                itemText2.text = item.applicationDate
                root.setOnClickListener{
                    listener?.onClickItem(item, position)
                }
            }
        }
    }

    interface OnClickListenerItem {
        fun onClickItem(item: VaccineView, position: Int)
    }
}