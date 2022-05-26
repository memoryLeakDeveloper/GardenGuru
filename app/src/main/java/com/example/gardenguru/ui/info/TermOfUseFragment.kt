package com.example.gardenguru.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.gardenguru.R
import com.example.gardenguru.databinding.FragmentTermOfUseBinding

class TermOfUseFragment : Fragment() {

    private lateinit var binding: FragmentTermOfUseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentTermOfUseBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.header.title.text = getString(R.string.terms_of_use)
        binding.header.back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

}