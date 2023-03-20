package com.entexy.gardenguru.ui.fragments.my_plants

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.entexy.gardenguru.R
import com.entexy.gardenguru.core.BaseFragment
import com.entexy.gardenguru.databinding.FragmentMyPlantsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlantsFragment : BaseFragment<FragmentMyPlantsBinding>() {

    private val viewModel: MyPlantsViewModel by viewModels()

    private lateinit var rvAdapter: GardensRecyclerAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            header.apply {
                back.setOnClickListener {
                    requireActivity().onBackPressed()
                }
                title.setText(R.string.my_plants)
            }

            initGardenList()
            viewModel.gardens.observe(viewLifecycleOwner) {
                if (it != null) {
                    binding.progressBar.visibility = View.GONE
                    if (viewModel.gardens.value?.isEmpty() == true) {
                        rvGardens.visibility = View.GONE
                        noPlantsContainer.visibility = View.VISIBLE
                        //search logic
                    } else {
                        rvAdapter.notifyDataSetChanged()
                        rvGardens.visibility = View.VISIBLE
                        noPlantsContainer.visibility = View.GONE
                    }
                } else {
                    binding.progressBar.visibility = View.VISIBLE
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
