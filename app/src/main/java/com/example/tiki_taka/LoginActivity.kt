package com.example.tiki_taka

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.tiki_taka.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var btn_signUp: Button
    lateinit var btn_signIn: Button
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var preference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initProperty()
        initializeView()
        init()
    }

    private fun init() {
        val id = binding.edtEmail.text.toString()
        val passwd = binding.edtPassword.text.toString()

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
        if (edt_email.text.toString().isNullOrBlank() &&   //아이디 또는 패스워드가 입력되었는 지 유효성 검사
            edt_password.text.toString().isNullOrBlank())
            Toast.makeText(this, "아이디 또는 패스워드를 입력해주세요", Toast.LENGTH_SHORT).show()
        else {
            auth.signInWithEmailAndPassword(edt_email.text.toString(), edt_password.text.toString())    //로그인 실행
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d("로그인", "성공")
                        val user = auth.currentUser
                        updateUI(user)
                        finish()
                    } else {
                        Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    fun initProperty() {    //초기 변수 세팅
        auth = FirebaseAuth.getInstance()     //Firebase 계정 관련 변수
        preference = getSharedPreferences("setting", MODE_PRIVATE)    //로그인 정보 저장용 SharedPreference
    }

    fun initializeView() {   //뷰 초기화
//        btn_signUp = binding.btnSignup
//        btn_signIn = binding.btnSignIn
        edt_email = binding.edtEmail
        edt_password = binding.edtPassword

        edt_email.setText(preference.getString("email", ""))     //마지막으로 로그인 한 이메일 세팅
        edt_password.setText(preference.getString("password", ""))   //마지막으로 로그인 한 패스워드 세팅
    }

    private fun updateUI(user: FirebaseUser?) { //로그인 성공 시 화면 이동
        if (user != null) {
            try {
                var preference = getSharedPreferences("setting", MODE_PRIVATE).edit()    //이메일 및 패스워드 저장
                preference.putString("email", edt_email.text.toString())
                preference.putString("password", edt_password.text.toString())
                preference.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}