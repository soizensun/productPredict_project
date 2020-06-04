package com.example.productpredict.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.example.productpredict.R
import kotlinx.android.synthetic.main.activity_home.*

class home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        search_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}
