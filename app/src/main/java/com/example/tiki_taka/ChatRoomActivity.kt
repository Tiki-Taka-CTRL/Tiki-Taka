package com.example.tiki_taka

import android.content.Intent
import android.graphics.Paint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiki_taka.databinding.ActivityChatRoomBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import model.ChatRoom
import model.Message
import model.User
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ChatRoomActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatRoomBinding
    lateinit var btn_exit: ImageButton
    lateinit var btn_submit: Button
    lateinit var txt_title: TextView
    lateinit var edt_message: EditText
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var recycler_talks: RecyclerView
    lateinit var chatRoom: ChatRoom
    lateinit var opponentUser: User
    lateinit var chatRoomKey: String
    lateinit var myUid: String
    lateinit var opponent_uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initializeProperty()
        initializeView()
        initializeListener()
        setupChatRooms()
        setTimer()
    }
    fun <T: Serializable> Intent.intentSerializable(key: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.getSerializableExtra(key, clazz)
        } else {
            this.getSerializableExtra(key) as T?
        }
    }
    fun initializeProperty() {  //변수 초기화
        myUid = FirebaseAuth.getInstance().currentUser?.uid!!              //현재 로그인한 유저 id
        firebaseDatabase = FirebaseDatabase.getInstance().reference!!
        opponent_uid = intent.getStringExtra("opponent_uid")!!
        chatRoom = (intent.getSerializableExtra("ChatRoom")) as ChatRoom      //채팅방 정보
        chatRoomKey = intent.getStringExtra("ChatRoomKey")!!            //채팅방 키
        opponentUser = (intent.getSerializableExtra("Opponent")) as User    //상대방 유저 정보
    }

    fun initializeView() {    //뷰 초기화
        btn_exit = binding.imgbtnQuit
        edt_message = binding.edtMessage
        recycler_talks = binding.recyclerMessages
        btn_submit = binding.btnSubmit
        txt_title = binding.txtTItle
        txt_title.text = opponentUser!!.nickname ?: ""

    }

    fun initializeListener() {   //버튼 클릭 시 리스너 초기화
        btn_exit.setOnClickListener()
        {
            startActivity(Intent(this@ChatRoomActivity, MainActivity::class.java))
        }
        btn_submit.setOnClickListener()
        {
            putMessage()
        }
    }

    fun setupChatRooms() {              //채팅방 목록 초기화 및 표시
        if (chatRoomKey.isNullOrBlank())
            setupChatRoomKey()
        else
            setupRecycler()
    }

    fun setupChatRoomKey() {            //chatRoomKey 없을 경우 초기화 후 목록 초기화
        val myUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var firebaseDatabaseChatRoom: DatabaseReference = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("ChatRoom")
        firebaseDatabaseChatRoom
            .child("chatRooms").orderByChild("users/${opponent_uid}").equalTo(myUid)    //상대방의 Uid가 포함된 목록이 있는지 확인
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        chatRoomKey = data.key!!          //chatRoomKey 초기화
                        setupRecycler()                  //목록 업데이트
                        break
                    }
                }
            })
    }

    fun putMessage() {       //메시지 전송
        try {
            var message = Message(myUid, getDateTimeString(), edt_message.text.toString())    //메시지 정보 초기화
            Log.i("ChatRoomKey", chatRoomKey)
            var firebaseDatabaseChatRoom: DatabaseReference = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("ChatRoom")

            firebaseDatabaseChatRoom.child("chatRooms")
                .child(chatRoomKey).child("messages")                   //현재 채팅방에 메시지 추가
                .push().setValue(message).addOnSuccessListener {
                    Log.i("putMessage", "메시지 전송에 성공하였습니다.")
                    edt_message.text.clear()
                }.addOnCanceledListener {
                    Log.i("putMessage", "메시지 전송에 실패하였습니다")
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("putMessage", "메시지 전송 중 오류가 발생하였습니다.")
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

    fun setupRecycler() {            //목록 초기화 및 업데이트
        recycler_talks.layoutManager = LinearLayoutManager(this)
        recycler_talks.adapter = RecyclerMessagesAdapter(this, chatRoomKey, opponentUser.uid)
    }

    fun setTimer(){
        var mSecond = 5
        val mTimer = Timer()
        val mTimerTask = object : TimerTask() {
            override fun run() {
                val mHandler = Handler(Looper.getMainLooper())
                mHandler.postDelayed({
                    mSecond -= 1
                    binding.missionBtn.text = mSecond.toString()
                    if (mSecond <= 0) {
                        binding.missionBtn.text = "Accept"
                        binding.missionBtn.isEnabled = true
                        mTimer.cancel()
                    }
                }, 0)
            }
        }
        mTimer.schedule(mTimerTask, 0, 1000)
    }
}