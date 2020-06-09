package com.example.productpredict.controller

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productpredict.R
import com.example.productpredict.controller.adapter.ProductInputAdapter
import com.example.productpredict.model.MyPreference
import com.example.productpredict.model.Plot
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    var count = 0
    val listItem = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        listItem.add(count)

        val testList = ArrayList<Int>()
        testList.add(0)
        testList.add(2)
        testList.add(1)

//        val plotsList = ArrayList<Plot>()
//        plotsList.add(Plot("1", "11111"))
//        plotsList.add(Plot("2", "22222"))
//        plotsList.add(Plot("2", "22222"))

//        recyclePlot1.setHasFixedSize(true)
//        recyclePlot1.layoutManager = LinearLayoutManager(this)
//        recyclePlot1.adapter = ProductInputAdapter(listItem, testList)

        val myPreference = MyPreference(this)

        val selectPlot = intent.getStringExtra("selectPlot")
        if (selectPlot != null)  plotName_TV.text = selectPlot
        else {
            // use sharePreference
//            if (myPreference.getCurrentPlotPreference() == "") plotName_TV.text = "เลือกแปลง"
//            else plotName_TV.text = myPreference.getCurrentPlotPreference()

            // don't use sharePreference
            plotName_TV.text = "เลือกแปลง"
        }

        addField_BTN1.setOnClickListener{
            val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var rowView = inflater.inflate(R.layout.a_product_input_listitem, null)
            parent_linear_layout.addView(rowView, parent_linear_layout.getChildCount() - 1)
        }



        plotName_TV.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        search_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

//    fun addNewListItem(){
//        count += 1
//        listItem.add(count)
//        recyclePlot1.adapter?.notifyDataSetChanged()
//    }



}
