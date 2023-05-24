package com.example.k_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.k_lab.databinding.ActivityJoin2Binding

class Join2Activity : AppCompatActivity() {
    lateinit var binding: ActivityJoin2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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