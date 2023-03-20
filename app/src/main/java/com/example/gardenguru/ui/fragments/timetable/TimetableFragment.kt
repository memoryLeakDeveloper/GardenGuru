package com.example.gardenguru.ui.fragments.timetable

import android.animation.ArgbEvaluator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.core.BaseFragment
import com.example.gardenguru.databinding.FragmentTimetableBinding
import com.example.gardenguru.ui.customview.CalendarView
import com.example.gardenguru.utils.checkAndVerifyCameraPermissions
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class TimetableFragment : BaseFragment<FragmentTimetableBinding>() {

    private val viewModel: TimetableViewModel by viewModels()
    private lateinit var calendarRecyclerAdapter: CalendarRecyclerAdapter
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
        }


        initAddButton()
        initCalendar()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initCalendar() {
        with(binding) {
            eventsRecyclerAdapter = TimetableRecyclerAdapter(viewModel)
            rvEvents.layoutManager = LinearLayoutManager(requireContext())
            rvEvents.adapter = eventsRecyclerAdapter
            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvEvents)
            rvEvents.scrollToPosition(3 + 7)

            calendarRecyclerAdapter =
                CalendarRecyclerAdapter(viewModel, requireContext().resources.displayMetrics.widthPixels / 7)

            calendarView.setCalendarAdapter(calendarRecyclerAdapter)
            val nameMonthArray = resources.getStringArray(R.array.calendar_month)

            var touchedView: View? = null
            var lastPositionRvEvents = 0

            rvEvents.setOnTouchListener { _, motionEvent ->
                touchedView = rvEvents
                return@setOnTouchListener rvEvents.onTouchEvent(motionEvent)
            }
            calendarView.setOnTouchListener { _, motionEvent ->
                touchedView = calendarView
                return@setOnTouchListener calendarView.onTouchEvent(motionEvent)
            }

            rvEvents.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (touchedView == rvEvents) {
                            val position =
                                (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                            if (position - lastPositionRvEvents > 0) {
                                calendarView.smoothScrollToPosition(position + 4)
                            } else {
                                calendarView.smoothScrollToPosition(position - 2)
                            }
                            lastPositionRvEvents = position

                        }
                    }

                }
            })

            calendarView.setSelectedCallback(object : CalendarView.CalendarItemSelectedCallback {
                override fun call(calendar: Calendar, position: Int) {
                    if (touchedView == calendarView) {
                        rvEvents.smoothScrollToPosition(position + 4)
                        tvMonthName.text = nameMonthArray[calendar[Calendar.MONTH]]
                    }
                }
            })
        }
    }

    private fun initAddButton() {
        with(binding) {
            ivCam.setOnClickListener {
                if (requireActivity().checkAndVerifyCameraPermissions() && requireActivity().checkAndVerifyCameraPermissions()) {
                    findNavController().navigate(R.id.action_timetableFragment_to_cameraFragment)
                }
            }

            requireContext().resources.displayMetrics.widthPixels
            val expandedBtnWidth = (requireContext().resources.displayMetrics.widthPixels
                    - 2 * resources.getDimension(R.dimen.xm_indent) - ivPlus.layoutParams.width / 2.0).toInt()

            ivCam.isEnabled = false
            ivPlus.setOnClickListener {
                if (ivCam.alpha == 1f) {
                    ivCam.isEnabled = false
                    etInputPlantName.animate().alpha(0.0f).apply {
                        duration = 80
                    }.start()

                    ivCam.animate().alpha(0f).apply {
                        duration = 80
                    }.start()

                    ValueAnimator.ofObject(IntEvaluator(), expandedBtnWidth, 0).apply {
                        duration = 200
                        startDelay = 100
                        addUpdateListener { animator ->
                            expandedBtnBackground.layoutParams = expandedBtnBackground.layoutParams.apply {
                                width = animator.animatedValue as Int
                            }
                        }
                    }.start()

                    ivPlus.animate().rotation(0.0f).duration = 300

                    ValueAnimator.ofObject(
                        ArgbEvaluator(),
                        resources.getColor(R.color.blue, null),
                        resources.getColor(R.color.primary_green, null)
                    ).apply {
                        duration = 300L
                        addUpdateListener { animator -> ivPlus.setBackgroundColor(animator.animatedValue as Int) }
                    }.start()
                } else {
                    ivCam.isEnabled = true
                    etInputPlantName.animate().alpha(1f).apply {
                        duration = 100
                        startDelay = 200
                    }.start()

                    ivCam.animate().alpha(1f).apply {
                        duration = 100
                        startDelay = 200
                    }.start()

                    ValueAnimator.ofObject(IntEvaluator(), 0, expandedBtnWidth).apply {
                        duration = 200
                        addUpdateListener { animator ->
                            expandedBtnBackground.layoutParams = expandedBtnBackground.layoutParams.apply {
                                width = animator.animatedValue as Int
                            }
                        }
                    }.start()

                    ivPlus.animate().rotation(225.0f).duration = 300

                    ValueAnimator.ofObject(
                        ArgbEvaluator(),
                        resources.getColor(R.color.primary_green, null),
                        resources.getColor(R.color.blue, null)
                    ).apply {
                        duration = 300L
                        addUpdateListener { animator -> ivPlus.setBackgroundColor(animator.animatedValue as Int) }
                    }.start()
                }
            }
        }
    }
}
