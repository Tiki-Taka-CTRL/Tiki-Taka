package com.example.tiki_taka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentNewMatchingTab4Binding

class NewMatchingTab4Fragment : Fragment() {
    lateinit var binding: FragmentNewMatchingTab4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewMatchingTab4Binding.inflate(layoutInflater)
        return binding.root
    }
}