package model

import java.io.Serializable

data class Message(
    var senderUid: String = "",
    var sendDate: String = "",
    var content: String = "",
    var confirmed: Boolean = false
) : Serializable