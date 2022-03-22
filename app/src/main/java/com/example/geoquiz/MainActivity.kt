package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import kotlin.math.floor

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    /*Т.к. объекты не появляются в памяти до тех пор, пока в onCreate не будет вызвана setContentView()
    используем lateinit чтобы указать компилятору, что будет введено ненулевое значение View
    перед попыткой использоать содержимое свойства*/
    private lateinit var trueButton: Button //Кнопка Верного ответа
    private lateinit var falseButton: Button //Кнопка неверного ответа
    private lateinit var nextButton: ImageButton //Кнопка-картинка "Далее"
    private lateinit var backButton: ImageButton //Кнопка-картинка "Назад"
    private lateinit var questionTextView: TextView //Поле для вывода вопроса
    private lateinit var annotationTextView: TextView //Поле для вывода объяснения ответа
    private lateinit var resultTextView: TextView //Поле для вывода результата игры

    //Создаем базу вопросов с ответами
    private val questionBank = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, true),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, true),
        Question(R.string.question_6, false),
        Question(R.string.question_7, true),
        Question(R.string.question_8, false),
        Question(R.string.question_9, false),
        Question(R.string.question_10, true),
    )
    //Создаем базу пояснений к ответам
    private val annotationBank = listOf(
        R.string.annotation_1,
        R.string.annotation_2,
        R.string.annotation_3,
        R.string.annotation_4,
        R.string.annotation_5,
        R.string.annotation_6,
        R.string.annotation_7,
        R.string.annotation_8,
        R.string.annotation_9,
        R.string.annotation_10
    )
    private val questionRes = arrayOfNulls<Int>(questionBank.size)  //Сюда пишем вопросы, на которые уже давали ответ
    private val resultAnswers = arrayOfNulls<Int>(questionBank.size) //Сюда пишем результат ответа 1 или 0
    private var currentIndex = 0 //Номер текущего вопроса
    private var resultAllAnswer: Float = 0.0F //Переменная для вычисления результата игры
    private var checkAnswerCount = 0 //На сколько вопросов уже дан ответ
    private var countTrue = 0 //Итоговое количество верных ответов
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Log.d(TAG, "onCreate(Bundle?) called")
        //Log.i(TAG, "Всего вопросов: " + (questionBank.size).toString())
        setContentView(R.layout.activity_main)



        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)
        annotationTextView = findViewById(R.id.annotation_text_view)
        resultTextView = findViewById(R.id.result_text_view)

        backButton.isVisible = currentIndex != 0 //Показать если currentIndex !=0

        trueButton.setOnClickListener { view: View ->
            checkAnswer(true)
        }

        falseButton.setOnClickListener { view: View ->
            checkAnswer(false)
        }

        nextButton.setOnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        backButton.setOnClickListener { view: View ->
            currentIndex = (currentIndex - 1) % questionBank.size
            updateQuestion()
        }
        questionTextView.setOnClickListener { view: View ->
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }


    //Функция обновления вопроса
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId) //Выводим текст вопроса
        backButton.isVisible = currentIndex != 0 //Показать кнопку назад, если это первый вопрос
        nextButton.isVisible = currentIndex != (questionBank.size - 1) //Скрыть кнопку "Далее", если это последний вопрос

        //Log.i(TAG, "Текущий вопрос : " + (currentIndex).toString())
        if (questionRes[currentIndex] == 1) {
            //Блокируем кнопки ответа на текущий вопрос, если на него уже отвечали
            trueButton.isEnabled = false
            falseButton.isEnabled = false
            //Показываем комментарий если на вопрос уже был дан ответ
            annotationTextView.setText(annotationBank[currentIndex])
            annotationTextView.isVisible = true
        } else {
            annotationTextView.isVisible = false
            //Разблокируем кнопки ответа на текущий вопрос, если они были заблокированы
            trueButton.isEnabled = true
            falseButton.isEnabled = true
        }
    }

    //Функция проверки ответов
    private fun checkAnswer(userAnswer: Boolean) {
        checkAnswerCount += 1; //счетчик отвеченных вопросов
        //Блокируем кнопки ответа на текущий вопрос
        trueButton.isEnabled = false
        falseButton.isEnabled = false

        val correctAnswer = questionBank[currentIndex].answer //Выбираем из базы ответ на вопрос
        questionRes[currentIndex] = 1 //Записываем факт ответа на вопрос
        //проверяем ответ
        val messageResId = if (userAnswer == correctAnswer) {
            //Ответ верный
            resultAnswers[currentIndex] = 1 //Записываем верный ответ в базу верных ответов
            R.string.correct_toast //В MessageId записываем текст уведомления что ответ верен
        } else {
            R.string.incorrect_toast //В MessageId записываем текст уведомления что ответ не верен
        }
        annotationTextView.setText(annotationBank[currentIndex]) //Выводим описание ответа
        annotationTextView.isVisible = true //Делаем описание ответа видимым
        val toastDuration = Toast.LENGTH_SHORT //Время показа сообщения
        val toast = Toast.makeText(this, messageResId, toastDuration)
        toast.setGravity(Gravity.TOP, 0, 0) //Работает не во всех сборках
        toast.show() //Показываем уведомление
        //Если на все вопросы были даны ответы, считаем итог
        if (checkAnswerCount == questionBank.size) {
            backButton.isVisible=false
            for (i in resultAnswers) { //Перебираем массив с ответами
                if (i == 1) {
                    countTrue += 1//Если ответ верен, добавляем его в копилку верных ответов
                }
            }
            //Вычисляем процент верных ответов от общего числа вопросов
            resultAllAnswer =
                ((questionBank.size).toFloat() / 100 * countTrue.toFloat()).toFloat() * 100
            //Выводим результат игры
            resultTextView.setText("ИТОГО " + (resultAllAnswer.toInt()).toString() + "% верных ответов")
        }
    }
}

