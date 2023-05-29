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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChattingFragment: Fragment() {
    private lateinit var binding: FragmentChattingBinding
    lateinit var recycler_chatroom: RecyclerView
    lateinit var context: FragmentActivity
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var user: FirebaseUser
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChattingBinding.inflate(layoutInflater)
        recycler_chatroom = binding.recyclerChatting
        context = requireActivity()
        user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
        firebaseDatabase = FirebaseDatabase.getInstance().reference!!
        initializeListener()
        setupRecycler()
        return binding.root
    }
    fun initializeListener()  //버튼 클릭 시 리스너 초기화
    {
        binding.btnChattingNewMatching.setOnClickListener() //matching activity로 이동
        {
            findNewFriend()
            val intent = Intent(requireContext(),NewMatchingActivity::class.java)
            startActivity(intent)

        }
    }

    private fun findNewFriend() {
        val result = firebaseDatabase.child("User").child(user.uid)
        println(result)
    }

    fun setupRecycler() {
        recycler_chatroom.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        recycler_chatroom.adapter = RecyclerChatRoomsAdapter(context)
    }
}