package com.example.gardenguru.ui.add_plant.client

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.gardenguru.R
import com.example.gardenguru.data.enums.Seasons
import com.example.gardenguru.data.media.PhotoData
import com.example.gardenguru.data.plant.PlantData
import com.example.gardenguru.data.plant.cloud.create.CreatePlantCloudObj
import com.example.gardenguru.databinding.AddPestsCardBinding
import com.example.gardenguru.databinding.FragmentClientPlantBinding
import com.example.gardenguru.ui.add_plant.AddingPlantFragment
import com.example.gardenguru.ui.add_plant.GetPlantInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("ClickableViewAccessibility")
@AndroidEntryPoint
class ClientPlantFragment(private val callback: AddingPlantFragment.UpdateLayoutHeightCallback) : Fragment(), GetPlantInfo {

    lateinit var binding: FragmentClientPlantBinding

    private lateinit var viewModel: ClientPlantViewModel

    @Inject
    lateinit var viewModelFactory: ClientPlantViewModel.Factory

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

                        if(image == null){
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
    var imageView: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[ClientPlantViewModel::class.java]
        binding = FragmentClientPlantBinding.inflate(inflater, container, false)
        return binding.root
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
            spinnerCare.initView(getString(R.string.define_care_difficult), null, listCareDifficult, false)
            val listCareType = ArrayList<String>(listOf(*resources.getStringArray(R.array.care_type)))
            spinnerWatering.apply {
                initView(getString(R.string.choose), null, listCareType, false)
                setValueListener { position, name ->
                    updateForm(position)
                    callback.update()
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
                shortForm.visibility = View.GONE
                fullForm.visibility = View.VISIBLE
                callback.update()
            }
            arrowUp.setOnClickListener {
                shortForm.visibility = View.VISIBLE
                fullForm.visibility = View.GONE
                callback.update()
            }
            textAddPests.setOnClickListener {
                cardsPests.addView(initPestCard())
                callback.update()
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
                callback.update()
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
        return if (!binding.etPlantName.text.isNullOrBlank() && viewModel.plantImage != null){
            PlantData("", name = binding.etPlantName.text.toString(), photo = viewModel.plantImage!!)
        }else null
    }

}