package com.example.tiki_taka

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiki_taka.databinding.FragmentChattingBinding

class ChattingFragment: Fragment() {
    private lateinit var binding: FragmentChattingBinding
    lateinit var recycler_chatroom: RecyclerView
    lateinit var context: FragmentActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChattingBinding.inflate(layoutInflater)
        recycler_chatroom = binding.recyclerChatting
        context = requireActivity()
        initializeListener()
        setupRecycler()
        return binding.root
    }
    fun initializeListener()  //버튼 클릭 시 리스너 초기화
    {
        binding.btnChattingNewMatching.setOnClickListener() //matching activity로 이동
        {
            val intent = Intent(requireContext(),NewMatchingActivity::class.java)
            startActivity(intent)
        }
    }
    fun setupRecycler() {
        recycler_chatroom.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        recycler_chatroom.adapter = RecyclerChatRoomsAdapter(context)
    }
}