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
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterExternalDataAccess
import com.teammovil.pettracker.data.adopter.fakes.FakeAdopterStorageDataAccess
import kotlinx.coroutines.launch


class AdopterLoginFragment : Fragment() {
    // TODO: Rename and change types of parameters



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var buttonAccess = view.findViewById<Button>(R.id.adopter_login_Btn)

        buttonAccess.setOnClickListener {
            val repository = AdopterRepository(FakeAdopterExternalDataAccess(), FakeAdopterStorageDataAccess())
            viewLifecycleOwner.lifecycleScope.launch {
                val user = ""
                val password = ""
                val result = repository.login(user, password)
                if (result)  {
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
        return inflater.inflate(R.layout.fragment_adopter_login, container, false)
    }


}


