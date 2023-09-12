package com.example.jtrivia.repository

import android.util.Log
import com.example.jtrivia.data.DataOrExpection
import com.example.jtrivia.model.Question
import com.example.jtrivia.model.QuestionItem
import com.example.jtrivia.network.QuestionService
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val questionService: QuestionService) {
    //tem que ser o arrayList porque ele 'e um data classe e nao o question
    private val dataOrExpection = DataOrExpection<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getQuestions(): DataOrExpection<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrExpection.loading = true
            dataOrExpection.data = questionService.getQuestions()
            if (dataOrExpection.data.toString().isNotEmpty()) dataOrExpection.loading = false

        } catch (expection: Exception) {
            dataOrExpection.e = expection
            Log.d("ErrorApi", "${dataOrExpection.e}")
        }
        return dataOrExpection
    }


}