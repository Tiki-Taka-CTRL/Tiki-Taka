package model

import java.io.Serializable

data class User(
    val uid: String? = "",
    val nickname: String? = "",
    val email: String? = "",
    val country: String? = "",
    val city: String? = "",
    var img: Int = 0,
    var coin: Int = 1000,
) : Serializable