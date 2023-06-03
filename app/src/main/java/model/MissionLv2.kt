package model

import java.io.Serializable

data class MissionLv2(
    val ans1: String = "",
    val ans2: String = "",
    val ans3: String = "",
    val ans4: String = "",
    val ans_num: Int = -1,
    val level: Int,
    override val title: String,

) : Serializable, Mission
