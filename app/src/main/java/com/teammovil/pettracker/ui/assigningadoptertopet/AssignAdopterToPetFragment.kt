package com.teammovil.pettracker.ui.assigningadoptertopet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentAssignAdopterToPetBinding

class AssignAdopterToPetFragment : Fragment(R.layout.fragment_assign_adopter_to_pet) {

    private lateinit var binding: FragmentAssignAdopterToPetBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAssignAdopterToPetBinding.bind(view)
    }

}