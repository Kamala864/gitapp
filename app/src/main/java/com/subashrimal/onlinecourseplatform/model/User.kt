package com.subashrimal.onlinecourseplatform.model

data class User (
    val _id: String = "",
    val username: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phone_num: String? = null,
    val profilePic: String? = null
)