package model

import java.io.Serializable

data class ChatRoom(
    val users: Map<String, String>? = HashMap(),
    var messages: Map<String, Message>? = HashMap()
) : Serializable
