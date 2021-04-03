package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentAdopterRegistrationBinding


class AdopterRegistrationFragment : Fragment(R.layout.fragment_adopter_registration) {

    private lateinit var binding: FragmentAdopterRegistrationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAdopterRegistrationBinding.bind(view)

        //binding.tvTest.text = "Test from Fragment"
        binding.btnRegister.setOnClickListener {
            Toast.makeText(activity, "Registro exitoso", Toast.LENGTH_SHORT).show()
        }

    }
}