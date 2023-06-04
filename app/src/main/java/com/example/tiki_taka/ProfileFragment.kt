package com.example.tiki_taka

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.tiki_taka.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        init()
        return binding.root
    }
    fun init() {
        binding.avatar1.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar1)

        }
        binding.avatar2.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar2)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar3.setOnClickListener {
            binding.saveBtn.text = "PURCHASE"
            binding.avatar.setImageResource(R.drawable.avatar3)
        }
        binding.avatar4.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar4)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar5.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar10)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar6.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar9)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar7.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar7)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.avatar8.setOnClickListener {
            binding.avatar.setImageResource(R.drawable.avatar8)
            binding.saveBtn.text = "PURCHASE"
        }
        binding.saveBtn.setOnClickListener {
            val mDialogView = LayoutInflater.from(requireContext()).inflate(R.layout.purchase, null)
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialogView)

            val mAlertDialog = mBuilder.show()

            val okButton = mDialogView.findViewById<Button>(R.id.purchaseButton)
            okButton.setOnClickListener {
                mAlertDialog.dismiss()
                binding.saveBtn.text = "SAVE"
                binding.price8.text = "∨"
                binding.coin.text = "900TC"
            }

            val noButton = mDialogView.findViewById<Button>(R.id.closeButton)
            noButton.setOnClickListener {
                mAlertDialog.dismiss()
            }
        }
    }
    }