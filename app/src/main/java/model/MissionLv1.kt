package model

import java.io.Serializable

data class MissionLv1(
    val check_ans: Boolean = false,
    val level: Int,
    override val title: String,

) : Serializable, Mission
