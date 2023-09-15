package com.example.jtrivia.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jtrivia.mock.mockQuestions
import com.example.jtrivia.model.QuestionItem
import com.example.jtrivia.utillity.AppColors
import com.example.jtrivia.views.ButtonWithGradient
import com.example.jtrivia.views.RowChoice
import java.util.UUID

@Composable
fun QuestionScreen(
    questions: List<QuestionItem>
) {
    //colocando algo no construtor do remember estamos garantindo propriedade computada
    //ou seja apenas quando realmente exister questions que iremos atuar ja que e assincrono
    val radioSelected = remember(questions) {
        mutableStateOf<String?>(null)
    }

    val questionIndexSelected = remember(questions) {
        mutableStateOf(0)
    }

    val isCorrectAnswer = remember(questions) {
        mutableStateOf<Boolean?>(null)
    }

    val score = remember(questions) {
        mutableStateOf<Int>(0)
    }


    fun handleChoiceSelected(choice: String) {
        radioSelected.value = choice
        isCorrectAnswer.value =
            choice == questions[questionIndexSelected.value].answer

        if (choice == questions[questionIndexSelected.value].answer) {
            score.value = score.value + 10
        }
        if (score.value > 1 && choice != questions[questionIndexSelected.value].answer) {
            score.value = score.value - 10
        }
    }
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple,

        ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {

            if (questionIndexSelected.value > 7) {
                Row(
                    modifier = Modifier
                        .padding(3.dp)
                        .fillMaxWidth()
                        .height(45.dp)
                        .border(
                            width = 3.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    AppColors.mLightGray,
                                    AppColors.mLightGray,
                                )
                            ),
                            shape = RoundedCornerShape(34.dp) //esse sera o circulo do corner
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomStartPercent = 50,
                                bottomEndPercent = 50
                            )
                        ), //preciso do clip e o valor interno pra cortar e encaixar perfeito a cor do graident
                    verticalAlignment = Alignment.CenterVertically,

                    ) {

                    Button(
                        contentPadding = PaddingValues(1.dp),
                        modifier = Modifier
                            .fillMaxWidth(questionIndexSelected.value * 0.005f)
                            .background(
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color(0xFFF95075),
                                        Color(0xFFBE6BE5)
                                    )
                                ),

                                ),
                        enabled = false,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent
                        ),
                        onClick = { /*TODO*/ }) {
                            Text(
                                text = "${score.value}", style = TextStyle(
                                    fontSize = 12.sp,
                                    color = AppColors.mLightGray
                                ),
                                textAlign = TextAlign.End,
                                overflow = TextOverflow.Visible,
                                softWrap = false
                            )
                        }
                }
            }

            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = buildAnnotatedString {
                    withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp,
                                color = AppColors.mLightGray
                            )
                        ) {
                            append("Question ${questionIndexSelected.value}/")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Light,
                                fontSize = 15.sp,
                                color = AppColors.mLightGray
                            )
                        ) {
                            append("${questions.size}")
                        }
                    }
                })
            //Qunado preciso desenhar algo que nao possui no compose eu uso o canvas
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .padding(top = 20.dp)
            ) {
                //color e obrigatorio se nao ira entender que drawline e uma funcao e nao compose
                drawLine(
                    start = Offset(0f, 0f),
                    color = AppColors.mLightGray,
                    end = Offset(size.width, 0f),
                    //vai ser 10 on 10 off
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
            Spacer(modifier = Modifier.padding(vertical = 15.dp))
            RowChoice(question = questions[questionIndexSelected.value], content = { choice ->
                RadioButton(
                    colors = RadioButtonDefaults.colors(
                        unselectedColor = AppColors.mLightGray,
                        selectedColor = if (radioSelected.value == questions[questionIndexSelected.value].answer
                        ) AppColors.green else AppColors.red
                    ),
                    selected = radioSelected.value == choice,
                    onClick = { handleChoiceSelected(choice) }

                )
            }, choiceSelected = radioSelected.value)
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.mLightBlue
                    ),
                    shape = RoundedCornerShape(10.dp), onClick = {
                        questionIndexSelected.value += 1
                    }) {
                    Text(
                        text = "Next", style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            color = AppColors.mOffWhite,
                            fontSize = 18.sp
                        )
                    )
                }
            }


        }
    }

}


//preview nao funciona com estados assincronos
@Preview
@Composable
fun QuestionPreview() {
    QuestionScreen(questions = mockQuestions)
}