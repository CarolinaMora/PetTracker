package com.teammovil.pettracker.ui.petdetail

import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.teammovil.pettracker.data.pet.DewormingsAdapter
import com.teammovil.pettracker.data.pet.EvidencesAdapter
import com.teammovil.pettracker.data.pet.PetRepository
import com.teammovil.pettracker.data.pet.VaccinesAdapter
import com.teammovil.pettracker.data.pet.fakes.PetFakeExternalDataAccess
import com.teammovil.pettracker.domain.Pet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PetDetailViewModel (var petRepository: PetRepository): ViewModel() {

    sealed class UiModel (){
        object Loading : UiModel()
        class PetDetailContent(val pet: Pet) : UiModel()
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

            _model.value = UiModel.PetDetailContent(resultPet)
        }
    }

}