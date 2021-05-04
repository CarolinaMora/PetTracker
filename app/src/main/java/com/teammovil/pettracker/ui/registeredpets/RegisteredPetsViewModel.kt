package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val rescuer = withContext(Dispatchers.IO) {rescuerRepository.getRescuer()}
            if(rescuer!=null) {
                val result = withContext(Dispatchers.IO) {petRepository.getAllPatsFromRescuer(rescuer.email)}
                setView(result)
            }
        }
    }

    private fun setView(petsList: List<Pet>) {
        _model.value = UiModel.PetsContent(petsList)
    }

    override fun onCleared() {
        super.onCleared()
    }

}