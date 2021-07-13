package com.teammovil.pettracker.ui.vaccines

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teammovil.pettracker.databinding.FragmentListPlusButtonBinding
import com.teammovil.pettracker.ui.views.BasicOptionsSwipeHelper

class VaccinesListFragment: Fragment(), VaccineAdapter.OnClickListenerItem,
    BasicOptionsSwipeHelper.BasicOptionsSwipeHelperListener,
    VaccineFragment.VaccineFragmentListener
{

    private lateinit var binding: FragmentListPlusButtonBinding

    private var vaccineAdapter: VaccineAdapter? = null
    private var optionsSwipeHelper: BasicOptionsSwipeHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListPlusButtonBinding.inflate(inflater)

        binding.itemAdd.setOnClickListener{
            onClickAddVaccine()
        }

        vaccineAdapter = VaccineAdapter(this)
        binding.itemsList.adapter = vaccineAdapter
        optionsSwipeHelper = BasicOptionsSwipeHelper(this, requireContext(), binding.itemsList)

        return binding.root
    }

    private fun onClickAddVaccine (){
        vaccineAdapter?.let{
            val vaccine = VaccineView(it.itemCount, "", "", "")
            showDialog(vaccine)
        }
    }

    override fun onClickItem(item: VaccineView, position: Int) {
        //showDialog(item)
    }

    override fun onClickOption(option: BasicOptionsSwipeHelper.OptionType, position: Int) {
        when(option){
            BasicOptionsSwipeHelper.OptionType.DELETE -> {vaccineAdapter?.deleteItem(position)}
            BasicOptionsSwipeHelper.OptionType.UPDATE -> {vaccineAdapter?.let{showDialog(it.items[position])}}
        }
    }

    override fun saveVaccine(vaccine: VaccineView) {
        vaccineAdapter?.updateAdd(vaccine)
    }

    private fun showDialog (item: VaccineView){
        val vaccineDialogFragment = VaccineFragment.newInstance(item)
        vaccineDialogFragment.listener = this
        vaccineDialogFragment.show(childFragmentManager, "VaccineFragment")
    }

    fun setVaccinesList (items: List<VaccineView>){
        vaccineAdapter?.items = items.toMutableList()
    }

    fun getVaccinesList (): List<VaccineView>{
        vaccineAdapter?.let{
            return it.items
        }
            ?: return listOf()
    }
}