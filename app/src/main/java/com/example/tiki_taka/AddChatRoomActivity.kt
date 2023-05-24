package com.example.tiki_taka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiki_taka.databinding.ActivityAddChatRoomBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddChatRoomActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddChatRoomBinding
    lateinit var btn_exit: AppCompatImageView
    lateinit var edt_opponent: EditText
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var recycler_people: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeView()
        initializeListener()
        setupRecycler()
    }

    fun initializeView()   //뷰 초기화
    {
        firebaseDatabase = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").reference!!
        btn_exit = binding.btnExit
        edt_opponent = binding.edtOpponent
        recycler_people = binding.recyclerPeople
    }
    fun initializeListener()   //버튼 클릭 시 리스너 초기화
    {
        btn_exit.setOnClickListener()
        {
            startActivity(Intent(this@AddChatRoomActivity, MainActivity::class.java))
        }

        edt_opponent.addTextChangedListener(object :
            TextWatcher                  //검색 창에 입력된 글자가 변경될 때마다 검색 내용 업데이트
        {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var adapter = recycler_people?.adapter as RecyclerUsersAdapter
                adapter.searchItem(s.toString())                  //입력된 검색어로 검색 진행 및 업데이트
            }
        })
    }
    fun setupRecycler()   //사용자 목록 초기화 및 업데이트
    {
        recycler_people.layoutManager = LinearLayoutManager(this)
        recycler_people.adapter = RecyclerUsersAdapter(this)
    }
}