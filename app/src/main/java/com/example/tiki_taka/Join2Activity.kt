package com.example.tiki_taka

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiki_taka.databinding.ActivityJoin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class Join2Activity : AppCompatActivity() {
    lateinit var binding: ActivityJoin2Binding
    private val MAX_SELECTION = 5
    private val selectedButtons = HashSet<Button>()
    private val buttonBackgrounds = HashMap<Button, Int>()
    private val database: DatabaseReference = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
    lateinit var auth: FirebaseAuth
    private val elements = arrayOf(
        "movie", "korean", "food","netflix","netherlands","english","BTS"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.userNicknameTv.text = intent.getStringExtra("nickname")
        createButtons() // 버튼 생성 및 추가
        init()
    }

    private fun init() {
        //정보 등록 완료(메인화면 이동)
        auth = FirebaseAuth.getInstance()
        binding.btnDone.setOnClickListener {
            try {
                val user = auth.currentUser
                val userId = user?.uid
                val userIdSt = userId.toString()
                val email = intent.getStringExtra("email")
                val nickname = intent.getStringExtra("nickname")
                val country = intent.getStringExtra("country")
                val city = intent.getStringExtra("city")
                val img = getRandomAvatar()
                val input_user : model.User = model.User(userIdSt, nickname, email, country, city, img)
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
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "화면 이동 중 문제가 발생하였습니다.", Toast.LENGTH_SHORT).show()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getRandomAvatar(): Int {
        val range = (0..9)
        val code = range.random()
        when(code) {
            0 -> return R.drawable.avatar1
            1 -> return R.drawable.avatar2
            2 -> return R.drawable.avatar3
            3 -> return R.drawable.avatar4
            4 -> return R.drawable.avatar5
            5 -> return R.drawable.avatar6
            6 -> return R.drawable.avatar7
            7 -> return R.drawable.avatar8
            8 -> return R.drawable.avatar6
            9 -> return R.drawable.avatar5
            else -> return R.drawable.avatar1
        }
    }

    private fun createButtons() {
        elements.forEach { element ->
            val button = createButton(element)
            binding.linearLayout.addView(button)
        }
    }

    private fun createButton(element: String): Button {
        val button = Button(this)
        button.text = element
        button.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        button.background.setTint(Color.parseColor("#565656")) // HEX 코드로 회색으로 설정

        button.setOnClickListener {
            toggleButtonSelection(button)
        }
        return button
    }

    private fun toggleButtonSelection(button: Button) {
        if (selectedButtons.contains(button)) {
            selectedButtons.remove(button)
            button.background.setTint(Color.parseColor("#565656"))
        } else {
            if (selectedButtons.size >= MAX_SELECTION) {
                // 최대 선택 개수를 초과한 경우 처리
                return
            }
            selectedButtons.add(button)
            button.background.setTint(Color.parseColor("#0000FF")) // HEX 코드로 파란색으로 변경 // 파란색으로 변경해야하는 경우 색상 변경 필요
        }
    }
}