package model

import java.io.Serializable

data class Mission(
    val level: Int = -1,
    val title: String = "",
    val ans1: String = "",
    val ans2: String = "",
    val ans3: String = "",
    val ans4: String = "",
    val ans_num: Int = -1,
) : Serializable
