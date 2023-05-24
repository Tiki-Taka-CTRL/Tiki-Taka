package com.example.k_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.k_lab.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        spinner()
        init()
    }

    private fun init() {
        //회원가입-2 화면 인텐트
        binding.btnSignIn.setOnClickListener {
            val intent = Intent(this, Join2Activity::class.java)
            startActivity(intent)
        }

        binding.backlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun spinner() {
        //스피너 선언
        val sp_country = findViewById<Spinner>(R.id.spinner_country)
        val sp_city = findViewById<Spinner>(R.id.spinner_city)

        //어댑터 설정 array_country.xml
        sp_country.adapter = ArrayAdapter.createFromResource(this, R.array.spinner_country, android.R.layout.simple_spinner_item)

        sp_country.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                when(position) {
                    0 -> {
                        sp_city.adapter = ArrayAdapter.createFromResource(this@JoinActivity, R.array.spinner_kor, android.R.layout.simple_spinner_item)
                    }
                    1 -> {
                        sp_city.adapter = ArrayAdapter.createFromResource(this@JoinActivity, R.array.spinner_ntl, android.R.layout.simple_spinner_item)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}