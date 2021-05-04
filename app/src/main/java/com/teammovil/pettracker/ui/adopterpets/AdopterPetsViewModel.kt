package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.adopter.AdopterRepository
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val adopter = withContext(Dispatchers.IO){
                adopterRepository.getAdopter()
            }
            val result = withContext(Dispatchers.IO){
                petRepository.getAllPetsFromAdopter(adopter.email)
            }
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