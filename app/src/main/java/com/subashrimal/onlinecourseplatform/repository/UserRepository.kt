package com.subashrimal.onlinecourseplatform.repository

import com.subashrimal.onlinecourseplatform.api.APIRequest
import com.subashrimal.onlinecourseplatform.api.ServiceBuilder
import com.subashrimal.onlinecourseplatform.api.UserAPI
import com.subashrimal.onlinecourseplatform.model.User
import com.subashrimal.onlinecourseplatform.response.SingleUserResponse
import com.subashrimal.onlinecourseplatform.response.UpdateProfileResponse
import com.subashrimal.onlinecourseplatform.response.UserResponse

class UserRepository : APIRequest() {

    private val userApi = ServiceBuilder.builderService(UserAPI::class.java)

    suspend fun registerUser(user: User): UserResponse {
        return  apiRequest {
            userApi.registerUser(user)
        }
    }
    suspend fun loginUser(username: String, password: String): UserResponse{
        return apiRequest {
            userApi.loginUser(username, password)
        }
    }

    suspend fun getSingleUser() : SingleUserResponse {
        return apiRequest {
            userApi.getSingleUser(ServiceBuilder.token!!)
        }
    }
    suspend fun updateUser(user: User) : UpdateProfileResponse {
        return apiRequest {
            userApi.UpdateUser(ServiceBuilder.token!!,user)
        }
    }
}