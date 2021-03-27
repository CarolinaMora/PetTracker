package com.teammovil.pettracker.ui.dewormings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.teammovil.pettracker.databinding.FragmentDewormingBinding
import com.teammovil.pettracker.databinding.FragmentVaccineBinding
import com.teammovil.pettracker.ui.views.DatePickerFragment


private const val ARG_DEWORMING = "param_deworming"

class DewormingFragment : DialogFragment(), DatePickerFragment.DatePickerFragmentListener {
    private lateinit var binding: FragmentDewormingBinding
    private var deworming: DewormingView? = null
    var listener: DewormingFragmentListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            deworming = it.getParcelable(ARG_DEWORMING)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDewormingBinding.inflate(inflater)
        setView()
        setListeners()
        return binding.root
    }

    private fun setView (){
        deworming?.let{
            with(binding){
                dewormingName.setText(it.name)
                dewormingApplicationDate.setText(it.applicationDate)
            }
        }
    }

    private fun setListeners(){
        with(binding){
            dewormingSaveAction.setOnClickListener { onClickSave() }
            dewormingApplicationDate.setOnClickListener{ onClickDate()}
        }
    }

    private fun onClickSave(){
        val dewormingName = binding.dewormingName.text.toString()
        val dewormingDate = binding.dewormingApplicationDate.text.toString()
        val id = deworming?.let{ it.id } ?: 0
        val deworming = DewormingView(id=id, name = dewormingName, applicationDate = dewormingDate)
        listener?.saveDeworming(deworming)
        this.dismiss()
    }

    private fun onClickDate(){
        val newFragment = DatePickerFragment()
        newFragment.listener = this
        newFragment.show(childFragmentManager, "datePicker")
    }

    override fun saveDate(date: String) {
        binding.dewormingApplicationDate.setText(date)
    }

    companion object {
        @JvmStatic
        fun newInstance(deworming: DewormingView) =
            DewormingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_DEWORMING, deworming)
                }
            }
    }

    interface DewormingFragmentListener {
        fun saveDeworming(deworming: DewormingView)
    }
}