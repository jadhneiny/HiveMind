package com.example.hivemind.network

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import com.example.hivemind.models.*

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>


    @POST("chats/")
    @FormUrlEncoded
    fun createChat(
        @Field("tutor_id") tutorId: Int,
        @Field("student_id") studentId: Int
    ): Call<Map<String, Any>>

    @POST("messages/")
    @FormUrlEncoded
    fun sendMessage(
        @Field("chat_id") chatId: Int,
        @Field("sender_id") senderId: Int,
        @Field("content") content: String
    ): Call<Map<String, Any>>

    @GET("chats/{user_id}")
    suspend fun getUserChats(@Path("user_id") userId: Int): Response<List<Chat>>

    @POST("/login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Response<LoginResponse>

    @GET("courses")
    suspend fun getCourses(): Response<List<Course>>

    @GET("courses/{course_name}/tutors")
    fun getTutorsByCourse(@Path("course_name") courseName: String): Call<List<User>>

    @GET("tutors")
    suspend fun getTutors(): Response<List<User>>

    @GET("tutors/{name}")
    suspend fun getTutorByName(@Path("name") name: String): Response<User>

    @GET("tutors/{id}")
    suspend fun getTutorById(@Path("id") tutorId: Int): Response<User>

    @GET("chats/{chatId}/messages")
    suspend fun getMessagesForChat(@Path("chatId") chatId: Int): Response<List<Message>>

    @POST("messages/")
    suspend fun sendMessage(@Body message: Message): Response<Message>
}

