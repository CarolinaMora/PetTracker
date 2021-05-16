package com.teammovil.pettracker.ui.adopterpets

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teammovil.pettracker.ui.common.Event
import com.teammovil.usecases.adopterPets.GetAdopterPetsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AdopterPetsViewModel(private val getAdopterPetsUseCase: GetAdopterPetsUseCase) : ViewModel() {

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
            val result = withContext(Dispatchers.IO){
                getAdopterPetsUseCase.invoke()
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