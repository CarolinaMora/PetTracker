package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisteredPetsViewModel @Inject constructor(private val getRescuerPets: GetRescuerPets) : ViewModel() {

    sealed class UiModel {
        object Loading : UiModel()
        class PetsContent(val pets: List<com.teammovil.domain.Pet>) : UiModel()
    }

    sealed class UiEvents {
        class GoToDetail(val petId: String) : UiEvents()
        object GoTORegistration : UiEvents()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() = _model

    private val _events = MutableLiveData<Event<UiEvents>>()
    val events: LiveData<Event<UiEvents>> get() = _events

    init {

        viewModelScope.launch {
            _model.value = UiModel.Loading
            val result = withContext(Dispatchers.IO) { getRescuerPets.invoke() }
            if (result != null) {
                setView(result)
            }
        }
    }

    fun onDetailPet(pet: com.teammovil.domain.Pet) {
        _events.value = Event(UiEvents.GoToDetail(pet.id))
    }

    fun onRegisterPet() {
        _events.value = Event(UiEvents.GoTORegistration)
    }

    private fun setView(petsList: List<com.teammovil.domain.Pet>) {
        _model.value = UiModel.PetsContent(petsList)
    }

}