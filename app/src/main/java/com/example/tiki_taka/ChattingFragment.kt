package com.example.tiki_taka

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiki_taka.databinding.FragmentChattingBinding
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.database.ktx.values
import com.google.firebase.ktx.Firebase

class ChattingFragment: Fragment() {
    private lateinit var binding: FragmentChattingBinding
    lateinit var recycler_chatroom: RecyclerView
    lateinit var context: FragmentActivity
    lateinit var database: FirebaseDatabase
    lateinit var user: FirebaseUser
    lateinit var userData: DataSnapshot
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChattingBinding.inflate(layoutInflater)
        recycler_chatroom = binding.recyclerChatting
        context = requireActivity()
        database = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
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