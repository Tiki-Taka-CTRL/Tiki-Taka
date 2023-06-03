package com.example.tiki_taka

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import model.MissionLv1

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

//        val database = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("Mission")
//        val mi1 = MissionLv1(1, null, "test1")
//        val mi2 = MissionLv1(1, null, "test2")
//        val mi3 = MissionLv1(1, null, "test3")
//        val mi4 = MissionLv1(1, null, "test4")
//        val mi5 = MissionLv1(1, null, "test5")
//        database.child("lv1").child(mi1.title).setValue(mi1)
//        database.child("lv1").child(mi2.title).setValue(mi2)
//        database.child("lv1").child(mi3.title).setValue(mi3)
//        database.child("lv1").child(mi4.title).setValue(mi4)
//        database.child("lv1").child(mi5.title).setValue(mi5)
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)

    }
}