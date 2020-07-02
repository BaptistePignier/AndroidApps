package com.example.counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun ajouter(view: View) {
        var contenu = textView.text.toString().toInt()
        contenu += 1

        textView.text = contenu.toString()
    }

    fun diminuer(view: View) {
        var contenu = textView.text.toString().toInt()
        contenu -= 1

        textView.text = contenu.toString()
    }
}