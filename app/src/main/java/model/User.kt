package model

import java.io.Serializable

data class User(
    val uid: String? = "",
    val nickname: String? = "",
    val email: String? = "",
    val country: String? = "",
    val city: String? = ""
) : Serializable