package com.example.k_lab

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.k_lab.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        val id = binding.editID.text.toString()
        val passwd = binding.editPasswd.text.toString()

        //로그인 버튼
        binding.btnLogin.setOnClickListener {
            login(id, passwd)
            if (id.isEmpty() && passwd.isEmpty()) {
                Toast.makeText(this, "Enter your id and password.", Toast.LENGTH_SHORT).show()
            } else {
                //login(id, passwd)
            }
        }

        //회원가입 화면 인텐트
        binding.signin.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        //비밀번호 찾기 화면 인텐트
        binding.findpasswd.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(id: String, passwd: String) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}