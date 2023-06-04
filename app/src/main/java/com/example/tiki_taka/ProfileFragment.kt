package com.example.tiki_taka

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import model.User

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    var sel_price: TextView? = null
    var sel_avatar: Int = -1
    var coin: Int = 1000
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        init()
        initLayout()
        return binding.root
    }
    fun init() {
        sel_price = binding.price1
        sel_avatar = R.drawable.avatar1

        binding.avatar1.setOnClickListener {
            sel_price = binding.price1
            sel_avatar = R.drawable.avatar1
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar2.setOnClickListener {
            sel_price = binding.price2
            sel_avatar = R.drawable.avatar2
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar3.setOnClickListener {
            binding.saveBtn.text = "PURCHASE"
            sel_price = binding.price3
            sel_avatar = R.drawable.avatar3
            binding.avatar.setImageResource(sel_avatar)
        }
        binding.avatar4.setOnClickListener {
            sel_price = binding.price4
            sel_avatar = R.drawable.avatar4
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar5.setOnClickListener {
            sel_price = binding.price5
            sel_avatar = R.drawable.avatar5
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar6.setOnClickListener {
            sel_price = binding.price6
            sel_avatar = R.drawable.avatar6
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar7.setOnClickListener {
            sel_price = binding.price7
            sel_avatar = R.drawable.avatar7
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar8.setOnClickListener {
            sel_price = binding.price8
            sel_avatar = R.drawable.avatar8
            binding.avatar.setImageResource(sel_avatar)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.saveBtn.setOnClickListener {
            val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.purchase, null)
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.purchaseButton)
            val modalImg = mDialogView.findViewById<ImageView>(R.id.image)
            modalImg.setImageResource(sel_avatar)
            okButton.setOnClickListener {
                mAlertDialog.dismiss()
                binding.saveBtn.text = "SAVE"
                sel_price!!.text = "∨"
                coin -= 100
                binding.coin.text = coin.toString() + "TC"
                val user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
                val users = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
                users.child("users").child(user.uid).child("img").setValue(sel_avatar)
                users.child("users").child(user.uid).child("coin").setValue(coin)
            }

            val noButton = mDialogView.findViewById<Button>(R.id.closeButton)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }

    private fun initLayout() {
        val user = FirebaseAuth.getInstance().currentUser!!           //현재 로그인한 유저 id
        val users = Firebase.database("https://example-d2e1f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("User")
        val result = users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val temp = snapshot.child("users")
                var target: String
                for (t in temp.children) {
                    if (user.uid == t.child("uid").value) {
                        val cur_user = t.getValue<User>()!!
                        binding.avatar.setImageResource(cur_user.img)
                        setUserAvatarPrice(cur_user.img)
                        coin = cur_user.coin
                        binding.coin.text = coin.toString() + "TC"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setUserAvatarPrice(img: Int) {
        when(img){
            R.drawable.avatar1 -> binding.price1.text = "∨"
            R.drawable.avatar2 -> binding.price2.text = "∨"
            R.drawable.avatar3 -> binding.price3.text = "∨"
            R.drawable.avatar4 -> binding.price4.text = "∨"
            R.drawable.avatar5 -> binding.price5.text = "∨"
            R.drawable.avatar6 -> binding.price6.text = "∨"
            R.drawable.avatar7 -> binding.price7.text = "∨"
            R.drawable.avatar8 -> binding.price8.text = "∨"
            else -> binding.price1.text = "∨"
        }
    }
}