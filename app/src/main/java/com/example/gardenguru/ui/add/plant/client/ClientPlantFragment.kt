package com.example.gardenguru.ui.add.plant.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.databinding.ClientPlantFragmentBinding
import com.example.gardenguru.ui.add.plant.AddingPlantFragment

class ClientPlantFragment(private val clickCallback: AddingPlantFragment.ClickCallback) : Fragment() {

    lateinit var binding: ClientPlantFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = ClientPlantFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.arrowDown.setOnClickListener {
            binding.ll.visibility = View.GONE
            binding.ll2.visibility = View.VISIBLE
            clickCallback.click()
        }
        binding.arrowUp.setOnClickListener {
            binding.ll.visibility = View.VISIBLE
            binding.ll2.visibility = View.GONE
            clickCallback.click()
        }
        binding.spinnerCare.initView(getString(R.string.define_care_difficult), arrayListOf("1", "2", "3", "4", "5"), false)
        binding.spinnerWatering.initView(getString(R.string.watering), arrayListOf("0000000", "2adsda", "3asdsa", "4", "5sadsadas"), false)
        binding.spinnerPests.initView(getString(R.string.choose_pests), arrayListOf("EFKO", "NATASHA", "COCA-COLA", "333333"), false)
    }
}