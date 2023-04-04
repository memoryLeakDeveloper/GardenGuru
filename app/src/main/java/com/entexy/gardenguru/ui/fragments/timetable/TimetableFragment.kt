package com.entexy.gardenguru.ui.fragments.timetable

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.core.exception.getResult
import com.entexy.gardenguru.databinding.DialogCameraPermissionBinding
import com.entexy.gardenguru.databinding.FragmentTimetableBinding
import com.entexy.gardenguru.ui.customview.DialogHelper
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.utils.*
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimetableBinding>() {

    private val viewModel: TimetableViewModel by viewModels()
    private lateinit var timetableRecyclerAdapter: TimetableRecyclerAdapter

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
                rvEvents.smoothScrollToPosition(7)
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
            var isCompleteProcessed = false
            timetableRecyclerAdapter = TimetableRecyclerAdapter { event, position ->
                if (isCompleteProcessed) return@TimetableRecyclerAdapter
                isCompleteProcessed = true
                val dialogHelper = DialogHelper()

                lifecycleScope.launch {
                    viewModel.completeEvent(event).collect { cloudResponse ->
                        withContext(Dispatchers.Main) {
                            cloudResponse.getResult(
                                success = {
                                    dialogHelper.hideDialog()

                                    event.isComplete = !event.isComplete

                                    viewModel.updateEvent(event.mapToEventData(), it.result.first, it.result.second)
                                    timetableRecyclerAdapter.updateEvent(event, it.result.first, position)

                                    if (event.eventTime.isDaysEquals(Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) })) {
                                        val recalculatedEvents = viewModel.recalculateEvents()
                                        timetableRecyclerAdapter.setEvents(recalculatedEvents)
                                    }
                                    isCompleteProcessed = false

                                },
                                failure = {
                                    dialogHelper.hideDialog()
                                    requireView().showSnackBar(R.string.error_update_data)
                                    isCompleteProcessed = false
                                },
                                loading = {
                                    dialogHelper.showDialog(ProgressBar(requireContext()), false)
                                }
                            )
                        }
                    }
                }
            }

            val recyclerLayoutManager = LinearLayoutManager(requireContext())
            rvEvents.layoutManager = recyclerLayoutManager
            rvEvents.adapter = timetableRecyclerAdapter

            rvEvents.setOnScrollChangeListener { _, _, _, _, _ ->
                if (recyclerLayoutManager.findFirstVisibleItemPosition() > 7) {
                    if (ivScrollUp.isGone()) {
                        ivScrollUp.toVisible()
                    }
                } else {
                    if (ivScrollUp.isVisible()) {
                        ivScrollUp.toGone()
                    }
                }
            }

            populateEvents()
        }
    }

    private fun populateEvents() = with(binding) {
        lifecycleScope.launch {
            viewModel.fetchPlantAndEvents().collect { cloudResponse ->
                cloudResponse.getResult(
                    success = {
                        rvEvents.hideSkeleton()
                        rvEvents.smoothScrollToPosition(7)
                        if (it.result.first.isEmpty()) {
                            noEventsContainer.toVisible()
                            rvEvents.toGone()
                            ivScrollUp.toGone()
                        } else {
                            noEventsContainer.toGone()
                            rvEvents.toVisible()
                            ivScrollUp.toVisible()

                            timetableRecyclerAdapter.setEvents(viewModel.calculateEvents(it.result.first, it.result.second))
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

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("qqqqq", "initAddButton addPlant camera permission granted just now")
                findNavController().navigate(R.id.action_timetableFragment_to_cameraFragment)
            } else {
                val dialogHelper = DialogHelper()
                val dialogBinding = DialogCameraPermissionBinding.inflate(LayoutInflater.from(requireContext()))
                with(dialogBinding){
                    btSettings.setOnClickListener{
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", requireContext().packageName, null)
                        intent.data = uri
                        startActivity(intent)
                        requireActivity().startActivity(intent)
                        dialogHelper.hideDialog()
                    }
                }
                dialogHelper.showDialog(dialogBinding.root)
            }
        }

    private fun initAddButton() {
        with(binding) {
            addPlant.initView({
                if (requireActivity().checkAndVerifyCameraPermissions(requestPermissionLauncher)) {
                    Log.d("qqqqq", "initAddButton addPlant camera permission granted")
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
