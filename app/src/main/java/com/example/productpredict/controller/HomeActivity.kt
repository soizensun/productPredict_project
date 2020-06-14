package com.example.productpredict.controller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import com.example.productpredict.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val myPreference = MyPreference(this)

        val selectPlot = intent.getStringExtra("selectPlot")
        if (selectPlot != null)  plotName_TV.text = selectPlot
        else {
            // use sharePreference
//            if (myPreference.getCurrentPlotPreference() == "") plotName_TV.text = "เลือกแปลง"
//            else plotName_TV.text = myPreference.getCurrentPlotPreference()

            // don't use sharePreference
            plotName_TV.text = "เลือกแปลง"
        }

        addField_BTN.setOnClickListener{
            val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.a_product_input_listitem, null)
            parent_linear_layout.addView(rowView, parent_linear_layout.childCount - 1)
        }

        plotName_TV.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        search_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        calulate_BTN.setOnClickListener {
            val childCount = parent_linear_layout.childCount

            for (i in 0 until childCount - 1){
                val thisChild = parent_linear_layout.getChildAt(i)

                val dbh_base_start_value = thisChild.findViewById<EditText>(R.id.dbh_base_startTV).text.toString()
                val dbh_base_end_value = thisChild.findViewById<EditText>(R.id.dbh_base_endTV).text.toString()

                val dbh_end_start_value = thisChild.findViewById<EditText>(R.id.dbh_end_startTV).text.toString()
                val dbh_end_end_value = thisChild.findViewById<EditText>(R.id.dbh_end_endTV).text.toString()

                val length_start_value = thisChild.findViewById<EditText>(R.id.length_startTV).text.toString()
                val length_end_value = thisChild.findViewById<EditText>(R.id.length_endTV).text.toString()

                if (dbh_base_start_value == ""){
                    thisChild.setBackgroundResource(R.drawable.shape_softpink_error_bg)
                }

                Log.i("fffff", dbh_base_start_value)
            }

        }

    }

}
