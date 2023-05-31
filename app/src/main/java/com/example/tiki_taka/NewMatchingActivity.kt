package com.example.tiki_taka

import NewMatchingFragmentAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.tiki_taka.databinding.ActivityNewMatchingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import model.User

class NewMatchingActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewMatchingBinding
    lateinit var database: FirebaseDatabase
    lateinit var user: FirebaseUser
    lateinit var userData: DataSnapshot
    lateinit var opponent_user: User

    //TabLayout 구현에 필요한 부분
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewMatchingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        setUserData()

        viewPager = binding.viewNewMatching
        tabLayout = binding.tabNewMatching

        // Create a list of fragment titles
        val tabTitles = listOf("Hobby", "Music", "Country", "Major")

        // Create a list of fragments for the ViewPager2
        val fragments = listOf(
            NewMatchingTab1Fragment(),
            NewMatchingTab2Fragment(),
            NewMatchingTab3Fragment(),
            NewMatchingTab4Fragment()
        )

        // Set up the ViewPager2 with the fragments
        viewPager.adapter = NewMatchingFragmentAdapter(this, fragments)

        // Connect the TabLayout with the ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tabTitles[position]
        }.attach()
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

                for (t in temp.children) {
                    if (user.uid != t.child("uid").value) {   //로그인 유저가 아닌 경우
                        /*
                        여기부터 매칭 조건 검사 부분
                        userData -> 로그인 유저 데이터
                        t -> db에 있는 모든 유저 데이터
                         */
                        if (userData.child("country").value.toString() == t.child("country").value.toString()) {  //country 가 같은 경우
                            var bundle = Bundle()
                            val dialog = NewMatchingDialogFragment()
                            opponent_user = t.getValue<User>()!!
                            bundle.putSerializable("opponent_user", opponent_user)

                            dialog.arguments = bundle
                            dialog.show(supportFragmentManager, "NewMatchingDialog")
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
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