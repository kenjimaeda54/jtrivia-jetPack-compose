package com.example.jtrivia.di

import com.example.jtrivia.network.QuestionService
import com.example.jtrivia.repository.QuestionRepository
import com.example.jtrivia.utillity.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun jtriviaQuestionRepository(questionService: QuestionService): QuestionRepository = QuestionRepository(questionService)

    @Singleton
    @Provides
    fun jtriviaQuestionService(): QuestionService = Retrofit.Builder().baseUrl(Constants.base_url)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(QuestionService::class.java)


}