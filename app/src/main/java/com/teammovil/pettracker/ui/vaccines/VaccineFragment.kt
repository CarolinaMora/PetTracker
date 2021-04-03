package com.teammovil.pettracker.ui.vaccines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teammovil.pettracker.databinding.FragmentVaccineBinding
import com.teammovil.pettracker.ui.views.DatePickerFragment


private const val ARG_VACCINE = "param_vaccine"

class VaccineFragment : DialogFragment(), DatePickerFragment.DatePickerFragmentListener {
    private lateinit var binding: FragmentVaccineBinding
    private var vaccine: VaccineView? = null
    var listener: VaccineFragmentListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            vaccine = it.getParcelable(ARG_VACCINE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVaccineBinding.inflate(inflater)
        setView()
        setListeners()
        return binding.root
    }

    private fun setView (){
        vaccine?.let{
            with(binding){
                vaccineName.setText(it.name)
                vaccineApplicationDate.setText(it.applicationDate)
            }
        }
    }

    private fun setListeners(){
        with(binding){
            vaccineSaveAction.setOnClickListener { onClickSave() }
            vaccineApplicationDate.setOnClickListener{ onClickDate()}
        }
    }

    private fun onClickSave(){
        val vaccineName = binding.vaccineName.text.toString()
        val vaccineDate = binding.vaccineApplicationDate.text.toString()
        val id = vaccine?.let{ it.id } ?: 0
        val vaccine = VaccineView(id=id, name = vaccineName, applicationDate = vaccineDate)
        listener?.saveVaccine(vaccine)
        this.dismiss()
    }

    private fun onClickDate(){
        val newFragment = DatePickerFragment(binding.root.id)
        newFragment.listener = this
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun saveDate(date: String, idCaller: Int) {
        binding.vaccineApplicationDate.setText(date)
    }

    companion object {
        @JvmStatic
        fun newInstance(vaccine: VaccineView) =
            VaccineFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_VACCINE, vaccine)
                }
            }
    }

    interface VaccineFragmentListener {
        fun saveVaccine(vaccine: VaccineView)
    }
}