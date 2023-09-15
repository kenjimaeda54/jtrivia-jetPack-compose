# JTrivia
Aplicativo de perguntas e respostas, são aproximadamente 4875 perguntas e possível também observar seus pontos a traves de um barra de progresso que aparece após a sétima pergunta


## Feature
- [Referencia API](https://raw.githubusercontent.com/itmmckernan/triviaJSON/master/world.json)
- Novamente usei arquitetura limpa recomendada pelo time do Google
- Para consumir dados web foi usado [Retrofit](https://square.github.io/retrofit/)
- Logica de implementação dos dados que retornam no Retrofit ficou na camada Repository
- Criei uma camada de abstração de todo metada da web, o nome era DataOrExpection
- Repara que para esse caso de uso o MutableState e o suficiente, não precisamos usar um Live Data ou Flow

```kotlin


//DataOrExpection
data class DataOrExpection<T,Boll,Exception>(
    var data: T? = null,
    var loading: Boll? = null,
    var e: Exception? = null
)


//NetWork
class QuestionRepository @Inject constructor(private val questionService: QuestionService) {

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

//no view model
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

```

## 
- Para criar uma customizada barra de progresso podemos usar Row é Button
- Precisa utilizar a propriedade clip e paddingValues do Button como exemplo abaixo
- Para gradiente do botão encaixa  perfeitamente, precisa que o clip assume 50 por cento do Row e no border o shape e para borda

```kotlin
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
                            shape = RoundedCornerShape(34.dp)
                        )
                        .clip(
                            RoundedCornerShape(
                                topStartPercent = 50,
                                topEndPercent = 50,
                                bottomStartPercent = 50,
                                bottomEndPercent = 50
                            )
                        ), 
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



```
##
- Compose não permite trabalhar Column com o LazyColumn juntos, para isto pode usar o forEach ou caso precise do índex forEachIndexed
- Para passar um componente como filho pode simplesmente declarar uma função do tipo composable, inclusive passar parâmetros como abaixo


```kotlin
@Composable
fun RowChoice(
    question: QuestionItem,
    content: @Composable() (String) -> Unit,
    choiceSelected: String?
) {

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

// quem implementa
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



```
## 
- Pode usar o remember como propriedade computada, algo parecido no swift, caso tenhamos uma variável que precisa ser criada após a outra existir
- No exemplo baixo radioSelected esta aguardando questions  esta disponível para  ser instanciada, evitamos assim crash no App

```kotlin
 val radioSelected = remember(questions) {
        mutableStateOf<String?>(null)
    }

```

