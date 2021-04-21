package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.*
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeExternalDataAccess
import com.teammovil.pettracker.data.rescuer.fakes.RescuerFakeStorageDataAccess
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.launch

class RegisteredPetsViewModel(val petRepository: PetRepository, val rescuerRepository: RescuerRepository) : ViewModel() {


    sealed class UiModel {
        object Loading: UiModel()
        class PetsContent(val pets: List<Pet>) : UiModel()
        class detailNav(val petId: Int) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    init {

        viewModelScope.launch {
            _model.value = UiModel.Loading
            var rescuer = rescuerRepository.getRescuer()
            var result = petRepository.getAllPatsFromRescuer(rescuer.id)
            setView(result)
        }
    }

    private fun setView(petsList: List<Pet>) {
        _model.value = UiModel.PetsContent(petsList)
    }

    override fun onCleared() {
        super.onCleared()
    }

}