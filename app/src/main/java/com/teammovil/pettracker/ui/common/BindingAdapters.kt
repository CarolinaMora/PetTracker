package com.teammovil.pettracker.ui.common

import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("showError")
fun <T>EditText.bindError (fieldView: FieldView<T>?){
    fieldView?.let{ field ->
        error = if(field.valid) null
                else context.getString(field.messageResourceId)
    }
}

@BindingAdapter("showError")
fun <T>TextView.bindError (fieldView: FieldView<T>?){
    fieldView?.let{ field ->
        text = if (field.valid) ""
               else context.getString(field.messageResourceId)
    }
}

@BindingAdapter("url")
fun ImageView.bindUrl (url: String?){
    url?.let{
        Glide
            .with(context)
            .load(it)
            .into(this)
    }
}

@BindingAdapter("showSelection")
fun <T>Spinner.bindSelection (fieldView: SelectFieldView<T>?){
    fieldView?.let{
        setSelection(it.selection)
    }
}