package com.subashrimal.onlinecourseplatform.api

import com.subashrimal.onlinecourseplatform.model.Course
import com.subashrimal.onlinecourseplatform.response.AddCourseResponse
import com.subashrimal.onlinecourseplatform.response.CourseGetResponse
import com.subashrimal.onlinecourseplatform.response.DeleteCourseResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface CourseAPI {

    @POST("student/")
    suspend fun addCourse(
        @Body course: Course,
        @Header("Authorization") token: String
    ): Response<AddCourseResponse>

    @GET("student/")
    suspend fun showCourse(
        @Header("Authorization") token: String
    ): Response<CourseGetResponse>

    @DELETE("student/{id}")
    suspend fun deleteCourse(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<DeleteCourseResponse>

    @Multipart
    @PUT("student/{id}/photo")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part file: MultipartBody.Part
    ): Response<DeleteCourseResponse>

}