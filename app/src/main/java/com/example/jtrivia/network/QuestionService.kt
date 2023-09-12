package com.example.jtrivia.network

import com.example.jtrivia.model.Question
import retrofit2.http.GET

interface QuestionService {
    @GET("world.json")
    suspend fun getQuestions(): Question
}