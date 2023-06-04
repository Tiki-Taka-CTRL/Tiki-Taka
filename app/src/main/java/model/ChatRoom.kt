package model

import java.io.Serializable

data class ChatRoom(
    val users: Map<String, String>? = HashMap(),
    var messages: Map<String, Message>? = HashMap(),
    var missionLv1 : MissionLv1? =null,
    var missionLv2: MissionLv2? = null,
    var missionStatus : Int = 0,
    var missionCount : Int =0
) : Serializable
