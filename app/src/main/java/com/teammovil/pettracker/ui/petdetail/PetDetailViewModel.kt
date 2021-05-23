package com.teammovil.pettracker.ui.petdetail


import androidx.lifecycle.*
import com.teammovil.usecases.petdetail.GetPetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PetDetailViewModel @Inject
   constructor(var getPetUseCase: GetPetUseCase): ViewModel() {

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
                getPetUseCase.invoke(petId)
            }
            if(resultPet!=null)
                _model.value = UiModel.PetDetailContent(resultPet)
        }
    }

}