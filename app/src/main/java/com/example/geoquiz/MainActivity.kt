package com.example.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

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

        trueButton.setOnClickListener{ view: View ->
            Toast.makeText(
                this, //MainActivity
                R.string.correct_toast, //Строка, которую надо отобразить
                Toast.LENGTH_SHORT) //как долго отображается уведомление
                .show()
        }

        falseButton.setOnClickListener{view: View->
            Toast.makeText(
                this,
                R.string.incorrect_toast,
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}