package com.subashrimal.onlinecourseplatform.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int=0,
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
)

annotation class Entity
