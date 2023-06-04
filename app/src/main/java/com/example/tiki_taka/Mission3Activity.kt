package com.example.tiki_taka

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.SwitchCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import model.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.random.Random

class Mission3Activity : AppCompatActivity() {
    private lateinit var switchButton: SwitchCompat
    val database =
        Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Mission")
    val databaseChatRoom =
        Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("ChatRoom")


    //    val missions_lv3 = arrayListOf<Mission>()
    lateinit var chatRoom: ChatRoom
    lateinit var chatRoomkey: String
    lateinit var opponentUser: User
    lateinit var currentUser: User
    lateinit var checkbox1: CheckBox
    lateinit var checkbox2: CheckBox
    lateinit var checkbox3: CheckBox
    lateinit var checkbox4: CheckBox
    lateinit var checkbox1ans: EditText
    lateinit var checkbox2ans: EditText
    lateinit var checkbox3ans: EditText
    lateinit var checkbox4ans: EditText
    lateinit var missionLv2: MissionLv2
    lateinit var missionLv1: MissionLv1
    lateinit var submitbtn: AppCompatButton
    lateinit var question_title: TextView
    lateinit var title_lv1: EditText
    lateinit var title_lv2: EditText
    var firstquestionanswer = false
    var answer = 0
    var mission1_ans: Boolean = true
    var mission2_ans: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission_lv3)
        currentUser = intent.getSerializableExtra("cur_user") as User
        chatRoom = intent.getSerializableExtra("chatRoom") as ChatRoom
        chatRoomkey = intent.getSerializableExtra("ChatRoomkey") as String
        opponentUser = intent.getSerializableExtra("opponent") as User
        initLayout()
        switchButton = findViewById(R.id.switch_button)
        if (chatRoom.missionStatus == 1) {
            setupMission()
        }
        submitbtn.setOnClickListener {
            chatRoom.missionStatus++
            Log.d("missionstatus", chatRoom.missionStatus.toString())
            if (chatRoom.missionStatus == 1) { //미션 전송
                chatRoom.missionLv1 = MissionLv1(1, firstquestionanswer, title_lv1.text.toString())
                chatRoom.missionLv2 = MissionLv2(
                    2,
                    checkbox1ans.text.toString(),
                    checkbox2ans.text.toString(),
                    checkbox3ans.text.toString(),
                    checkbox4ans.text.toString(),
                    answer,
                    title_lv2.text.toString()
                )
                databaseChatRoom.child("chatRooms").child(chatRoomkey)
                    .child("missionlv3").child("missionlv1")                //현재 채팅방에 메시지 추가
                    .push().setValue(chatRoom.missionLv1).addOnSuccessListener {

                    }.addOnCanceledListener {

                    }
                databaseChatRoom.child("chatRooms").child(chatRoomkey)
                    .child("missionlv3").child("missionlv2")                //현재 채팅방에 메시지 추가
                    .push().setValue(chatRoom.missionLv2).addOnSuccessListener {

                    }.addOnCanceledListener {

                    }
                databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionStatus")
                    .setValue(chatRoom.missionStatus)
                databaseChatRoom.child("chatRooms").child(chatRoomkey).child("messages").push()
                    .setValue(Message(currentUser.uid!!, getDateTimeString(), "Mission Sent"))
                    .addOnSuccessListener {
                        Log.i("putMessage", "메시지 전송에 성공하였습니다.")
                    }.addOnCanceledListener {
                        Log.i("putMessage", "메시지 전송에 실패하였습니다")
                    }
            } else if (chatRoom.missionStatus == 2) { //미션 풀기
                Log.d("test1234",missionLv1.check_ans.toString())
                Log.d("test1234",missionLv2.ans_num.toString())
                if ((firstquestionanswer == missionLv1.check_ans) && (answer == missionLv2.ans_num)) {
                    databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionStatus")
                        .setValue(chatRoom.missionStatus)
                    databaseChatRoom.child("chatRooms").child(chatRoomkey).child("messages").push()
                        .setValue(
                            Message(
                                currentUser.uid!!,
                                getDateTimeString(),
                                "Mission Solved!"
                            )
                        )
                        .addOnSuccessListener {
                            Log.i("putMessage", "메시지 전송에 성공하였습니다.")
                            updateMissionCount(chatRoom)
                        }.addOnCanceledListener {
                            Log.i("putMessage", "메시지 전송에 실패하였습니다")
                        }
                } else {
                    Toast.makeText(this@Mission3Activity, "MissionFailed", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(this@Mission3Activity, "No more Chance..", Toast.LENGTH_LONG)
                    .show()
            }
            var intent = Intent(this@Mission3Activity, ChatRoomActivity::class.java)
            intent.putExtra("ChatRoom", chatRoom)       //채팅방 정보
            intent.putExtra("Opponent", opponentUser)    //상대방 정보
            intent.putExtra("ChatRoomKey", chatRoomkey)
            startActivity(intent)
        }
    }

    private fun updateMissionCount(chatRoom: ChatRoom) {
        chatRoom.missionCount++
        databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionCount").push()
            .setValue(chatRoom.missionCount)
            .addOnSuccessListener {
                Log.i("updateMissionCount", "missionCOunt증가")

            }.addOnCanceledListener {
                Log.i("updateMissionCount", "missionCOunt증가실패")
            }
    }

    private fun initLayout() {
        checkbox1 = findViewById(R.id.checkbox1)
        checkbox2 = findViewById(R.id.checkbox2)
        checkbox3 = findViewById(R.id.checkbox3)
        checkbox4 = findViewById(R.id.checkbox4)
        checkbox1ans = findViewById(R.id.edt_ans1)
        checkbox2ans = findViewById(R.id.edt_ans2)
        checkbox3ans = findViewById(R.id.edt_ans3)
        checkbox4ans = findViewById(R.id.edt_ans4)
        submitbtn = findViewById(R.id.btn_submit)
        title_lv1 = findViewById(R.id.edt_question1)
        title_lv2 = findViewById(R.id.edt_question2)
        switchButton = findViewById(R.id.switch_button)
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            firstquestionanswer = isChecked
        }
        checkbox1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                answer = 1
            }
        }

        checkbox2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                answer = 2
                Log.d("putMessage", "체크됨")
            }
        }

        checkbox3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                answer = 3
            }
        }

        checkbox4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                answer = 4
            }
        }

    }

    fun getDateTimeString(): String {          //메시지 보낸 시각 정보 반환
        try {
            var localDateTime = LocalDateTime.now()
            localDateTime.atZone(TimeZone.getDefault().toZoneId())
            var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            return localDateTime.format(dateTimeFormatter).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            throw Exception("getTimeError")
        }
    }

    private fun setupMission() {
        databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionlv3").child("missionlv1")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = snapshot.children
                    for (item in items) {
                        title_lv1.setText(item.getValue<MissionLv1>()!!.title)
                        missionLv1 = item.getValue<MissionLv1>()!!
                        mission1_ans = item.getValue<MissionLv1>()!!.check_ans == true
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionlv3").child("missionlv2")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val items = snapshot.children
                    for (item in items) {
                        title_lv2.setText(item.getValue<MissionLv2>()!!.title)
                        checkbox1ans.setText(item.getValue<MissionLv2>()!!.ans1)
                        checkbox2ans.setText(item.getValue<MissionLv2>()!!.ans2)
                        checkbox3ans.setText(item.getValue<MissionLv2>()!!.ans3)
                        checkbox4ans.setText(item.getValue<MissionLv2>()!!.ans4)
                        missionLv2 = item.getValue<MissionLv2>()!!
                        mission2_ans = item.getValue<MissionLv2>()!!.ans_num
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}
