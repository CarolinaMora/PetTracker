package com.teammovil.pettracker.ui.registeredpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.pettracker.ui.common.ScopedViewModel
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisteredPetsViewModel @Inject constructor(private val getRescuerPets: GetRescuerPets, uiDispatcher: CoroutineDispatcher) : ScopedViewModel(uiDispatcher) {

    sealed class UiModel {
        object Loading : UiModel()
        data class PetsContent(val pets: List<com.teammovil.domain.Pet>) : UiModel()
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

    fun onStartView() {

        launch {
            _model.value = UiModel.Loading
            val result = getRescuerPets.invoke()
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