package com.example.tiki_taka

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import model.MissionLv1
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val database = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Mission")
//        val mi1 = MissionLv1(1, null, "Does he or she like pets?")
//        val mi2 = MissionLv1(1, null, "Does he or she play any sports?")
//        val mi3 = MissionLv1(1, null, "Does he or she like cooking?")
//        val mi4 = MissionLv1(1, null, "Does he or she know how to swim?")
//        val mi5 = MissionLv1(1, null, "Does he or she like traveling?")
//        val mi6 = MissionLv1(1, null, "Does he or she play any instrument?")
//        val mi7 = MissionLv1(1, null, "Does he or she like music?")
//        val mi8 = MissionLv1(1, null, "Does he or she like pineapple on pizza?")
//        val mi9 = MissionLv1(1, null, "Does he or she like Harry Potter?")
//        val mi10 = MissionLv1(1, null, "Does he or she like eating sweets?")
//        database.child("lv1").child(mi1.title).setValue(mi1)
//        database.child("lv1").child(mi2.title).setValue(mi2)
//        database.child("lv1").child(mi3.title).setValue(mi3)
//        database.child("lv1").child(mi4.title).setValue(mi4)
//        database.child("lv1").child(mi5.title).setValue(mi5)
//        database.child("lv1").child(mi6.title).setValue(mi6)
//        database.child("lv1").child(mi7.title).setValue(mi7)
//        database.child("lv1").child(mi8.title).setValue(mi8)
//        database.child("lv1").child(mi9.title).setValue(mi9)
//        database.child("lv1").child(mi10.title).setValue(mi10)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}