package com.example.tiki_taka


import NewMatchingFragmentAdapter
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.tiki_taka.databinding.ActivityNewMatchingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
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
    val set = mutableSetOf<Int>()
    val allUsers: ArrayList<User> =arrayListOf()
    var NedUser: ArrayList<User> = arrayListOf()
    var korUser: ArrayList<User> = arrayListOf()
    var chatRooms: ArrayList<ChatRoom> = arrayListOf()
    var WhoAmiFrendWith : ArrayList<String> = arrayListOf()
    var switchCheck : Boolean = false
    lateinit var opponent_user: User

    //TabLayout 구현에 필요한 부분
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewMatchingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
        setupAllUserList()
        setUserData()
        setChatRoomData()

        viewPager = binding.viewNewMatching
        tabLayout = binding.tabNewMatching

        // Create a list of fragment titles
        val tabTitles = listOf("hobby", "character", "country", "major")

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
        binding.switchNewMatching.setOnCheckedChangeListener { CompoundButton, onSwitch ->
            //  스위치가 켜지면
            if (onSwitch) {
                Log.d("switch","on")
                switchCheck = true //스위치 켜짐
            }
            //  스위치가 꺼지면
            else {
                Log.d("switch","off")
                switchCheck = false
            }
        }
    }

    private fun findNewFriend() {
        var check = 0
//        Log.d("getKOUser",korUser.size.toString())
//        Log.d("getNeduser",NedUser.size.toString())
//        Log.d("getNeduser",WhoAmiFrendWith.toString())
        val users = database.getReference("User")
        if(switchCheck){//true이면 같은 지역만 서치
            if(userData.child("country").value.toString() == "Netherlands"){//같은 지역에서만
                set.clear()
                while(set.size<NedUser.size){
                    set.add((0 until NedUser.size).random())
                }
//                Log.d("test1234", WhoAmiFrendWith.toString())
                for (i in 0 until NedUser.size){
//                    Log.d("checkWhoamiFriend",WhoAmiFrendWith.contains(NedUser[set.indexOf(i)].uid).toString())
//                    Log.d("checkWhoamiFriend",NedUser[set.indexOf(i)].uid.toString())
//                    Log.d("checkWhoamiFriend",WhoAmiFrendWith.toString())
                    if(!WhoAmiFrendWith.contains(NedUser[set.indexOf(i)].uid)){ //친구리스트에 이미 있으면
                        if(userData.key?.let { findUser(it)?.city } == NedUser[set.indexOf(i)]?.city) {
                            if (userData.key != NedUser[set.indexOf(i)]?.uid) {
                                var bundle = Bundle()
                                val dialog = NewMatchingDialogFragment()
                                opponent_user = NedUser[set.indexOf(i)].uid?.let { findUser(it) }!!
                                Log.d("123456", opponent_user.toString())
                                bundle.putSerializable("opponent_user", opponent_user)
                                dialog.arguments = bundle
                                dialog.show(supportFragmentManager, "NewMatchingDialog")
                                check = 1
                                break
                            }
                        }
                    }
                }
                if(check ==0) {
                    Toast.makeText(this@NewMatchingActivity, "No matching", Toast.LENGTH_LONG)
                        .show()
                }else{
                    check = 0
                }
            }
            else{
                set.clear()
                while(set.size<korUser.size){
                    set.add((0 until korUser.size).random())
                }
                Log.d("test1234", WhoAmiFrendWith.toString())
                for (i in 0 until korUser.size){
                    if(!WhoAmiFrendWith.contains(korUser[set.indexOf(i)].uid)){ //친구리스트에 이미 있으면
                        if(userData.key?.let { findUser(it)?.city } == korUser[set.indexOf(i)]?.city) {
                            if (userData.key != korUser[set.indexOf(i)]?.uid) {
                                var bundle = Bundle()
                                val dialog = NewMatchingDialogFragment()
                                opponent_user = korUser[set.indexOf(i)].uid?.let { findUser(it) }!!
//                                Log.d("123456", opponent_user.toString())
                                bundle.putSerializable("opponent_user", opponent_user)
                                dialog.arguments = bundle
                                dialog.show(supportFragmentManager, "NewMatchingDialog")
                                check =1
                                break
                            }
                        }
                    }
                }
                if(check ==0) {
                    Toast.makeText(this@NewMatchingActivity, "No matching", Toast.LENGTH_LONG)
                        .show()
                }else{
                    check = 0
                }
            }
        }
        else{//false이면 전체 지역에서 찾기
            set.clear()
            while(set.size<allUsers.size){
                set.add((0 until allUsers.size).random())
            }
//            Log.d("testset",set.toString())
            for (i in 0 until allUsers.size){
                if(!WhoAmiFrendWith.contains(allUsers[set.indexOf(i)].uid)){ //친구리스트에 없으면
                    if(userData.key != allUsers[set.indexOf(i)]?.uid){
                        var bundle = Bundle()
                        val dialog = NewMatchingDialogFragment()
                        opponent_user = allUsers[set.indexOf(i)].uid?.let { findUser(it) }!!
//                        Log.d("123456",opponent_user.toString())
                        bundle.putSerializable("opponent_user", opponent_user)
                        dialog.arguments = bundle
                        dialog.show(supportFragmentManager, "NewMatchingDialog")
                        check =1
                        break
                    }
                }
            }
            if(check ==0) {
                Toast.makeText(this@NewMatchingActivity, "No matching", Toast.LENGTH_LONG)
                    .show()
            }else{
                check = 0
            }
        }


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
                    chatRooms.clear()
                    WhoAmiFrendWith.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<ChatRoom>()
                        if (item != null) {
                            chatRooms.add(item)
                            if(item.users?.contains(userData.key) == true){//userData를 갖고 있으면
                                item.users[userData.key]?.let { WhoAmiFrendWith.add(it) }
                            }
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
                NedUser.clear()
                korUser.clear()
                for (t in temp.children) {
                    if(t.getValue<User>()?.country =="Netherlands"){
                        NedUser.add(t.getValue<User>()!!)
                    }
                    else{
                        korUser.add(t.getValue<User>()!!)
                    }
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
    fun setupAllUserList() {        //전체 사용자 목록 불러오기
        val myUid = FirebaseAuth.getInstance().currentUser?.uid.toString()        //현재 사용자 아이디
        //Log.d("userid..setupAlluserList",myUid)
        val users = database.getReference("User")
        users.child("users")   //사용자 데이터 요청
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                }
                override fun onDataChange(snapshot: DataSnapshot) {
                    allUsers.clear()
                    for (data in snapshot.children) {
                        val item = data.getValue<User>()
                        allUsers.add(item!!)              //전체 사용자 목록에 추가
                    }

                }


            })
    }
    fun findUser(userUid: String) : User? {
        var finduser : User? = null
        user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
        val users = database.getReference("User")
        for (i in 0 until allUsers.size ){
            if(userUid == allUsers[i].uid){
                finduser = allUsers[i]
            }
        }
        return finduser
    }
}

