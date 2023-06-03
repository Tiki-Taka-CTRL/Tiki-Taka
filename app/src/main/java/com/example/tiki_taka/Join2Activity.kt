package com.example.tiki_taka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiki_taka.databinding.ActivityJoin2Binding
import com.google.firebase.firestore.auth.User

class Join2Activity : AppCompatActivity() {
    lateinit var binding: ActivityJoin2Binding
    lateinit var user : model.User
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = intent.getSerializableExtra("userinfo") as model.User
        binding.userNicknameTv.text = user.nickname
        init()
    }

    private fun init() {
        //정보 등록 완료(메인화면 이동)
        binding.btnDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}