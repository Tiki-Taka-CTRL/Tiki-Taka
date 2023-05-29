package com.example.tiki_taka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.tiki_taka.databinding.ActivityNewMatchingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class NewMatchingActivity : AppCompatActivity() {
    lateinit var binding : ActivityNewMatchingBinding
    lateinit var database: FirebaseDatabase
    lateinit var user: FirebaseUser
    lateinit var userData: DataSnapshot
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewMatchingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        setUserData()
    }
    private fun init() {
        database = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app")
        binding.btnNewMatchingSubmit.setOnClickListener(){
            val target = findNewFriend()
            Log.d("target", target.toString())
            //submit누르면 유효성 매칭 정보 확인 후 매칭 다이얼로그 띄우기
        }
    }

    private fun findNewFriend(): DataSnapshot? {
        val users = database.getReference("User")
        var target: DataSnapshot? = null
        users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("users")

                for (t in temp.children) {
                    if (user.uid != t.child("uid").value) {   //로그인 유저가 아닌 경우
                        /*
                        여기부터 매칭 조건 검사 부분
                        userData -> 로그인 유저 데이터
                        t -> db에 있는 모든 유저 데이터
                         */
                        if (userData.child("country").value.toString() == t.child("country").value.toString()) {  //country 가 같은 경우
                            target = t
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return target
    }

    fun setUserData(){
        user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
        val users = database.getReference("User")
        val result = users.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("users")
                var target: String
                for (t in temp.children){
                    if(user.uid == t.child("uid").value){   //로그인 유저가 아닌 경우
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