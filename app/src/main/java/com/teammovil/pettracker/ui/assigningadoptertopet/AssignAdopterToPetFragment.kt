package com.teammovil.pettracker.ui.assigningadoptertopet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.database.dataaccess.AdopterStorageDataAccessDataBaseImpl
import com.teammovil.data.pet.PetRepository
import com.teammovil.pettracker.data.services.AdopterExternalDataAccessServiceImpl
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentAssignAdopterToPetBinding
import com.teammovil.usecases.assignadoptertopet.AssignAdopterToPetUseCase
import com.teammovil.usecases.getalladopters.GetAllAdoptersUseCase

const val ARG_PET_ID= "petId"


class AssignAdopterToPetFragment : Fragment(R.layout.fragment_assign_adopter_to_pet) {

    private lateinit var binding: FragmentAssignAdopterToPetBinding
    private lateinit var viewModel: AdopterViewModel
    private lateinit var adoptersAdapter: AdopterAdapter
    private lateinit var petId:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getString(ARG_PET_ID).toString()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAssignAdopterToPetBinding.bind(view)

        val petExternal = PetExternalDataAccessServiceImpl()
        val adopterFake = AdopterExternalDataAccessServiceImpl()
        val adopterStorage = AdopterStorageDataAccessDataBaseImpl(requireContext())

        val assignAdopterToPetUseCase = AssignAdopterToPetUseCase(PetRepository(petExternal))
        val getAllAdopterUseCase = GetAllAdoptersUseCase(AdopterRepository(
            adopterFake,
            adopterStorage
        ))

        viewModel = ViewModelProvider(this,AdopterViewModelFactory(assignAdopterToPetUseCase,getAllAdopterUseCase))[AdopterViewModel::class.java]

        setViews()
        viewModel.model.observe(viewLifecycleOwner, Observer {
            updateUI(it)
        })

        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            goBackScreem()
        })
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
        binding.progress.visibility = if(uiModel is AdopterViewModel.UiModel.Loading) View.VISIBLE else View.GONE
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
    //Crear un método que tenga cómo objetivo mandar el petId y el adopterID una ves que el usuario haya confirmado la asiganación en el viewModel se manda a llamar
    //Para hacer le Match es necesario mandar el petId y el adopterID, con eso deberia.

}