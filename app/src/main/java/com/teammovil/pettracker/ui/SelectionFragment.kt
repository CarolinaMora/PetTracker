package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.findNavController
import com.teammovil.pettracker.R



class SelectionFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_selection, container, false)

        val adoptedButton = view.findViewById<AppCompatButton>(R.id.adoptante_Btn)
        adoptedButton.setOnClickListener{
            view?.findNavController()
                    ?.navigate(R.id.action_selectionFragment_to_adopterLoginFragment)
        }

        val rescatistaButton = view.findViewById<AppCompatButton>(R.id.rescatista_Btn)
        rescatistaButton.setOnClickListener{
            view?.findNavController()
                    ?.navigate(R.id.action_selectionFragment_to_rescuerLoginFragment)
        }
        return view

    }


}