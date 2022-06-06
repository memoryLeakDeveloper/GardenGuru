package com.example.gardenguru.ui.my_plants

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentMyPlantsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyPlantsFragment : Fragment() {

    private lateinit var binding: FragmentMyPlantsBinding
    private lateinit var viewModel: MyPlantsViewModel

    @Inject lateinit var viewModelFactory: MyPlantsViewModel.Factory

    private lateinit var rvAdapter: GardensRecyclerAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        viewModel = ViewModelProvider(this, viewModelFactory)[MyPlantsViewModel::class.java]
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

            initGardenList()
            viewModel.gardens.observe(viewLifecycleOwner){
                if (viewModel.gardens.value?.isEmpty() == true){
                    rvGardens.visibility = View.GONE
                    noPlantsContainer.visibility = View.VISIBLE
                    //search logic
                }else{
                    rvAdapter.notifyDataSetChanged()
                    rvGardens.visibility = View.VISIBLE
                    noPlantsContainer.visibility = View.GONE
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.initGardens()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initGardenList() {
        with(binding) {
            rvAdapter = GardensRecyclerAdapter(viewModel)
            rvGardens.layoutManager = LinearLayoutManager(requireContext())
            rvGardens.adapter = rvAdapter
        }
    }
}
