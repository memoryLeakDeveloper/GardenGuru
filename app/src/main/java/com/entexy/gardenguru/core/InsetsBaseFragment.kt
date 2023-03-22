package com.entexy.gardenguru.core

import android.graphics.Rect
import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewbinding.ViewBinding
import com.entexy.gardenguru.utils.bugger

abstract class InsetsBaseFragment<VB : ViewBinding>() : BaseFragment<VB>() {

    private var customInsets: Rect? = null
    private var baseInsets: Rect = Rect(0, 0, 0, 0)

    fun setBaseInsets(insets: Rect) {
        this.baseInsets = insets
    }

    fun getBaseInsets() = baseInsets

    open fun setCustomInsets(insets: Rect) {
        customInsets = insets
    }

    fun getInsets() = customInsets ?: baseInsets

    open fun updateInsets(topView: View) {
        ViewCompat.setOnApplyWindowInsetsListener(topView) { view, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            // Apply the insets as a margin to the view. Here the system is setting
            // only the bottom, left, and right dimensions, but apply whichever insets are
            // appropriate to your layout. You can also update the view padding
            // if that's more appropriate.
            bugger("bottom = ${insets.bottom}")
            view.updatePadding(
                0, 0, 0, insets.bottom
            )

            // Return CONSUMED if you don't want want the window insets to keep being
            // passed down to descendant views.
            WindowInsetsCompat.CONSUMED
        }
    }

    open fun getViewInsets() = requireView()
}