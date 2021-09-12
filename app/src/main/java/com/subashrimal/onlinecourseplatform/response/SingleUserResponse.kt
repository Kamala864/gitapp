package com.subashrimal.onlinecourseplatform.response

import com.subashrimal.onlinecourseplatform.model.User

data class SingleUserResponse(
    var data : User? = null,
    var success : Boolean? = null,
)
