package com.example.tiki_taka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentNewMatchingTab1Binding

class NewMatchingTab1Fragment : Fragment() {
    lateinit var binding: FragmentNewMatchingTab1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMatchingTab1Binding.inflate(layoutInflater)
        return binding.root
    }
}