package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.rescuer.RescuerRepository
import com.teammovil.pettracker.domain.Pet
import com.teammovil.pettracker.ui.common.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisteredPetsViewModel(val petRepository: PetRepository, val rescuerRepository: RescuerRepository) : ViewModel() {

    sealed class UiModel {
        object Loading: UiModel()
        class PetsContent(val pets: List<Pet>) : UiModel()
    }

    sealed class UiEvents {
        class GoToDetail (val petId: String): UiEvents()
        object GoTORegistration: UiEvents()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    private val _events = MutableLiveData<Event<UiEvents>>()
    val events: LiveData<Event<UiEvents>> get() = _events

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

    fun onDetailPet (pet: Pet){
        _events.value = Event(UiEvents.GoToDetail(pet.id))
    }

    fun onRegisterPet (){
        _events.value = Event(UiEvents.GoTORegistration)
    }

    private fun setView(petsList: List<Pet>) {
        _model.value = UiModel.PetsContent(petsList)
    }

}