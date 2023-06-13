package com.example.tiki_taka

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentMyplaceBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import model.User

class MyplaceFragment : Fragment() {
    val database = FirebaseDatabase.getInstance().getReference()
    private lateinit var binding: FragmentMyplaceBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyplaceBinding.inflate(layoutInflater)
        initLayout()
        return binding.root
    }

    private fun initLayout() {
        val user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
        val users = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
        val result = users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("users")
                var target: String
                for (t in temp.children) {
                    if (user.uid == t.child("uid").value) {
                        val cur_user = t.getValue<User>()!!
                        binding.rachel.text = cur_user.nickname
                        binding.imageViewProfile.setImageResource(cur_user.img)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}