package com.teammovil.pettracker.ui.sendevidence


import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.R
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.usecases.SaveEvidenceUseCase
import com.teammovil.domain.Result
import com.teammovil.domain.Error
import com.teammovil.usecases.common.UseCaseErrors
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class SendEvidenceViewModel(
    var saveEvidenceUseCase: SaveEvidenceUseCase
    ): ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class EvidenceError(val evidenceView: EvidenceView) : UiModel()
        class SuccessNotification(@StringRes val messageResourceId: Int) : UiModel()
        class ErrorNotification(@StringRes val messageResourceId: Int) : UiModel()
    }

    private val _model = MutableLiveData<SendEvidenceViewModel.UiModel>()
    val model: LiveData<SendEvidenceViewModel.UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<Unit>>()
    val navigation: LiveData<Event<Unit>> get() = _navigation

    fun onSaveEvidence(petId: String?, evidence: EvidenceView) {
       saveEvidence(petId, evidence)
    }

    fun onClickOkAdvice (){
        _navigation.value = Event(Unit)
    }

    private fun saveEvidence(petId:String?, evidence: EvidenceView) {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            val result = withContext(Dispatchers.IO) {
                saveEvidenceUseCase.invoke(petId,
                    Mapper.map (evidence))
            }
            manageResult(result, evidence)

        }
    }

    private fun manageResult(result: Result<Unit, List<Error>>, evidence: EvidenceView) {
        if (result.valid)
            showSuccessAdvice()
        else {
            when {
                result.error.isNullOrEmpty() -> {}
                result.error!![0].code == UseCaseErrors.SEND_EVIDENCE_GENERIC_ERROR -> { showEvidenceError() }
                else -> { showEvidenceViewError( result.error!!, evidence) }
            }

        }
    }

    private fun showEvidenceViewError(errorList: List<Error>, evidence: EvidenceView) {
        _model.value = UiModel.EvidenceError(Mapper.map (evidence, errorList))
    }


    private fun showSuccessAdvice (){
        _model.value = UiModel.SuccessNotification(R.string.send_evidence_ok )
    }

    private fun showEvidenceError (){
        _model.value = UiModel.ErrorNotification(R.string.send_evidence_error)
    }
}