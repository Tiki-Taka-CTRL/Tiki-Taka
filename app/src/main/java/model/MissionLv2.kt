package model

import java.io.Serializable

data class MissionLv2(
    val level: Int,
    val ans1: String = "",
    val ans2: String = "",
    val ans3: String = "",
    val ans4: String = "",
    var ans_num: Int = -1,
    override val title: String,

    ) : Serializable, Mission{
    constructor():this(0, "", "", "", "", 0, "")
}
