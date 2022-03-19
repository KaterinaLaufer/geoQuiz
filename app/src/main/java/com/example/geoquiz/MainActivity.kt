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
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    /*Т.к. объекты не появляются в памяти до тех пор, пока в onCreate не будет вызвана setContentView()
    используем lateinit чтобы указать компилятору, что будет введено ненулевое значение View
    перед попыткой использоать содержимое свойства*/
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var backButton: ImageButton
    private lateinit var questionTextView: TextView

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
    private var currentIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        backButton = findViewById(R.id.back_button)
        questionTextView = findViewById(R.id.question_text_view)

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
        //ToDo Разобраться как быть с 1 вопросом
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

    //Функция обновления вопроса
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    //Функция проверки ответов
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        val toastText = R.string.correct_toast
        val toastDuration = Toast.LENGTH_SHORT //Время показа сообщения
        val toast = Toast.makeText(this, messageResId, toastDuration)
        toast.setGravity(Gravity.TOP, 0, 0) //Работает не во всех сборках
        toast.show()
    }
}