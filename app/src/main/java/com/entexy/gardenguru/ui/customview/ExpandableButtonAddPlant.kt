package com.entexy.gardenguru.ui.customview

import android.animation.ArgbEvaluator
import android.animation.IntEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.widget.ConstraintLayout
import com.entexy.gardenguru.R
import com.entexy.gardenguru.databinding.ButtonAddPlantBinding

class ExpandableButtonAddPlant(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private var viewBinding = ButtonAddPlantBinding.inflate(LayoutInflater.from(context), this)

    fun initView(camClickListener: () -> Unit, plantNameListener: (plantName: String) -> Unit) {
        with(viewBinding) {
            ivCam.setOnClickListener { camClickListener() }
            etInputPlantName.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    plantNameListener(etInputPlantName.text.toString())
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
        setAddButtonAnimation()
    }

    private fun setAddButtonAnimation() {
        with(viewBinding) {
            val expandedBtnWidth = (context.resources.displayMetrics.widthPixels
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