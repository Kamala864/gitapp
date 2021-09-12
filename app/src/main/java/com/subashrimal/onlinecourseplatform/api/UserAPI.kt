package com.subashrimal.onlinecourseplatform.api

import com.subashrimal.onlinecourseplatform.model.User
import com.subashrimal.onlinecourseplatform.response.SingleUserResponse
import com.subashrimal.onlinecourseplatform.response.UpdateProfileResponse
import com.subashrimal.onlinecourseplatform.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {

    @POST("auth/register")
    suspend fun registerUser(
        @Body user: User
    ): Response<UserResponse>


    @FormUrlEncoded
    @POST("auth/login")
    suspend fun loginUser(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<UserResponse>

    @GET("auth/me")
    suspend fun getSingleUser(
        @Header("Authorization") token : String
    ) : Response<SingleUserResponse>
    @PUT("auth/updateProfile")


    suspend fun UpdateUser(
        @Header("Authorization") token : String,
        @Body user : User
    ) : Response<UpdateProfileResponse>
}