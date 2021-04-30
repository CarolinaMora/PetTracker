package com.teammovil.pettracker.ui.assigningadoptertopet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.domain.Adopter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterViewModel(val adopterRepository: AdopterRepository,val petRepository: PetRepository):ViewModel() {

    sealed class UiModel{
        object Loading: UiModel()
        class AdoptersContent(val adopter: List<Adopter>) :UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    init {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            var result = withContext(Dispatchers.IO){adopterRepository.getAllAdopters()}
            setView(result)
        }
    }

    private fun setView(adopterList: List<Adopter>){
        _model.value = UiModel.AdoptersContent(adopterList)
    }

    override fun onCleared() {
        super.onCleared()
    }

}