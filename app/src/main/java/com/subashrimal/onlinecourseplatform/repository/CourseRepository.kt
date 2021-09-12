package com.subashrimal.onlinecourseplatform.repository

import com.subashrimal.onlinecourseplatform.api.APIRequest
import com.subashrimal.onlinecourseplatform.api.CourseAPI
import com.subashrimal.onlinecourseplatform.api.ServiceBuilder
import com.subashrimal.onlinecourseplatform.model.Course
import com.subashrimal.onlinecourseplatform.response.AddCourseResponse
import com.subashrimal.onlinecourseplatform.response.CourseGetResponse
import com.subashrimal.onlinecourseplatform.response.DeleteCourseResponse
import okhttp3.MultipartBody

class CourseRepository : APIRequest() {
    private val courseAPI = ServiceBuilder.builderService(CourseAPI::class.java)

    suspend fun addCourse(course: Course): AddCourseResponse {
        return apiRequest {
            courseAPI.addCourse(course, ServiceBuilder.token!!)
        }
    }

//    suspend fun showCourse(): CourseGetResponse {
//        return apiRequest {
//            courseAPI.showCourse(ServiceBuilder.token!!)
//        }
//    }

    suspend fun getAllCourse(): CourseGetResponse {
        return apiRequest {
            courseAPI.showCourse(
                ServiceBuilder.token!!
            )
        }
    }

    suspend fun deleteStudent(id: String): DeleteCourseResponse {
        return apiRequest {
            courseAPI.deleteCourse(ServiceBuilder.token!!,id)
        }
    }

    suspend fun uploadImage(id: String, body: MultipartBody.Part)
            : DeleteCourseResponse {
        return apiRequest {
            courseAPI.uploadImage(ServiceBuilder.token!!, id, body)
        }
    }

}