package com.example.tiki_taka

import android.graphics.Color
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class MissionActivity : AppCompatActivity() {
    private lateinit var switchButton: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)

        switchButton = findViewById(R.id.switch_button)
        switchButton.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                // 스위치 버튼이 체크된 상태일 때의 동작
                //switchButton.thumbDrawable=resources.getDrawable(R.drawable.switch_thumb_green)
                //switchButton.trackDrawable = resources.getDrawable(R.drawable.switch_track_green)
            } else {
                // 스위치 버튼이 체크되지 않은 상태일 때의 동작
                //switchButton.thumbDrawable=resources.getDrawable(R.drawable.switch_thumb_gray)
                //switchButton.trackDrawable = resources.getDrawable(R.drawable.switch_track_gray)
            }
        }
    }

    lateinit var checkbox1: CheckBox
    lateinit var checkbox2: CheckBox
    lateinit var checkbox3: CheckBox
    lateinit var checkbox4: CheckBox

    private fun checkBox(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)

        checkbox1 = findViewById(R.id.checkbox1)
        checkbox2 = findViewById(R.id.checkbox2)
        checkbox3 = findViewById(R.id.checkbox3)
        checkbox4 = findViewById(R.id.checkbox4)

        checkbox1.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox1.setBackgroundColor(Color.BLACK)
                checkbox1.setTextColor(Color.WHITE)
            } else {
                checkbox1.setBackgroundColor(Color.WHITE)
                checkbox1.setTextColor(Color.BLACK)
            }
        }

        checkbox2.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox1.setBackgroundColor(Color.BLACK)
                checkbox1.setTextColor(Color.WHITE)
            } else {
                checkbox1.setBackgroundColor(Color.WHITE)
                checkbox1.setTextColor(Color.BLACK)
            }
        }

        checkbox3.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox1.setBackgroundColor(Color.BLACK)
                checkbox1.setTextColor(Color.WHITE)
            } else {
                checkbox1.setBackgroundColor(Color.WHITE)
                checkbox1.setTextColor(Color.BLACK)
            }
        }

        checkbox4.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox1.setBackgroundColor(Color.BLACK)
                checkbox1.setTextColor(Color.WHITE)
            } else {
                checkbox1.setBackgroundColor(Color.WHITE)
                checkbox1.setTextColor(Color.BLACK)
            }
        }
    }
}