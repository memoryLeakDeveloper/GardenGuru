package com.entexy.gardenguru.ui.fragments.timetable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentTimetableBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.utils.checkAndVerifyCameraPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimetableBinding>() {

    private val viewModel: TimetableViewModel by viewModels()
    private lateinit var eventsRecyclerAdapter: TimetableRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        initAddButton()
        initCalendar()
        updateInsets(binding.addPlant)
    }

    private fun setListeners() = binding.apply {
        ivLeaf.setOnClickListener {
            findNavController().navigate(R.id.action_timetableFragment_to_myPlantsFragment)
        }
        ivSettings.setOnClickListener {
            findNavController().navigate(R.id.action_timetableFragment_to_settingsFragment)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initCalendar() = binding.apply {
        eventsRecyclerAdapter = TimetableRecyclerAdapter(viewModel)
        rvEvents.layoutManager = LinearLayoutManager(requireContext())
        rvEvents.adapter = eventsRecyclerAdapter
        LinearSnapHelper().attachToRecyclerView(rvEvents)
        rvEvents.scrollToPosition(3 + 7)
    }

    private fun initAddButton() = binding.apply {
        addPlant.initView({
            if (requireActivity().checkAndVerifyCameraPermissions() && requireActivity().checkAndVerifyCameraPermissions()) {
                findNavController().navigate(R.id.action_timetableFragment_to_cameraFragment)
            }
        }, {
            findNavController().navigate(
                R.id.action_timetableFragment_to_addingPlantFragment,
                bundleOf(AddingPlantFragment.SEARCH_ARGUMENTS_KEY to it)
            )
        })
    }

}
