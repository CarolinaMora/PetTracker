package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.pet.PetRepository
import kotlinx.coroutines.Dispatchers
import com.teammovil.pettracker.ui.common.Event
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterPetsViewModel(val petRepository: PetRepository, val adopterRepository: AdopterRepository) : ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class AdopterPetsContent(val pets: List<com.teammovil.domain.Pet>) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel> get() = _model

    private val _navigation = MutableLiveData<Event<String>>()
    val navigation: LiveData<Event<String>> get() = _navigation

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

    private fun setView(petsList: List<com.teammovil.domain.Pet>){
        _model.value = UiModel.AdopterPetsContent(petsList)
    }


    fun onDetailPet(pet: com.teammovil.domain.Pet) {
        _navigation.value = Event(pet.id)
    }


}