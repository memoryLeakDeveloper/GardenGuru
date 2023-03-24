package com.entexy.gardenguru.ui.fragments.add_plant.client

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.data.enums.Seasons
import com.entexy.gardenguru.data.plant.PlantData
import com.entexy.gardenguru.databinding.AddPestsCardBinding
import com.entexy.gardenguru.databinding.FragmentClientPlantBinding
import com.entexy.gardenguru.ui.fragments.add_plant.AddingPlantFragment
import com.entexy.gardenguru.ui.fragments.add_plant.GetPlantInfo
import com.entexy.gardenguru.utils.toGone
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class ClientPlantFragment() : BaseFragment<FragmentClientPlantBinding>(), GetPlantInfo {

    private val viewModel: ClientPlantViewModel by viewModels()
    var imageView: ImageView? = null

    private val getPestImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val imageUri = result.data!!.data ?: return@registerForActivityResult

                    if (imageView != null)
                        Glide.with(requireContext()).load(imageUri).centerCrop().into(imageView!!)
                    imageView = null
                }
            }
        }

    private val getPlantImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data != null) {
                    val imageUri = result.data!!.data ?: return@registerForActivityResult

                    Glide.with(requireContext()).load(imageUri).circleCrop().into(binding.plantPhoto)

                    CoroutineScope(Dispatchers.IO).launch {
                        val image = viewModel.uploadImage(imageUri, "plant", requireContext())

                        if (image == null) {
                            launch(Dispatchers.Main) {
                                binding.plantPhoto.setImageResource(R.drawable.plant_placeholder)
                                Toast.makeText(requireContext(), R.string.error_when_upload_image, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
        }


    private val touchListener = View.OnTouchListener { v, event ->
        v.onTouchEvent(event)
        binding.root.requestFocus()
        hideKeyboard(binding.etPlantName)
        return@OnTouchListener true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setListeners()

        initImage()
    }

    private fun initImage() {
        with(binding) {
            addImage.setOnClickListener {
                val intent =
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )

                getPlantImageResult.launch(intent)
            }
        }
    }

    private fun initView() {
        with(binding) {
            val listCareDifficult = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_difficult)))
            spinnerCare.initView(getString(R.string.define_care_difficult), listCareDifficult, false)
            val listCareType = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_type)))
            spinnerWatering.apply {
                initView(getString(R.string.choose), listCareType, false)
                setValueListener { position, name ->
                    updateForm(position)
//                    callback.update()
                }
            }
            temperatureCardSummer.initView(Seasons.Summer)
            temperatureCardWinter.initView(Seasons.Winter)
            spinnerPests.initView(
                getString(R.string.choose_pests),
                arrayListOf("EFKO", "NATASHA", "COCA-COLA", "333333")
            )
            calendarWinter.initView(Seasons.Winter)
            calendarWinter.setValueListener {
                Log.d("bugger", it.toString())
            }
            calendarSummer.initView(Seasons.Summer)
            calendarSummer.setValueListener {
                Log.d("bugger", it.toString())
            }
        }
    }

    private fun setListeners() {
        with(binding) {
            root.setOnTouchListener(touchListener)
            spinnerWatering.setOnTouchListener(touchListener)
            spinnerPests.setOnTouchListener(touchListener)
            spinnerCare.setOnTouchListener(touchListener)
            arrowDown.setOnClickListener {
                shortForm.toGone()
                fullForm.visibility = View.VISIBLE
//                callback.update()
            }
            arrowUp.setOnClickListener {
                shortForm.visibility = View.VISIBLE
                fullForm.visibility = View.GONE
//                callback.update()
            }
            textAddPests.setOnClickListener {
                cardsPests.addView(initPestCard())
//                callback.update()
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
                    plantingCard.setValueListener {
                        Log.d("bugger", it)
                    }
                }
                7 -> {
                    plantingCard.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarWinter.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    calendarSummer.let { if (it.visibility != View.GONE) it.visibility = View.GONE }
                    temperatureCardSummer.visibility = View.VISIBLE
                    temperatureCardSummer.setValueListener {
                        Log.d("bugger", "$it")
                    }
                    temperatureCardWinter.visibility = View.VISIBLE
                    temperatureCardWinter.setValueListener {
                        Log.d("bugger", "$it")
                    }
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
                LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply {
                    topMargin = (15F * requireContext().resources.displayMetrics.density).toInt()
                }
            buttonDelete.setOnClickListener {
                binding.cardsPests.removeView(root)
//                callback.update()
            }
            buttonAddPhoto.setOnClickListener {
                imageView = imagePest

                val intent =
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                getPestImageResult.launch(intent)
            }
        }
        return bindingCard.root
    }

    private fun hideKeyboard(editText: EditText) {
        val imm = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    override fun getPlantInfo(): PlantData? {
        return if (!binding.etPlantName.text.isNullOrBlank() && viewModel.plantImage != null) {
            PlantData("", name = binding.etPlantName.text.toString(), photo = viewModel.plantImage!!)
        } else {
            Toast.makeText(requireContext(), R.string.error_garden_not_selected, Toast.LENGTH_SHORT).show()
            null
        }
    }

}