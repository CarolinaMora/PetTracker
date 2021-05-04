package com.teammovil.pettracker.ui.sendevidence

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.R
import com.teammovil.data.pet.PetRepository
import com.teammovil.pettracker.getDateFromString
import com.teammovil.pettracker.ui.common.Event
import kotlinx.coroutines.launch

class SendEvidenceViewModel(var petRepository: PetRepository): ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class EvidenceError(val evidenceView: EvidenceView) : UiModel()
        class SuccessAdvice(val message: String) : UiModel()
        class ErrorAdvice(val message: String) : UiModel()
    }

    private val _model = MutableLiveData<SendEvidenceViewModel.UiModel>()
    val model: LiveData<SendEvidenceViewModel.UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onSaveEvidence(petId: String?, evidence: EvidenceView) {
        if (validateView(evidence)) {
            if (petId != null)
                saveEvidence(petId, mapEvidence(evidence))
        } else {
            _model.value = SendEvidenceViewModel.UiModel.EvidenceError(evidence)
        }
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun validateView(evidence: EvidenceView): Boolean {
        var valid = true
        if (evidence.photo.value.isNullOrEmpty()) {
            valid = false
            evidence.photo.valid = false
            evidence.photo.messageResourceId = R.string.error_photo_required
        }
        if (evidence.evidenceDate.value == null || getDateFromString(evidence.evidenceDate.value) == null) {
            valid = false
            evidence.evidenceDate.valid = false
            evidence.evidenceDate.messageResourceId = R.string.error_field_required
        }
        if (evidence.comments.value.isNullOrEmpty()) {
            valid = false
            evidence.comments.valid = false
            evidence.comments.messageResourceId = R.string.error_field_required
        }

        return valid
    }

    private fun saveEvidence(petId:String, evidence: com.teammovil.domain.Evidence) {
        viewModelScope.launch {
            _model.value = SendEvidenceViewModel.UiModel.Loading
            val result = petRepository.saveEvidence(petId, evidence)
            if (result) showSuccessAdvice()
            else showRegistrationError()
        }
    }


    private fun mapEvidence (origin: EvidenceView): com.teammovil.domain.Evidence {
        return com.teammovil.domain.Evidence(
            origin.externalId,
            origin.comments.value,
            origin.photo.value!!,
            getDateFromString(origin.evidenceDate.value)!!
        )
    }

    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessAdvice("Evidencia enviada correctamente")
    }

    private fun showRegistrationError (){
        _model.value = UiModel.ErrorAdvice("Hubo un error al enviar evidenca. Intente más tarde.")
    }
}