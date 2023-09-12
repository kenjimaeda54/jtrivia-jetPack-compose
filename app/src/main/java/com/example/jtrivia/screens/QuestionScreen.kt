package com.example.jtrivia.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jtrivia.model.QuestionItem
import com.example.jtrivia.utillity.AppColors
import com.example.jtrivia.viewModels.QuestionViewModel

@Composable
fun QuestionScreen(
    question:
    SnapshotStateList<QuestionItem>?
) {
    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        color = AppColors.mDarkPurple,

        ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = buildAnnotatedString {
                withStyle(style = ParagraphStyle(textIndent = TextIndent.None)) {
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = AppColors.mLightGray
                        )
                    ) {
                        append("Question 10/")
                    }
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Light,
                            fontSize = 15.sp,
                            color = AppColors.mLightGray
                        )
                    ) {
                        append("100")
                    }
                }
            })
            //Qunado preciso desenhar algo que nao possui no compose eu uso o canvas
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(top = 20.dp)) {
                //color e obrigatorio se nao ira entender que drawline e uma funcao e nao compose
                drawLine(
                    start = Offset(0f, 0f),
                    color = AppColors.mLightGray,
                    end = Offset(size.width, 0f),
                    //vai ser 10 on 10 off
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
            }
        }

    }

}


//preview nao funciona com estados assincronos
@Preview
@Composable
fun QuestionPreview() {
    QuestionScreen(question = null)
}