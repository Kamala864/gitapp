package com.subashrimal.onlinecourseplatform.response

import com.subashrimal.onlinecourseplatform.model.Course

data class CourseGetResponse (
    val success: Boolean? = null,
    val data: MutableList<Course>? =null,
)