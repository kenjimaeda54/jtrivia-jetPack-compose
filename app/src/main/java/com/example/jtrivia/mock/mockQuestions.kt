package com.example.jtrivia.mock

import com.example.jtrivia.model.QuestionItem
import com.example.jtrivia.viewModels.QuestionViewModel


val mockQuestions = listOf<QuestionItem>(
    QuestionItem(
        answer = "True",
        question = "LFD2 was banned in Australia.",
        category = "world",
        choices = listOf(
            "False",
            "True"
        )
    ),
    QuestionItem(
        answer = "Louisiana",
        question = "One of the remnants of this states former status as a possession of France is the fact that it was named after a French king.",
        category = "world",
        choices = listOf(
            "Oklahoma",
            "Florida",
            "Louisiana",
            "Georgia"
        )
    )
)