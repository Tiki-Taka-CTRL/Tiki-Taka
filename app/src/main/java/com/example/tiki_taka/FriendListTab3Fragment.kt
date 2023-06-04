package com.example.tiki_taka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentFriendLevel3Binding

class FriendListTab3Fragment : Fragment() {
    lateinit var binding: FragmentFriendLevel3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFriendLevel3Binding.inflate(layoutInflater)
        return binding.root
    }
}