package com.example.gardenguru.ui.timetable

import android.animation.ArgbEvaluator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gardenguru.R
import com.example.gardenguru.databinding.TimetableFragmentBinding
import com.example.gardenguru.ui.customview.CalendarView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class TimetableFragment : Fragment() {

    private lateinit var binding: TimetableFragmentBinding
    private var viewModel = TimetableViewModel()//: TimetableViewModel by viewModels()
    private lateinit var calendarRecyclerAdapter: CalendarRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = TimetableFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAddButton()
        initCalendar()
    }

    private fun initCalendar() {
        with(binding){
            calendarRecyclerAdapter = CalendarRecyclerAdapter(viewModel, requireContext().resources.displayMetrics.widthPixels / 7)

            calendarView.setCalendarAdapter(calendarRecyclerAdapter)
            val nameMonthArray = resources.getStringArray(R.array.calendar_month)

            calendarView.setSelectedCallback(object : CalendarView.CalendarItemSelectedCallback{
                override fun call(calendar: Calendar) {
                    tvMonthName.text = nameMonthArray[calendar[Calendar.MONTH]]
                }
            })
        }
    }

    private fun initAddButton() {
        with(binding) {
            requireContext().resources.displayMetrics.widthPixels;
            val expandedBtnWidth = (requireContext().resources.displayMetrics.widthPixels
                    - 2 * resources.getDimension(R.dimen.xm_indent) - ivPlus.layoutParams.width / 2.0).toInt()

            ivPlus.setOnClickListener {
                if (ivCam.alpha == 1f) {
                    etInputPlantName.animate().alpha(0.0f).apply {
                        duration = 200
                    }.start()

                    ivCam.animate().alpha(0f).apply {
                        duration = 300
                    }.start()

                    val widthAnimator = ValueAnimator.ofObject(IntEvaluator(), expandedBtnWidth, 0).apply {
                        duration = 300
                        startDelay = 500
                        addUpdateListener { animator ->
                            expandedBtnBackground.layoutParams = expandedBtnBackground.layoutParams.apply {
                                width = animator.animatedValue as Int
                            }
                        }
                    }
                    widthAnimator.start()

                    ivPlus.animate().rotation(0.0f).duration = 800

                    val colorAnimation =
                        ValueAnimator.ofObject(
                            ArgbEvaluator(),
                            resources.getColor(R.color.blue, null),
                            resources.getColor(R.color.primary_green, null)
                        ).apply {
                            duration = 300L
                            startDelay = 500L
                            addUpdateListener { animator -> ivPlus.setBackgroundColor(animator.animatedValue as Int) }
                        }
                    colorAnimation.start()
                } else {
                    etInputPlantName.animate().alpha(1f).apply {
                        duration = 300
                        startDelay = 500
                    }.start()

                    ivCam.animate().alpha(1f).apply {
                        duration = 300
                        startDelay = 500
                    }.start()

                    val widthAnimator = ValueAnimator.ofObject(IntEvaluator(), 0, expandedBtnWidth).apply {
                        duration = 500
                        addUpdateListener { animator ->
                            expandedBtnBackground.layoutParams = expandedBtnBackground.layoutParams.apply {
                                width = animator.animatedValue as Int
                            }
                        }
                    }
                    widthAnimator.start()

                    ivPlus.animate().rotation(405.0f).duration = 500

                    val colorAnimation =
                        ValueAnimator.ofObject(
                            ArgbEvaluator(),
                            resources.getColor(R.color.primary_green, null),
                            resources.getColor(R.color.blue, null)
                        ).apply {
                            duration = 500L
                            startDelay = 300L
                            addUpdateListener { animator -> ivPlus.setBackgroundColor(animator.animatedValue as Int) }
                        }
                    colorAnimation.start()
                }
            }
        }
    }
}
