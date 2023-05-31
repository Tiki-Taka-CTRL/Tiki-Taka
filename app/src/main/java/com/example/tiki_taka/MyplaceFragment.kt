package com.example.tiki_taka

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tiki_taka.databinding.FragmentMyplaceBinding

class MyplaceFragment : Fragment() {
    private lateinit var binding: FragmentMyplaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyplaceBinding.inflate(layoutInflater)
        return binding.root
    }
}