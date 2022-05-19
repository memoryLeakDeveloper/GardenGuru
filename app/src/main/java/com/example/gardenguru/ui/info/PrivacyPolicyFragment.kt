package com.example.gardenguru.ui.info

import android.content.Context
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gardenguru.R
import com.example.gardenguru.databinding.PrivacyPolicyFragmentBinding
import com.example.gardenguru.ui.customview.ExpandableLayout
import com.example.gardenguru.ui.customview.SpinnerAdapter

class PrivacyPolicyFragment : Fragment() {

    private lateinit var binding: PrivacyPolicyFragmentBinding
    private var popupWindow: PopupWindow? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = PrivacyPolicyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.privacy_policy)
        binding.header.title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        binding.header.back.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.spinner.apply {
            setOnClickListener {
                popupWindow?.dismiss()
                if (popupWindow == null) {
                    provideCountryPopupWindow(it)
                }
                binding.spinner.background = null
                popupWindow!!.showAsDropDown(it, 0, -it.height)
            }
        }
    }

    private fun provideCountryPopupWindow(view: View) {
        popupWindow = PopupWindow(view.width, ViewGroup.LayoutParams.WRAP_CONTENT)
            .apply {
                setBackgroundDrawable(getDrawable(requireContext(), R.drawable.spinner_background))
                isOutsideTouchable = true
                val listView = layoutInflater.inflate(R.layout.spinner_recycler, binding.root, false) as RecyclerView
                val listener = ExpandableLayout.SelectListener {
                    binding.spinnerText1.text = it
                    popupWindow?.dismiss()
                }
                listView.adapter =
                    SpinnerAdapter(listOf("111111111111", "22222222", "333333333", "4", "5", "6", "7", "8", "7777"), listener)
                listView.layoutManager = LinearLayoutManager(context)
                contentView = listView
                val dismissListener = PopupWindow.OnDismissListener {
                    binding.spinner.background = getDrawable(requireContext(), R.drawable.spinner_background)
                }
                setOnDismissListener(dismissListener)
            }
    }

    private fun convertDpToPx(context: Context, dp: Float) = (dp * context.resources.displayMetrics.density).toInt()
}