package com.example.jtrivia.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jtrivia.data.DataOrExpection
import com.example.jtrivia.model.Question
import com.example.jtrivia.model.QuestionItem
import com.example.jtrivia.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {
     var data: MutableState<DataOrExpection<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(
            DataOrExpection(null, true, Exception(""))
        )

    init {
        getAllQuestion()
    }

    private fun getAllQuestion() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getQuestions()
            if(data.value.toString().isNotEmpty()) data.value.loading = false
        }
    }


}