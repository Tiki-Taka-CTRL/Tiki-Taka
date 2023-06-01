package com.example.tiki_taka

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Switch
import androidx.fragment.app.DialogFragment


class MissionDialog : DialogFragment() {
    private lateinit var switchButton: Switch
    private lateinit var checkbox1 : CheckBox
    private lateinit var checkbox2 : CheckBox
    private lateinit var checkbox3 : CheckBox
    private lateinit var checkbox4 : CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.dialog_mission, container, false)

        switchButton = view.findViewById(R.id.switch_button)
        switchButton.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                // 스위치 버튼이 체크된 상태일 때의 동작
                //switchButton.thumbDrawable=resources.getDrawable(R.drawable.switch_thumb_green)
                //switchButton.trackDrawable = resources.getDrawable(R.drawable.switch_track_green)
            } else {
                // 스위치 버튼이 체크되지 않은 상태일 때의 동작
                //switchButton.thumbDrawable=resources.getDrawable(R.drawable.switch_thumb_gray)
                //switchButton.trackDrawable = resources.getDrawable(R.drawable.switch_track_gray)
            }
        }

        checkbox1 = view.findViewById(R.id.checkbox1)
        checkbox2 = view.findViewById(R.id.checkbox2)
        checkbox3 = view.findViewById(R.id.checkbox3)
        checkbox4 = view.findViewById(R.id.checkbox4)

        checkbox1.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox1.setBackgroundColor(Color.BLACK)
                //checkbox1.setTextColor(Color.WHITE)
            } else {
                checkbox1.setBackgroundColor(Color.WHITE)
                //checkbox1.setTextColor(Color.BLACK)
            }
        }

        checkbox2.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox2.setBackgroundColor(Color.BLACK)
                //checkbox2.setTextColor(Color.WHITE)
            } else {
                checkbox2.setBackgroundColor(Color.WHITE)
                //checkbox2.setTextColor(Color.BLACK)
            }
        }

        checkbox3.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox3.setBackgroundColor(Color.BLACK)
                //checkbox3.setTextColor(Color.WHITE)
            } else {
                checkbox3.setBackgroundColor(Color.WHITE)
                //checkbox3.setTextColor(Color.BLACK)
            }
        }

        checkbox4.setOnCheckedChangeListener{_, isChecked->
            if (isChecked) {
                checkbox4.setBackgroundColor(Color.BLACK)
                //checkbox4.setTextColor(Color.WHITE)
            } else {
                checkbox4.setBackgroundColor(Color.WHITE)
                //checkbox4.setTextColor(Color.BLACK)
            }
        }

        return view
    }
}