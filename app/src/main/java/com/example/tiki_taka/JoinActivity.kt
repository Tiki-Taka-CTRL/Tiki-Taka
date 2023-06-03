package com.example.tiki_taka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.tiki_taka.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import model.User

class JoinActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var edt_email: EditText
    lateinit var edt_password: EditText
    lateinit var edt_nickname: EditText
    lateinit var binding: ActivityJoinBinding
    private val database: DatabaseReference = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoinBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initializeView()
        spinner()
        init()
    }

    private fun init() {
        //회원가입-2 화면 인텐트
        binding.btnSignIn.setOnClickListener {
            signUp()
        }

        binding.backlogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    fun initializeView() {  //뷰 초기화
        auth = FirebaseAuth.getInstance()
        edt_email = binding.idTextView
        edt_password = binding.passwdTextView
        edt_nickname = binding.nickTextView
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

    fun signUp() {     //회원 가입 실행
        var email = edt_email.text.toString()           //각 입력란 값 String으로 변환
        var password = edt_password.text.toString()
        var nickname = edt_nickname.text.toString()
        var country = binding.spinnerCountry.selectedItem.toString()
        var city = binding.spinnerCity.selectedItem.toString()
        println(country)
        auth.createUserWithEmailAndPassword(email, password)      //FirebaseAuth에 회원가입 성공 시
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {     //회원 가입 성공 시
                    try {
                        val user = auth.currentUser
                        val userId = user?.uid
                        val userIdSt = userId.toString()
                        val input_user : User = User(userIdSt, nickname, email, country, city)
                        database.child("users")
                            .child(userId.toString()).setValue(input_user) { databaseError, _ ->
                                if (databaseError != null) {
                                    // 에러 처리
                                    Log.e("테스트", "데이터 저장 실패: ${databaseError.message}")
                                } else {
                                    // 성공 처리
                                    Log.d("테스트", "데이터 저장 성공")
                                }//Firebase RealtimeDatabase에 User 정보 추가
                            }
                        Toast.makeText(this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("UserId", "$userId")
                        val intent = Intent(this,Join2Activity::class.java)
                        intent.putExtra("userinfo",input_user)
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "화면 이동 중 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                } else
                    Toast.makeText(this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()

            }

    }
}