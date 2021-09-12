package com.subashrimal.onlinecourseplatform.response

import com.subashrimal.onlinecourseplatform.model.Course

data class AddCourseResponse (
    val success: Boolean? = null,
    val data: MutableList<Course>? =null,
)