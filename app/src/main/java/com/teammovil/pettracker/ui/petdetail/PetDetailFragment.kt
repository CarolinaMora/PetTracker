    package com.teammovil.pettracker.ui.petdetail

    import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.fragment.app.Fragment
    import androidx.fragment.app.viewModels
    import androidx.lifecycle.Observer
    import androidx.navigation.findNavController
    import com.bumptech.glide.Glide
    import com.teammovil.pettracker.databinding.FragmentPetDetailBinding
    import dagger.hilt.android.AndroidEntryPoint

    const val ARG_PET_ID= "petId"

    @AndroidEntryPoint
    class PetDetailFragment: Fragment() {
        private var petId: String? = null
        private lateinit var binding: FragmentPetDetailBinding

        //di
        val viewModel: PetDetailViewModel by viewModels()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                petId = it.getString(ARG_PET_ID)
            }

        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {


            binding = FragmentPetDetailBinding.inflate (inflater)

            viewModel.model.observe(viewLifecycleOwner, Observer {
                updateUi(it)
            })

            petId?.let{
                viewModel.onGetPetDetail(it)
            }

            setListeners()

            return binding.root
        }

        private fun setListeners(){
            binding.btEdit.setOnClickListener {
                val action = PetDetailFragmentDirections.actionPetDetailFragmentToPetRegistrationFragment(petId)
                view?.findNavController()?.navigate(action)
            }
        }

        private fun updateUi (uiModel: PetDetailViewModel.UiModel) {
            //Activa / desactiva progress bar
            binding.prgProgress.visibility = if (uiModel is PetDetailViewModel.UiModel.Loading)  View.VISIBLE else View.GONE

            when (uiModel) {
                is PetDetailViewModel.UiModel.PetDetailContent -> setView(uiModel.pet)
            }

        }

        private fun setView(pet: com.teammovil.domain.Pet) {
            with(binding){

                txtName.text = pet.name
                txtGender.text= pet.gender.name
                txtRace.text = pet.race
                txtDesc.text = pet.description
                txtBirth.text = pet.approximateDateOfBirth.toString()
                txtRescue.text = pet.rescueDate.toString()
                txtType.text = pet.petType.name
                txtSterilized.text = pet.sterilized.toString()
                rvwVaccine.adapter = VaccinesAdapter (pet.vaccines)
                rvwDeworming.adapter = DewormingsAdapter(pet.dewormings.map{it.applicationDate})
                txtStatus.text = pet.status.name
                rvwEvidences.adapter = EvidencesAdapter (pet.evidences)
                Glide
                    .with(binding.root.context)
                    .load(pet.mainPhoto)
                    .into(binding.imgPetPhoto)

            }

        }

    }