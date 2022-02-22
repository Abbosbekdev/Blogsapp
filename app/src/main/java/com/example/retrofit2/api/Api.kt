package com.example.retrofit2.api

import com.example.retrofit2.model.PostModel
import com.example.retrofit2.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path

interface Api {
    @Headers("app-id: 61ff66c4722f85f65e6d8d87")
    @GET("user")
    fun getUser():Call<BaseResponse<List<UserModel>>>

    @Headers("app-id: 61ff66c4722f85f65e6d8d87")
    @GET("post")
    fun getPost():Call<BaseResponse<List<PostModel>>>

    @Headers("app-id: 61ff66c4722f85f65e6d8d87")
    @GET("user/{user_id}/post")
    fun getPostByUser(@Path("user_id") id:String):Call<BaseResponse<List<PostModel>>>
}