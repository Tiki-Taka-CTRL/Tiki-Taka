package com.example.tiki_taka

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tiki_taka.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ChatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    lateinit var btnAddchatRoom: Button
    lateinit var btnSignout: Button
    lateinit var context: FragmentActivity
    lateinit var binding: FragmentChatBinding
    lateinit var firebaseDatabase: DatabaseReference
    lateinit var recycler_chatroom: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChatBinding.inflate(layoutInflater, container, false)
        context = requireActivity()
        initializeView()
        initializeListener()
        setupRecycler()
        return binding.root
    }

    fun initializeView() { //뷰 초기화
        try {
            firebaseDatabase = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("ChatRoom")!!
            btnSignout = binding.btnSignout
            btnAddchatRoom = binding.btnNewMessage
            recycler_chatroom = binding.recyclerChatrooms
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
    }
    fun initializeListener()  //버튼 클릭 시 리스너 초기화
    {
        btnSignout.setOnClickListener()
        {
            signOut()
        }
        btnAddchatRoom.setOnClickListener()  //새 메시지 화면으로 이동
        {
            startActivity(Intent(context, AddChatRoomActivity::class.java))
        }
    }

    fun setupRecycler() {
        recycler_chatroom.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL,false)
        recycler_chatroom.adapter = RecyclerChatRoomsAdapter(context)
    }

    fun signOut()    //로그아웃 실행
    {
        try {
            val builder = AlertDialog.Builder(context)
                .setTitle("로그아웃")
                .setMessage("로그아웃 하시겠습니까?")
                .setPositiveButton("확인"
                ) { dialog, id ->
                    try {
                        FirebaseAuth.getInstance().signOut()             //로그아웃
                        startActivity(Intent(context, LoginActivity::class.java))
                        dialog.dismiss()
//                        finish()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        dialog.dismiss()

                    }
                }
                .setNegativeButton("취소"          //다이얼로그 닫기
                ) { dialog, id ->
                    dialog.dismiss()
                }
            builder.show()
        }catch (e:Exception)
        {
            e.printStackTrace()

        }
    }

//    override fun onBackPressed() {
//        signOut()
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ChatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}