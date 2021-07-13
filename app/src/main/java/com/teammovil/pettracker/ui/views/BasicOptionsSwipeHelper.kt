package com.teammovil.pettracker.ui.views

import android.content.Context
import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView

class BasicOptionsSwipeHelper(
    var listener: BasicOptionsSwipeHelperListener?,
    context: Context?,
    recyclerView: RecyclerView
): SwipeHelper(context, recyclerView) {

    override fun instantiateUnderlayButton(
        viewHolder: RecyclerView.ViewHolder?,
        underlayButtons: MutableList<UnderlayButton>?
    ) {
        val deleteButton = UnderlayButton(
            "Eliminar",
        0,
            Color.parseColor("#FF3C30"),
            object : UnderlayButtonClickListener {
                override fun onClick(pos: Int) {
                    listener?.onClickOption(OptionType.DELETE, pos)
                }
            }
        )
        underlayButtons?.add(deleteButton)

        val editButton = UnderlayButton(
            "Editar",
            0,
            Color.parseColor("#FF9502"),
            object : UnderlayButtonClickListener {
                override fun onClick(pos: Int) {
                    listener?.onClickOption(OptionType.UPDATE, pos)
                }
            }
        )
        underlayButtons?.add(editButton)
    }

    interface BasicOptionsSwipeHelperListener{
        fun onClickOption (option: OptionType, position: Int)
    }

    enum class OptionType{
        DELETE,
        UPDATE
    }
}
