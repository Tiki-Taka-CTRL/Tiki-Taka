package com.example.tiki_taka

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
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

class MissionActivity : AppCompatActivity() {
    private lateinit var switchButton: SwitchCompat
    val database =
        Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("Mission")
    val databaseChatRoom =
        Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
            .getReference("ChatRoom")
    val missions_lv1 = arrayListOf<MissionLv1>()
    var missions_lv2 = arrayListOf<MissionLv2>()

    //    val missions_lv3 = arrayListOf<Mission>()
    lateinit var chatRoom: ChatRoom
    lateinit var chatRoomkey: String
    lateinit var opponentUser: User
    lateinit var currentUser: User
    lateinit var checkbox1: CheckBox
    lateinit var checkbox2: CheckBox
    lateinit var checkbox3: CheckBox
    lateinit var checkbox4: CheckBox
    lateinit var missionLv2: MissionLv2
    lateinit var missionLv1: MissionLv1
    lateinit var submitbtn: AppCompatButton
    lateinit var question_title: TextView
    lateinit var switch_btn: SwitchCompat
    lateinit var title_lv1: TextView
    lateinit var title: TextView
    lateinit var title_lv2: TextView
    var mission1_ans : Boolean = true
    var mission2_ans : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mission)
        currentUser = intent.getSerializableExtra("cur_user") as User
        chatRoom = intent.getSerializableExtra("chatRoom") as ChatRoom
        chatRoomkey = intent.getSerializableExtra("ChatRoomkey") as String
        opponentUser = intent.getSerializableExtra("opponent") as User
        initLayout()
        setupMission()

        switchButton = findViewById(R.id.switch_button)

        submitbtn.setOnClickListener {
            chatRoom.missionStatus++
            Log.d("missionstatus", chatRoom.missionStatus.toString())
            if (chatRoom.missionStatus == 1) { //미션 전송
                chatRoom.missionlv1 = missionLv1
                chatRoom.missionlv2 = missionLv2
                databaseChatRoom.child("chatRooms").child(chatRoomkey)
                    .child("missionlv1")                //현재 채팅방에 메시지 추가
                    .push().setValue(missionLv1).addOnSuccessListener {

                    }.addOnCanceledListener {

                    }
                databaseChatRoom.child("chatRooms").child(chatRoomkey)
                    .child("missionlv2")                //현재 채팅방에 메시지 추가
                    .push().setValue(missionLv2).addOnSuccessListener {

                    }.addOnCanceledListener {

                    }
                databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionStatus")
                    .setValue(chatRoom.missionStatus)
                databaseChatRoom.child("chatRooms").child(chatRoomkey).child("messages").push()
                    .setValue(Message(currentUser.uid!!, getDateTimeString(), "       ❓[Mission Sent]❓\n Let's Solve with your friend!"))
                    .addOnSuccessListener {
                        Log.i("putMessage", "메시지 전송에 성공하였습니다.")
                    }.addOnCanceledListener {
                        Log.i("putMessage", "메시지 전송에 실패하였습니다")
                    }
            } else if (chatRoom.missionStatus == 2) { //미션 풀기
                if ((mission1_ans == missionLv1.check_ans) && (mission2_ans == missionLv2.ans_num)) {
                    databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionStatus")
                        .setValue(chatRoom.missionStatus)
                    databaseChatRoom.child("chatRooms").child(chatRoomkey).child("messages").push()
                        .setValue(Message(currentUser.uid!!, getDateTimeString(), "       🔥[Mission Solved!]🔥\nCongratulation! Your friend Solve Mission😎"))
                        .addOnSuccessListener {
                            Log.i("putMessage", "메시지 전송에 성공하였습니다.")
                            updateMissionCount(chatRoom)
                        }.addOnCanceledListener {
                            Log.i("putMessage", "메시지 전송에 실패하였습니다")
                        }
                } else {
                    Toast.makeText(this@MissionActivity, "MissionFailed", Toast.LENGTH_LONG)
                        .show()
                }
            } else {
                Toast.makeText(this@MissionActivity, "No more Chance..", Toast.LENGTH_LONG)
                    .show()
            }
            var intent = Intent(this@MissionActivity, ChatRoomActivity::class.java)
            intent.putExtra("ChatRoom", chatRoom)       //채팅방 정보
            intent.putExtra("Opponent", opponentUser)    //상대방 정보
            intent.putExtra("ChatRoomKey", chatRoomkey)
            startActivity(intent)
        }
    }

    private fun updateMissionCount(chatRoom: ChatRoom) {
        chatRoom.missionCount ++
        databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionCount")
            .setValue(chatRoom.missionCount)
            .addOnSuccessListener {
                Log.i("updateMissionCount", "missionCOunt증가")

            }.addOnCanceledListener {
                Log.i("updateMissionCount", "missionCOunt증가실패")
            }
    }


    override fun onStart() {
        super.onStart()
    }

    private fun initLayout() {
        checkbox1 = findViewById(R.id.checkbox1)
        checkbox2 = findViewById(R.id.checkbox2)
        checkbox3 = findViewById(R.id.checkbox3)
        checkbox4 = findViewById(R.id.checkbox4)
        submitbtn = findViewById(R.id.btn_submit)
        question_title = findViewById(R.id.match)!!
        title_lv1 = findViewById(R.id.mission_Q1)
        title_lv2 = findViewById(R.id.mission_Q2)
        title = findViewById(R.id.match)
        switch_btn = findViewById(R.id.switch_button)

    }

    private fun setupMission() {
        databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionStatus").get().addOnSuccessListener {
            chatRoom.missionStatus = it.getValue<Int>()!!
        }
        if(chatRoom.missionCount >= 5){

        }else {
            if (chatRoom.missionStatus == 0) {
                database.child("lv2").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val items = snapshot.children
                        for (item in items) {
                            missions_lv2.add(item.getValue<MissionLv2>()!!)
                        }
                        missionLv2 = getOneRandomMission(2) as MissionLv2
                        initLayout()
                        title_lv2.setText(missionLv2.title)
                        checkbox1.setText(missionLv2.ans1)
                        checkbox2.setText(missionLv2.ans2)
                        checkbox3.setText(missionLv2.ans3)
                        checkbox4.setText(missionLv2.ans4)
                        checkbox1.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                missionLv2.ans_num = 1
                            }
                        }

                        checkbox2.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                missionLv2.ans_num = 2
                                Log.d("putMessage", "체크됨")
                            }
                        }

                        checkbox3.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                missionLv2.ans_num = 3
                            }
                        }

                        checkbox4.setOnCheckedChangeListener { _, isChecked ->
                            if (isChecked) {
                                missionLv2.ans_num = 4
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

                database.child("lv1").addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val items = snapshot.children
                        for (item in items) {
                            missions_lv1.add(item.getValue<MissionLv1>()!!)
                        }

                        missionLv1 = getOneRandomMission(1) as MissionLv1
                        initLayout()
                        title_lv1.setText(missionLv1.title)
                        switchButton.setOnCheckedChangeListener { _, isChecked ->
                            missionLv1.check_ans = isChecked
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            } else {
                title.text = "Check Your Friend Answer"
                databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionlv1")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val items = snapshot.children

                            for (item in items) {
                                title_lv1.setText(item.getValue<MissionLv1>()!!.title)
                                missionLv1 = item.getValue<MissionLv1>()!!
                                mission1_ans = item.getValue<MissionLv1>()!!.check_ans == true
                            }
                            switchButton.setOnCheckedChangeListener { _, isChecked ->
                                missionLv1.check_ans = isChecked
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
                databaseChatRoom.child("chatRooms").child(chatRoomkey).child("missionlv2")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val items = snapshot.children
                            for (item in items) {
                                title_lv2.setText(item.getValue<MissionLv2>()!!.title)
                                checkbox1.setText(item.getValue<MissionLv2>()!!.ans1)
                                checkbox2.setText(item.getValue<MissionLv2>()!!.ans2)
                                checkbox3.setText(item.getValue<MissionLv2>()!!.ans3)
                                checkbox4.setText(item.getValue<MissionLv2>()!!.ans4)
                                missionLv2 = item.getValue<MissionLv2>()!!
                                mission2_ans = item.getValue<MissionLv2>()!!.ans_num
                            }

                            checkbox1.setOnCheckedChangeListener { _, isChecked ->
                                if (isChecked) {
                                    missionLv2.ans_num = 1
                                }
                            }

                            checkbox2.setOnCheckedChangeListener { _, isChecked ->
                                if (isChecked) {
                                    missionLv2.ans_num = 2
                                    Log.d("putMessage", "체크됨")
                                }
                            }

                            checkbox3.setOnCheckedChangeListener { _, isChecked ->
                                if (isChecked) {
                                    missionLv2.ans_num = 3
                                }
                            }

                            checkbox4.setOnCheckedChangeListener { _, isChecked ->
                                if (isChecked) {
                                    missionLv2.ans_num = 4
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                        }
                    })
            }
        }
    }

//    fun insertMissionData(level: Int, title: String, ans: ArrayList<String>, ans_num: Int){
//        database.child("lv2").child("What is his or her favorite pet animal?").setValue(MissionLv1(level, title, ans[0], ans[1], ans[2], ans[3], ans_num))
//    }

    fun getOneRandomMission(level: Int): Mission? {
        if (level == 2) {
            val rNum = Random.nextInt(missions_lv2.size)
            return missions_lv2[rNum]
        } else if (level == 1) {
            val rNum = Random.nextInt(missions_lv1.size)
            return missions_lv1[rNum]
        }
        return null
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
}