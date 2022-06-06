package com.example.gardenguru.ui.plant_card.info

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.sun.relation.SunRelationData
import com.example.gardenguru.databinding.DialogPlantMovingBinding
import com.example.gardenguru.databinding.FragmentPlantCardInfoBinding
import com.example.gardenguru.ui.customview.DialogHelper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PlantCardInfoFragment : Fragment() {

    private var isDescriptionShowed: Boolean = false
    private val dialog = DialogHelper()
    private val bindingDialog by lazy { DialogPlantMovingBinding.inflate(LayoutInflater.from(requireContext())) }
    val data = PlantData(
        "0",
        0,
        "Иван",
        "КактусКактусКактусКактусКактус.КактусКактусКактусКактусКактусКактусКактусКактусКактусКактус",

        PhotoData(
            "0",
            "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png",
            "https://flowers.evroopt.by/wp-content/uploads/2019/03/kaktus4_800h800_fon.png"
        ),
        SunRelationData(0, ""),
        arrayListOf(),
        arrayListOf(),
        arrayListOf(),
        "",
        "",
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0,
        0
    )

    private lateinit var binding: FragmentPlantCardInfoBinding
    private var viewModel = PlantCardInfoViewModel()//: PlantCardViewModel by viewModels()  todo

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentPlantCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniView()
    }

    private fun iniView() {
        with(binding) {
            Glide.with(requireContext())
                .load(data.photo.file)
                .circleCrop()
                .placeholder(ContextCompat.getDrawable(requireContext(), R.drawable.plant_placeholder))
                .into(plantPhoto)
            plantName.text = data.name
            plantName1.text = data.name
            plantInfo.movementMethod = LinkMovementMethod.getInstance()
            plantInfo.text = getSpannableNextString()
            careDifficult.initView(data.careComplexity, true)
            wheather.initView(data)
            careDescription.initView(data)
            pests.initView(data)
            benefits.initView(data)
            buttonMove.setOnClickListener {
//                bindingDialog.spinner.initView("Введите сад", null, arrayListOf("11111111111", "2222222", "33333", "444444", "5555555"), true)
                dialog.showDialog(bindingDialog.root)
            }
        }
    }

    private fun showPlantDescription() {
        isDescriptionShowed = true
        binding.plantInfo.text = getSpannableHideString()
    }

    private fun hidePlantDescription() {
        isDescriptionShowed = false
        binding.plantInfo.text = getSpannableNextString()
    }

    private fun getSpannableNextString(): SpannableString {
        val text = data.description.substringBefore(".")
        val span = SpannableString(text + ". " + getString(R.string.next_dots))
        span.setSpan(
            getClickableSpan(),
            0,
            text.length + getString(R.string.next_dots).length + 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_green)),
            text.length + 2,
            text.length + getString(R.string.next_dots).length + 2,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

    private fun getSpannableHideString(): SpannableString {
        val text = data.description
        val span = SpannableString(text + " " + getString(R.string.hide))
        span.setSpan(
            getClickableSpan(),
            0,
            text.length + getString(R.string.hide).length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        span.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_green)),
            text.length + 1,
            text.length + getString(R.string.hide).length + 1,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return span
    }

    private fun getClickableSpan() = object : ClickableSpan() {
        override fun updateDrawState(ds: TextPaint) {
            ds.isUnderlineText = false
        }

        override fun onClick(widget: View) {
            if (isDescriptionShowed) hidePlantDescription() else showPlantDescription()
        }
    }
}
