package com.example.jtrivia.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jtrivia.mock.mockQuestions
import com.example.jtrivia.model.QuestionItem
import com.example.jtrivia.utillity.AppColors

@Composable
fun RowChoice(
    question: QuestionItem,
    content: @Composable() (String) -> Unit,
    choiceSelected: String?
) {


    //para criar secoes podemos usar um item igual no react native
    //https://developer.android.com/jetpack/compose/lists?hl=pt-br
    //    LazyColumn {
    //        //item e para renderizar apenas um  seria tipo secao do react native
    //        item {
    //
    //        }
    //      items( aqui uma lista) {
    //      }
    //


    Column {

        Text(
            text = question.question,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 25.dp)
                .fillMaxHeight(0.2f),
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 21.sp,
                color = AppColors.mLightGray,
                fontWeight = FontWeight.Bold
            )
        )
        //estou usando for each porque nao podemos miturar columnas com lazycolumn


        question.choices.forEach {
            val color = when {
                choiceSelected == it && choiceSelected == question.answer -> AppColors.green
                choiceSelected != it -> AppColors.mOffWhite
                else -> {
                    AppColors.red
                }
            }

            val textBuilder = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color =  color,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Normal,
                    )
                ) {
                    append(it)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
                    .height(45.dp)
                    .background(Color.Transparent)
                    .border(
                        width = 4.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                AppColors.mLightGray,
                                AppColors.mLightGray
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    .clip(
                        RoundedCornerShape(
                            topStartPercent = 50,
                            topEndPercent = 50,
                            bottomEndPercent = 50,
                            bottomStartPercent = 50
                        )
                    ),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically

            ) {
                content(it)
                Text(textBuilder)

            }
        }
    }

}


@Composable
@Preview(showBackground = true)
fun RowQuestinPreview() {
    RowChoice(question = mockQuestions[0], content = {
        Text(text = "Question")
    }, choiceSelected = "")
}
