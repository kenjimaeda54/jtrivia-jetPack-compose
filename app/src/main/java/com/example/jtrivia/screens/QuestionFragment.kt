package com.example.jtrivia.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jtrivia.mock.mockQuestions
import com.example.jtrivia.utillity.AppColors
import com.example.jtrivia.viewModels.QuestionViewModel


//o preview nao funciona com requisicoes http por isso o fragment
@Composable
fun QuestionFragment(questionViewModel: QuestionViewModel = viewModel()) {
    val questions = questionViewModel.data.value.data?.toMutableStateList()
    if (questionViewModel.data.value.loading == true) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(AppColors.mDarkPurple),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator()
        }

    } else {
        if (questions != null) {
            QuestionScreen(questions = questions.toMutableStateList())
        }
    }
}