package com.teammovil.pettracker.ui.sendevidence

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.teammovil.pettracker.R
import com.teammovil.data.pet.PetRepository
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.databinding.FragmentSendEvidenceBinding
import com.teammovil.pettracker.ui.common.EventObserver
import com.teammovil.pettracker.ui.common.FieldView
import com.teammovil.pettracker.ui.common.PhotoTaker
import com.teammovil.pettracker.ui.petdetail.ARG_PET_ID
import com.teammovil.pettracker.ui.views.DatePickerFragment
import com.teammovil.usecases.SaveEvidenceUseCase


class SendEvidenceFragment : Fragment(R.layout.fragment_send_evidence),
    DatePickerFragment.DatePickerFragmentListener {

    private lateinit var binding: FragmentSendEvidenceBinding
    private lateinit var viewModel: SendEvidenceViewModel
    private var photoTaker: PhotoTaker? = null
    private var petId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            petId = it.getString(ARG_PET_ID)

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveEvidenceUseCase = SaveEvidenceUseCase(
            PetRepository(
                PetExternalDataAccessServiceImpl()
            )
        )
        binding = FragmentSendEvidenceBinding.bind(view)
        viewModel = ViewModelProvider(
            this,
            SendEvidenceViewModelFactory(
                saveEvidenceUseCase
            )
        )[SendEvidenceViewModel::class.java]

        photoTaker = PhotoTaker(requireContext())
        photoTaker?.fragment = this

        setListeners()
        setObservers()
    }

    private fun setObservers (){
        viewModel.model.observe(viewLifecycleOwner, Observer { updateUI(it) })
        viewModel.navigation.observe(viewLifecycleOwner, EventObserver{ navigateUp() })
    }

    private fun setListeners() {
        binding.sendEvidenceSendAction.setOnClickListener{
            onClickSendEvidence()
        }
        binding.sendEvidencePhotoEvidence.setOnClickListener{
            onTakePhoto()
        }
        binding.sendEvidenceDate.setOnClickListener {
            onClickDate(it.id)
        }
    }

    private fun updateUI(model: SendEvidenceViewModel.UiModel) {

        when(model){
            is SendEvidenceViewModel.UiModel.EvidenceError -> showEvidenceError(model.evidenceView)
            is SendEvidenceViewModel.UiModel.ErrorNotification -> showErrorAdvice(model.messageResourceId)
            is SendEvidenceViewModel.UiModel.SuccessNotification -> showSuccessAdvice(model.messageResourceId)
        }
    }

    override fun saveDate(date: String, idCaller: Int) {
        when (idCaller){
            binding.sendEvidenceDate.id -> binding.sendEvidenceDate.setText(date)

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        photoTaker?.onActivityResult(requestCode, resultCode, data, binding.sendEvidencePhotoEvidence)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        photoTaker?.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun onClickSendEvidence() {
        with(binding) {
            val evidence = EvidenceView (
                "",
                FieldView(photoTaker?.currentPhotoPath),
                FieldView(sendEvidenceComments.text.toString()),
                FieldView(sendEvidenceDate.text.toString())
            )
            viewModel.onSaveEvidence(petId, evidence)
        }
    }

    private fun onClickDate (idCaller: Int){
        val newFragment = DatePickerFragment(idCaller)
        newFragment.listener = this
        newFragment.show(childFragmentManager, "datePicker")
    }

    private fun onTakePhoto (){
        photoTaker?.dispatchTakePictureIntent()
    }

    private fun showEvidenceError (evidenceView: EvidenceView){
        binding.sendEvidencePhotoError.error = if (evidenceView.photo.valid) null else getString(evidenceView.photo.messageResourceId)
        binding.sendEvidenceDate.error = if (evidenceView.evidenceDate.valid) null else getString(evidenceView.evidenceDate.messageResourceId)
        binding.sendEvidenceComments.error = if (evidenceView.comments.valid) null else getString(evidenceView.comments.messageResourceId)
    }

    private fun showSuccessAdvice (@StringRes message: Int){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
                viewModel.onClickOkAdvice()
            }
        builder.create().show()
    }

    private fun showErrorAdvice (@StringRes message: Int){
        val builder = AlertDialog.Builder(requireContext())
            .setMessage(message)
            .setPositiveButton(R.string.action_accept) { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()
    }

    private fun navigateUp (){
        view?.findNavController()?.navigateUp()
    }
}