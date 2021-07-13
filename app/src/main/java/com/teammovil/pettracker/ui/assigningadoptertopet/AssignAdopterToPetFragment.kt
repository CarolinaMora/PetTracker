package com.teammovil.pettracker.ui.assigningadoptertopet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.pettracker.databinding.FragmentAssignAdopterToPetBinding
import dagger.hilt.android.AndroidEntryPoint

const val ARG_PET_ID= "petId"

@AndroidEntryPoint
class AssignAdopterToPetFragment : Fragment(R.layout.fragment_assign_adopter_to_pet) {

    private lateinit var binding: FragmentAssignAdopterToPetBinding
    private lateinit var adoptersAdapter: AdopterAdapter
    private lateinit var petId:String

    val viewModel : AdopterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getString(ARG_PET_ID).toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAssignAdopterToPetBinding.bind(view)

        setViews()
        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })

        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            goBackScreem()
        })

        viewModel.onStartView()
    }

    private fun setViews() {
        adoptersAdapter = AdopterAdapter{
            onClickAdopter(it)
        }
        binding.adopterRecycler.adapter = adoptersAdapter

    }

    private fun onClickAdopter(it: com.teammovil.domain.Adopter) {
        viewModel.onAdopterClicked(it)
    }

    private fun goBackScreem(){
        view?.findNavController()
            ?.navigateUp()
    }

    private fun updateUI(uiModel: AdopterViewModel.UiModel){
        binding.assignAdopterToPetProgress.visibility = if(uiModel is AdopterViewModel.UiModel.Loading) View.VISIBLE else View.GONE
        when(uiModel){
            is AdopterViewModel.UiModel.SuccessConfimation -> showConfirmation(uiModel.adopterId,uiModel.adopterName)
            is AdopterViewModel.UiModel.SuccessNotification -> showSuccessAdvice(uiModel.message)
            is AdopterViewModel.UiModel.ErrorNotification -> showErrorAdvice(uiModel.message)
            is AdopterViewModel.UiModel.AdoptersContent -> setView(uiModel.adopter)
        }
    }

    private fun setView(adopterList: List<com.teammovil.domain.Adopter>){
        adoptersAdapter.items = adopterList
    }

    private fun showConfirmation (adopterId:String, adopterName: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage("Estás seguro que quieres asignar a $adopterName a la mascota")
            .setCancelable(false)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
                viewModel.onClickConfirmation(petId,adopterId)
            }
            .setNegativeButton("Cancelar"){dialog,_->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun showSuccessAdvice (message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
                viewModel.onClickOkAdvice()
            }
        builder.create().show()
    }

    private fun showErrorAdvice (message: String){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }
}