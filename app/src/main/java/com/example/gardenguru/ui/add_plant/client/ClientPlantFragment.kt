package com.example.gardenguru.ui.add_plant.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.enums.Seasons
import com.example.gardenguru.databinding.AddPestsCardBinding
import com.example.gardenguru.databinding.FragmentClientPlantBinding
import com.example.gardenguru.ui.add_plant.AddingPlantFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientPlantFragment(private val clickCallback: AddingPlantFragment.ClickCallback) : Fragment() {

    lateinit var binding: FragmentClientPlantBinding
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        if (imageView != null)
            Glide.with(requireContext()).load(it).centerCrop().into(imageView!!)
        imageView = null
    }
    var imageView: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentClientPlantBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()
    }

    private fun initView() {
        with(binding) {
            val listCareDifficult = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_difficult)))
            spinnerCare.initView(getString(R.string.define_care_difficult), null, listCareDifficult, false)
            val listCareType = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_type)))
            spinnerWatering.apply {
                initView(getString(R.string.choose), null, listCareType, false)
                setValueListener { position, name ->
                    updateForm(position)
                    clickCallback.click()
                }
            }
            temperatureCardSummer.initView(Seasons.Summer)
            temperatureCardWinter.initView(Seasons.Winter)
            spinnerPests.initView(getString(R.string.choose_pests), arrayListOf("EFKO", "NATASHA", "COCA-COLA", "333333"))
            calendarWinter.initView(Seasons.Winter)
            calendarSummer.initView(Seasons.Summer)
        }
    }

    private fun setListeners() {
        with(binding) {
            arrowDown.setOnClickListener {
                shortForm.visibility = View.GONE
                fullForm.visibility = View.VISIBLE
                clickCallback.click()
            }
            arrowUp.setOnClickListener {
                shortForm.visibility = View.VISIBLE
                fullForm.visibility = View.GONE
                clickCallback.click()
            }
            textAddPests.setOnClickListener {
                cardsPests.addView(initPestCard())
                clickCallback.click()
            }
            editTextBenefit.addTextChangedListener {
                editTextCount.text = "${it.toString().length}/1000"
            }
        }
    }

    private fun updateForm(position: Int) {
        with(binding) {
            when (position) {
                0, 1, 3 -> {
                    plantingCard.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    temperatureCardSummer.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    temperatureCardWinter.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarWinter.visibility = View.VISIBLE
                    calendarSummer.visibility = View.VISIBLE
                }
                2, 4, 5 -> {
                    temperatureCardSummer.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    temperatureCardWinter.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarWinter.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarSummer.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    plantingCard.visibility = View.VISIBLE
                }
                7 -> {
                    plantingCard.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarWinter.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarSummer.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    temperatureCardSummer.visibility = View.VISIBLE
                    temperatureCardWinter.visibility = View.VISIBLE
                }
                6 -> {
                    //TODO
                }
            }
        }
    }

    private fun initPestCard(): View {
        val bindingCard = AddPestsCardBinding.inflate(LayoutInflater.from(context), binding.cardsPests, false).apply {
            root.layoutParams =
                LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                    topMargin = (15F * requireContext().resources.displayMetrics.density).toInt()
                }
            buttonDelete.setOnClickListener {
                binding.cardsPests.removeView(root)
                clickCallback.click()
            }
            buttonAddPhoto.setOnClickListener {
                imageView = imagePest
                getContent.launch("image/*")
            }
        }
        return bindingCard.root
    }

}