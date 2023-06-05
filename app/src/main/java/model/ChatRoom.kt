package model

import java.io.Serializable


data class ChatRoom(
    val users: Map<String, String>? = HashMap(),
    var messages: Map<String, Message>? = HashMap(),
    var missionlv1 : MissionLv1? = null,
    var missionlv2: MissionLv2? = null,
    var missionStatus : Int = 0,
    var missionCount : Int =0
) : Serializable{
    constructor():this(HashMap(), HashMap(), null, null, 0, 0)
}
