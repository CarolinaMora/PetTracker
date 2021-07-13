package com.teammovil.pettracker.ui.dewormings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.teammovil.pettracker.databinding.FragmentListPlusButtonBinding
import com.teammovil.pettracker.ui.views.BasicOptionsSwipeHelper

class DewormingsListFragment: Fragment(), DewormingAdapter.OnClickListenerItem,
    BasicOptionsSwipeHelper.BasicOptionsSwipeHelperListener,
    DewormingFragment.DewormingFragmentListener
{

    private lateinit var binding: FragmentListPlusButtonBinding

    private var dewormingAdapter: DewormingAdapter? = null
    private var optionsSwipeHelper: BasicOptionsSwipeHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListPlusButtonBinding.inflate(inflater)

        binding.itemAdd.setOnClickListener{
            onClickAddDeworming()
        }

        dewormingAdapter = DewormingAdapter(this)
        binding.itemsList.adapter = dewormingAdapter
        optionsSwipeHelper = BasicOptionsSwipeHelper(this, requireContext(), binding.itemsList)

        return binding.root
    }

    private fun onClickAddDeworming (){
        dewormingAdapter?.let{
            val deworming = DewormingView(it.itemCount, "", "", "")
            showDialog(deworming)
        }
    }

    override fun onClickItem(item: DewormingView, position: Int) {
        //showDialog(item)
    }

    override fun onClickOption(option: BasicOptionsSwipeHelper.OptionType, position: Int) {
        when(option){
            BasicOptionsSwipeHelper.OptionType.DELETE -> {dewormingAdapter?.deleteItem(position)}
            BasicOptionsSwipeHelper.OptionType.UPDATE -> {dewormingAdapter?.let{showDialog(it.items[position])}}
        }
    }

    override fun saveDeworming(deworming: DewormingView) {
        dewormingAdapter?.updateAdd(deworming)
    }

    private fun showDialog (item: DewormingView){
        val dewormingDialogFragment = DewormingFragment.newInstance(item)
        dewormingDialogFragment.listener = this
        dewormingDialogFragment.show(childFragmentManager, "DewormingFragment")
    }

    fun setDewormingsList (items: List<DewormingView>){
        dewormingAdapter?.items = items.toMutableList()
    }

    fun getDewormingsList (): List<DewormingView>{
        dewormingAdapter?.let{
            return it.items
        }
            ?: return listOf()
    }
}