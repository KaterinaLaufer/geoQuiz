package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlin.math.floor

class MainActivity : AppCompatActivity() {

    /*Т.к. объекты не появляются в памяти до тех пор, пока в onCreate не будет вызвана setContentView()
    используем lateinit чтобы указать компилятору, что будет введено ненулевое значение View
    перед попыткой использоать содержимое свойства*/
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)

        trueButton.setOnClickListener { view: View ->
            val toastText = R.string.correct_toast
            val toastDuration = Toast.LENGTH_SHORT //Время показа сообщения
            val toast = Toast.makeText(this, toastText, toastDuration)
            toast.setGravity(Gravity.TOP, 0, 0)//Работает не во всех сборках
            toast.show()
        }

        falseButton.setOnClickListener { view: View ->
            val toastText = R.string.incorrect_toast
            val toastDuration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this, toastText, toastDuration)
            toast.setGravity(Gravity.TOP,0,0)
            toast.show()
        }
    }
}