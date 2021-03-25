package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentRegisteredPetsBinding
import com.teammovil.pettracker.domain.getPets


/**
 * A simple [Fragment] subclass.
 * Use the [RegisterPetsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterPetsFragment : Fragment() {

    lateinit var binding: FragmentRegisteredPetsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRegisteredPetsBinding.inflate(inflater, container, false)
        binding.registeredPetsRecycler.adapter =RegisteredPetsAdapter(getPets())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


}