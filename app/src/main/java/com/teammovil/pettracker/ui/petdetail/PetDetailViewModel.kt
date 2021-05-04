package com.teammovil.pettracker.ui.petdetail

import androidx.lifecycle.*
import com.teammovil.data.pet.PetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PetDetailViewModel (var petRepository: PetRepository): ViewModel() {

    sealed class UiModel (){
        object Loading : UiModel()
        class PetDetailContent(val pet: com.teammovil.domain.Pet) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            return _model
        }

    fun onGetPetDetail (petId: String)
    {

        viewModelScope.launch{

            _model.value = UiModel.Loading

            var resultPet = withContext( Dispatchers.IO ) {
                petRepository.getPetById(petId)
            }
            if(resultPet!=null)
                _model.value = UiModel.PetDetailContent(resultPet)
        }
    }

}