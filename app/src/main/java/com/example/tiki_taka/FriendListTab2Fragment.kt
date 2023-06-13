package com.example.tiki_taka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentFriendLevel2Binding

class FriendListTab2Fragment : Fragment() {
    lateinit var binding: FragmentFriendLevel2Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendLevel2Binding.inflate(layoutInflater)
        return binding.root
    }
}