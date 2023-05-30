package com.example.tiki_taka


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tiki_taka.databinding.ActivityNewMatchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import model.ChatRoom
import model.User

class NewMatchingActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMatchingBinding
    lateinit var database: FirebaseDatabase
    lateinit var user: FirebaseUser
    lateinit var userData: DataSnapshot
    var chatRooms: ArrayList<ChatRoom> = arrayListOf()
    lateinit var opponent_user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewMatchingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        setChatRoomData()
        setUserData()
    }

    private fun init() {
        database =
            Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
        binding.btnNewMatchingSubmit.setOnClickListener() {
            findNewFriend()
            //submit누르면 유효성 매칭 정보 확인 후 매칭 다이얼로그 띄우기
        }
    }

    private fun findNewFriend() {
        val users = database.getReference("User")
        users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("users")
                var check : Boolean = false
                for (t in temp.children) {
                    if (user.uid != t.child("uid").value) {   //로그인 유저가 아닌 경우
                        /*
                        여기부터 매칭 조건 검사 부분
                        userData -> 로그인 유저 데이터
                        t -> db에 있는 모든 유저 데이터
                         */
                        if ((userData.child("country").value.toString() == t.child("country").value.toString())) {  //country 가 같은 경우
                            for (i in 0 until chatRooms.size) {
                                Log.d("chatrooms", chatRooms[i].users?.keys.toString())
                                if (chatRooms[i].users?.keys?.contains(t.child("uid").value.toString()) == true) {
                                    if (chatRooms[i].users?.keys?.contains(user.uid) == true) {
                                        check = true
                                    }
                                }
                            }
                            if(check){
                                Toast.makeText(this@NewMatchingActivity,"No Matching...",Toast.LENGTH_SHORT).show()
                            }else {
                                var bundle = Bundle()
                                val dialog = NewMatchingDialogFragment()
                                opponent_user = t.getValue<User>()!!
//                            if(isMatched(opponent_user)){
//                                Toast.makeText(this@NewMatchingActivity,"No Matching...",Toast.LENGTH_SHORT).show()
//                                break
//                            }
                                bundle.putSerializable("opponent_user", opponent_user)
                                dialog.arguments = bundle
                                dialog.show(supportFragmentManager, "NewMatchingDialog")
                                break
                            }
                            check = false
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun isMatched(opponent: User): Boolean {
        var check: Boolean = true
        val currnentUser = FirebaseAuth.getInstance().currentUser!!
        val database2: DatabaseReference =
            Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("ChatRoom")
        database2.child("chatRooms")
            .orderByChild("users/${opponent.uid}").equalTo(currnentUser.uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    check = snapshot.value != null // 채팅방이 없으면 false
                }
            })
        return check
    }

    fun setChatRoomData() {
        val currnentUser = FirebaseAuth.getInstance().currentUser!!
        val database2: DatabaseReference =
            Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("ChatRoom")
        database2.child("chatRooms")
            .orderByChild("users/${currnentUser.uid}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val item = data.getValue<ChatRoom>()
                        if (item != null) {
                            chatRooms.add(item)
                        }
                    }
                }
            })
    }

    fun setUserData() {
        user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
        val users = database.getReference("User")
        val result = users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("users")
                var target: String
                for (t in temp.children) {
                    if (user.uid == t.child("uid").value) {   //로그인 유저가 아닌 경우
                        userData = t
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}