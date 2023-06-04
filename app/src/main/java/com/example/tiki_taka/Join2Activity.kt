package com.example.tiki_taka

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.ViewGroup.LayoutParams
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.tiki_taka.databinding.ActivityJoin2Binding
import com.google.firebase.firestore.auth.User

class Join2Activity : AppCompatActivity() {
    lateinit var binding: ActivityJoin2Binding
    lateinit var user : model.User
    private val MAX_SELECTION = 5
    private val selectedButtons = HashSet<Button>()
    private val buttonBackgrounds = HashMap<Button, Int>()

    private val elements = arrayOf(
        "movie", "korean", "food","netflix","netherlands","english","BTS"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityJoin2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = intent.getSerializableExtra("userinfo") as model.User
        binding.userNicknameTv.text = user.nickname

        createButtons() // 버튼 생성 및 추가
        init()
    }

    private fun init() {
        //정보 등록 완료(메인화면 이동)
        binding.btnDone.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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