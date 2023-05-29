package com.example.tiki_taka

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.example.tiki_taka.databinding.FragmentNewMatchingDialogBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import model.ChatRoom
import model.User

class NewMatchingDialogFragment : DialogFragment() {
    lateinit var binding : FragmentNewMatchingDialogBinding
    lateinit var opponent_user: User
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewMatchingDialogBinding.inflate(inflater,container,false)
        initLayout()
        return binding.root
    }

    private fun initLayout() {
        var bundle = arguments

        opponent_user = bundle!!.getSerializable("opponent_user") as User
        binding.tvNewDialogName.text = opponent_user.nickname.toString()

        binding.btnNewDialogNo.setOnClickListener {
            dismiss()
        }

        binding.btnNewDialogYes.setOnClickListener{
            addChatRoom()
        }
    }

    fun addChatRoom() {     //채팅방 추가
        opponent_user.uid?.let { Log.d("opponentid", it) }
        val currnentUser = FirebaseAuth.getInstance().currentUser!!
        val database2: DatabaseReference = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("ChatRoom") //넣을 database reference 세팅
        var chatRoom = ChatRoom(         //추가할 채팅방 정보 세팅
            mapOf(currnentUser.uid!!to opponent_user.uid!!, opponent_user.uid!!to currnentUser.uid!!),
            null
        )
        Log.d("Userid", currnentUser.uid!!)
        var myUid = FirebaseAuth.getInstance().uid//내 Uid
//        if (myUid != null) {
//            Log.d("userid..addChatRoom",myUid)
//        }
        database2.child("chatRooms")
            .orderByChild("users/${opponent_user.uid}").equalTo(currnentUser.uid)
        database2.child("chatRooms")
            .orderByChild("users/${opponent_user.uid}").equalTo(currnentUser.uid)       //상대방 Uid가 포함된 채팅방이 있는 지 확인
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {}
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.value== null) {              //채팅방이 없는 경우
                        database2.child("chatRooms").child(opponent_user.uid+currnentUser.uid).setValue(chatRoom).addOnSuccessListener{// 채팅방 새로 생성 후 이동
                            goToChatRoom(chatRoom, opponent_user, opponent_user.uid+currnentUser.uid)
                        }
                    } else {
                        requireActivity().startActivity(Intent(requireContext(), MainActivity::class.java))
                        goToChatRoom(chatRoom, opponent_user, opponent_user.uid+currnentUser.uid)                    //해당 채팅방으로 이동
                    }
                }
            })
    }

    fun goToChatRoom(chatRoom: ChatRoom, opponentUid: User, string: String) {       //채팅방으로 이동
        var intent = Intent(requireContext(), ChatRoomActivity::class.java)
        intent.putExtra("ChatRoom", chatRoom)       //채팅방 정보
        intent.putExtra("Opponent", opponentUid)    //상대방 정보
        intent.putExtra("ChatRoomKey", string)   //채팅방 키
        requireContext().startActivity(intent)
        (requireContext() as AppCompatActivity).finish()
    }
}