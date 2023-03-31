package com.entexy.gardenguru.ui.fragments.timetable

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.databinding.FragmentTimetableBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.utils.*
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimetableBinding>() {

    private val viewModel: TimetableViewModel by viewModels()
    private lateinit var eventsRecyclerAdapter: TimetableRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            ivLeaf.setOnClickListener {
                findNavController().navigate(R.id.action_timetableFragment_to_myPlantsFragment)
            }

            ivSettings.setOnClickListener {
                findNavController().navigate(R.id.action_timetableFragment_to_settingsFragment)
            }

            ivScrollUp.setOnClickListener {
                rvEvents.smoothScrollToPosition(10)
            }

            title.setOnClickListener {
//                App.firestoreUserRef.document(App.user.userId).collection("plants").document().set(PlantMockData.plant.mapToPlantCloud())
            }
        }

        initCalendar()
        initAddButton()

    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initCalendar() {
        with(binding) {
            eventsRecyclerAdapter = TimetableRecyclerAdapter { event ->
                lifecycleScope.launch {
                    viewModel.completeEvent(event).collect {
                        it.getResult(
                            success = {},
                            failure = {
                                requireView().showSnackBar(R.string.error_update_data)
                            },
                            loading = {}
                        )
                    }
                }
            }

            val recyclerLayoutManager = LinearLayoutManager(requireContext())
            rvEvents.layoutManager = recyclerLayoutManager
            rvEvents.adapter = eventsRecyclerAdapter
            rvEvents.scrollToPosition(3 + 7)

            rvEvents.setOnScrollChangeListener { _, _, _, _, _ ->
                if (recyclerLayoutManager.findFirstVisibleItemPosition() > 10) {
                    if (ivScrollUp.isGone()) {
                        ivScrollUp.toVisible()
                    }
                } else {
                    if (ivScrollUp.isVisible()) {
                        ivScrollUp.toGone()
                    }
                }
            }

            lifecycleScope.launch {
                viewModel.fetchEvents().collect { cloudResponse ->
                    cloudResponse.getResult(
                        success = {
                            rvEvents.hideSkeleton()
                            rvEvents.smoothScrollToPosition(10)
                            if (it.result.isEmpty()) {
                                noEventsContainer.toVisible()
                                rvEvents.toGone()
                                ivScrollUp.toGone()
                            } else {
                                noEventsContainer.toGone()
                                rvEvents.toVisible()
                                ivScrollUp.toVisible()

                                eventsRecyclerAdapter.setEvents(ArrayList(it.result))
                            }
                        },
                        failure = {
                            rvEvents.hideSkeleton()

                            requireView().showSnackBar(R.string.error_loading_data)
                        },
                        loading = {
                            rvEvents.loadSkeleton(R.layout.rv_timetable_item) {
                                itemCount(10)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun initAddButton() {
        with(binding) {
            addPlant.initView({
                if (requireActivity().checkAndVerifyCameraPermissions() && requireActivity().checkAndVerifyCameraPermissions()) {
                    findNavController().navigate(R.id.action_timetableFragment_to_cameraFragment)
                }
            }, {
                findNavController().navigate(
                    R.id.action_timetableFragment_to_addingPlantFragment,
                    bundleOf(AddingPlantFragment.SEARCH_BY_VARIETY_ARGUMENTS_KEY to it)
                )
            })
        }
    }
}
