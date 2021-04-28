package com.teammovil.pettracker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.ui.petdetail.ARG_PET_ID

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScreenSelectionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScreenSelectionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_screen_selection, container, false)

        val petListButton = view.findViewById<AppCompatButton>(R.id.bt_pet_list)
        petListButton.setOnClickListener{
            view?.findNavController()
                ?.navigate(R.id.action_screenSelectionFragment_to_registerPetsFragment)
        }

        val detailButton = view.findViewById<AppCompatButton>(R.id.bt_detail_option)
        detailButton.setOnClickListener{
            //val bundle = TODO CREAR EL BUNDLE, mandar el misma contastne ARG_ID_PET = "1"
            val bundle = bundleOf(Pair(ARG_PET_ID, "1" ))
            view?.findNavController()
                ?.navigate(R.id.action_screenSelectionFragment_to_petDetailFragment, bundle)
            //, bundle dentro del navigate
        }

        val adopterRegistrationButton = view.findViewById<AppCompatButton>(R.id.bt_adopter_registration)
        adopterRegistrationButton.setOnClickListener{
            view?.findNavController()
                ?.navigate(R.id.action_screenSelectionFragment_to_adopterRegistrationFragment)
        }

        val petRegistrationButton = view.findViewById<AppCompatButton>(R.id.bt_pet_registration)
        petRegistrationButton.setOnClickListener{
            view?.findNavController()
                ?.navigate(R.id.action_screenSelectionFragment_to_petRegistrationFragment)
        }

        val assignAdopterToPetButton = view.findViewById<AppCompatButton>(R.id.btn_assign_adopter_to_pet)
        assignAdopterToPetButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_screenSelectionFragment_to_assignAdopterToPetFragment)
        }

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ScreenSelectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ScreenSelectionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}