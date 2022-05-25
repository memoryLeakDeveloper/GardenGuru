package com.example.gardenguru.ui.timetable

import android.animation.ArgbEvaluator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentTimetableBinding
import com.example.gardenguru.ui.customview.CalendarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TimetableFragment : Fragment() {

    private lateinit var binding: FragmentTimetableBinding
    private var viewModel = TimetableViewModel()//: TimetableViewModel by viewModels()  todo
    private lateinit var calendarRecyclerAdapter: CalendarRecyclerAdapter
    private lateinit var eventsRecyclerAdapter: TimetableRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivLeaf.setOnClickListener{
            findNavController().navigate(R.id.action_timetableFragment_to_myPlantsFragment)
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
            ivCam.setOnClickListener{
                findNavController().navigate(R.id.addingPlantFragment)
            }

            requireContext().resources.displayMetrics.widthPixels
            val expandedBtnWidth = (requireContext().resources.displayMetrics.widthPixels
                    - 2 * resources.getDimension(R.dimen.xm_indent) - ivPlus.layoutParams.width / 2.0).toInt()

            ivPlus.setOnClickListener {
                if (ivCam.alpha == 1f) {
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
