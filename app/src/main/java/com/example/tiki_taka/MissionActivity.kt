package com.example.tiki_taka

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import model.Mission
import kotlin.random.Random
import kotlin.random.nextInt

class MissionActivity : AppCompatActivity() {
    private lateinit var switchButton: SwitchCompat
    val database = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Mission")
    val missions_lv1 = arrayListOf<Mission>()
    var missions_lv2 = arrayListOf<Mission>()
    val missions_lv3 = arrayListOf<Mission>()

    lateinit var checkbox1: CheckBox
    lateinit var checkbox2: CheckBox
    lateinit var checkbox3: CheckBox
    lateinit var checkbox4: CheckBox
    lateinit var question_title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)
        initLayout()
        setupMission()
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

    private fun initLayout() {
        checkbox1 = findViewById(R.id.checkbox1)
        checkbox2 = findViewById(R.id.checkbox2)
        checkbox3 = findViewById(R.id.checkbox3)
        checkbox4 = findViewById(R.id.checkbox4)
        question_title = findViewById(R.id.question_title)!!


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

    private fun setupMission() {
        database.child("lv2").addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = snapshot.children
                for (item in items){
//                    println(item.getValue<Mission>())
                    missions_lv2.add(item.getValue<Mission>()!!)
                }

                val mission = getOneRandomMission(2)
                question_title.setText(mission.title)
                checkbox1.setText(mission.ans1)
                checkbox2.setText(mission.ans2)
                checkbox3.setText(mission.ans3)
                checkbox4.setText(mission.ans4)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun insertMissionData(level: Int, title: String, ans: ArrayList<String>, ans_num: Int){
        database.child("lv2").child("What is his or her favorite pet animal?").setValue(Mission(level, title, ans[0], ans[1], ans[2], ans[3], ans_num))
    }

    fun getOneRandomMission(level: Int): Mission{
        val rNum = Random.nextInt(missions_lv2.size)
        return missions_lv2[rNum]
    }
}