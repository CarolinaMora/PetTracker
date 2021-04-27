package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.launch

class AdopterPetsViewModel(val petRepository: PetRepository, val adopterRepository: AdopterRepository) : ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class AdopterPetsContent(val pets: List<Pet>) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    init {
        viewModelScope.launch {
            _model.value = UiModel.Loading
            val adopter = adopterRepository.getAdopter()
            val result = petRepository.getAllPetsFromAdopter(adopter.id)
            setView(result)

        }
    }

    private fun setView(petsList: List<Pet>){
        _model.value = UiModel.AdopterPetsContent(petsList)
    }

    override fun onCleared() {
        super.onCleared()
    }



}