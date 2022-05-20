package com.example.gardenguru.ui.add.plant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gardenguru.R
import com.example.gardenguru.databinding.AddingPlantFragmentBinding
import com.example.gardenguru.ui.onboarding.OnboardingStepFragment
import com.example.gardenguru.utils.Extensions.setString

class AddingPlantFragment : Fragment() {

    private lateinit var binding: AddingPlantFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = AddingPlantFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.setString(R.string.adding)
        binding.spinner.initView("DDDDDDDDDDDDDDDDDDDD", arrayListOf("11111111111", "2222222", "2222222", "2222222", "2222222"), true)
    }


    private inner class PagerAdapter(fragment: Fragment, private val itemsCount: Int) : FragmentStateAdapter(fragment) {

        override fun getItemCount() = itemsCount

        override fun createFragment(position: Int) =
            when (position) {
                0 -> OnboardingStepFragment(0)
                1 -> OnboardingStepFragment(1)
                2 -> OnboardingStepFragment(2)
                else -> OnboardingStepFragment(0)
            }
    }

}