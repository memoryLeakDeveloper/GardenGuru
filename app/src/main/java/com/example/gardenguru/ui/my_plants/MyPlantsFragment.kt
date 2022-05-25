package com.example.gardenguru.ui.my_plants

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentMyPlantsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyPlantsFragment : Fragment() {

    private lateinit var binding: FragmentMyPlantsBinding
    private var viewModel = MyPlantsViewModel()//: MyPlantsViewModel by viewModels()  todo

    private var rvAdapter = MyPlantsRecyclerAdapter(viewModel)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMyPlantsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            header.apply {
                back.setOnClickListener{
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.my_plants)
            }

            if (viewModel.gardens.value?.isEmpty() == true){
                rvGardens.visibility = View.GONE
                noPlantsContainer.visibility = View.VISIBLE

                //search logic
            }else{
                rvGardens.visibility = View.VISIBLE
                noPlantsContainer.visibility = View.GONE

                initGardenList()
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initGardenList() {
        with(binding) {
            rvGardens.layoutManager = LinearLayoutManager(requireContext())
            rvGardens.adapter = rvAdapter
        }
    }
}
