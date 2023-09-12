package com.example.jtrivia.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jtrivia.viewModels.QuestionViewModel


//o preview nao funciona com requisicoes http por isso o fragment
@Composable
fun QuestionFragment(questionViewModel: QuestionViewModel = viewModel()) {
    val question = questionViewModel.data.value.data?.toMutableStateList()
    QuestionScreen(question = question)
}