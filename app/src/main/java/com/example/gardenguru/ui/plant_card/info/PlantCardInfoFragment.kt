package com.example.gardenguru.ui.plant_card.info

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.databinding.DialogPlantMovingBinding
import com.example.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class PlantCardInfoFragment : Fragment() {

    private lateinit var binding: FragmentPlantCardInfoBinding
    private val viewModel: PlantCardInfoViewModel by viewModels()
    private val bindingDialog by lazy { DialogPlantMovingBinding.inflate(LayoutInflater.from(requireContext())) }
    private val dialog by lazy { DialogHelper() }
    private var isDescriptionShowed: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlantCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idPlant = requireArguments().getString("PLANT_ID")
        lifecycleScope.launch(Dispatchers.IO) {
            val data = viewModel.fetchPlant(idPlant!!)
            withContext(Dispatchers.Main) {
                iniView(data)
            }
        }
    }

    private fun iniView(data: PlantData?) {
        Log.d("bugger", data.toString())
        if (data == null) return
        with(binding) {
            Glide.with(requireContext())
                .load(data.photo.file)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .into(plantPhoto)
            plantName.text = data.name

            data.description?.let {
                plantInfo.text = getSpannableNextString(it)
                plantInfo.movementMethod = LinkMovementMethod.getInstance()
            } ?: run {
                plantInfo.visibility = View.GONE
                aboutPlant.visibility = View.GONE
                plantName1.visibility = View.GONE
            }
            careDifficult.initView(data.careComplexity, true)
            wheather.initView(data)
            careDescription.initView(data)
            pests.initView(data.pests)
            benefits.initView(data.benefits)
            buttonMove.setOnClickListener {
                bindingDialog.spinner.initView(
                    "Введите сад",
                    null,
                    arrayListOf("11111111111", "2222222", "33333", "444444", "5555555"),
                    true
                )
                dialog.showDialog(bindingDialog.root)
            }
        }
    }

    private fun showPlantDescription(description: String) {
        isDescriptionShowed = true
        binding.plantInfo.text = getSpannableHideString(description)
    }

    private fun hidePlantDescription(description: String) {
        isDescriptionShowed = false
        binding.plantInfo.text = getSpannableNextString(description)
    }

    private fun getSpannableNextString(description: String): SpannableString {
        val text = description.substringBefore(".")
        return SpannableString(text + ". " + getString(R.string.next_dots)).apply {
            setSpan(
                getClickableSpan(description),
                0,
                text.length + getString(R.string.next_dots).length + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_green)),
                text.length + 2,
                text.length + getString(R.string.next_dots).length + 2,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun getSpannableHideString(description: String): SpannableString {
        val text = description.substringBefore(".")
        return SpannableString(text + " " + getString(R.string.hide)).apply {
            setSpan(getClickableSpan(description), 0, text.length + getString(R.string.hide).length + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(
                ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_green)),
                text.length + 1,
                text.length + getString(R.string.hide).length + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun getClickableSpan(description: String) = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            if (isDescriptionShowed) hidePlantDescription(description) else showPlantDescription(description)
        }
    }
}
