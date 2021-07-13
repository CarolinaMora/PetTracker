package com.teammovil.pettracker.ui.dewormings

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.SimpleHorizontalItemViewBinding

class DewormingAdapter(
    val listener: OnClickListenerItem?
):  RecyclerView.Adapter<DewormingAdapter.DewormingViewHolder>(){

    var items: MutableList<DewormingView> = ArrayList()
                set(value){
                    field = value
                    notifyDataSetChanged()
                }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DewormingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.simple_horizontal_item_view, parent, false)
        return DewormingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: DewormingViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position)
    }

    fun deleteItem (position: Int){
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun updateAdd (item: DewormingView){
        val itemFound = items.find { it.id == item.id }
        if(itemFound!=null){
            val index = items.indexOf(itemFound)
            items.set(index, item)
        }
        else{
            items.add(item)
        }
        notifyDataSetChanged()
    }

    inner class DewormingViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = SimpleHorizontalItemViewBinding.bind(view)

        fun bind(item: DewormingView, position: Int){
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
        fun onClickItem(item: DewormingView, position: Int)
    }
}