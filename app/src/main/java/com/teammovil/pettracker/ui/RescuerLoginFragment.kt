package com.teammovil.pettracker.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.teammovil.pettracker.R
import com.teammovil.pettracker.data.database.dataaccess.RescuerStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.services.RescuerExternalDataAccessServiceImpl
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RescuerLoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RescuerLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var buttonAccess = view.findViewById<Button>(R.id.rescuer_login_Btn)

        buttonAccess.setOnClickListener {
            val repository = RescuerRepository(RescuerExternalDataAccessServiceImpl(), RescuerStorageDataAccessDataBaseImpl(requireContext()))
            viewLifecycleOwner.lifecycleScope.launch {
                val user = ""
                val password = ""
                val data = withContext(Dispatchers.IO) { repository.login(user, password) }
                if (data){
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rescuer_login, container, false)
    }


}