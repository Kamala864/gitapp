package com.subashrimal.onlinecourseplatform.response

import com.subashrimal.onlinecourseplatform.model.User

data class UpdateProfileResponse (
    val success : Boolean? = null,
    val message : String? = null,
    val data : User? = null,
)
