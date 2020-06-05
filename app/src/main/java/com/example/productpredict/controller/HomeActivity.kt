package com.example.productpredict.controller

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productpredict.R
import com.example.productpredict.controller.adapter.TestAdapter
import com.example.productpredict.model.MyPreference
import com.example.productpredict.model.Plot
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val plotsList = ArrayList<Plot>()
        plotsList.add(Plot("1", "11111"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))
        plotsList.add(Plot("2", "22222"))

        recyclePlot1.setHasFixedSize(true)
        recyclePlot1.layoutManager = LinearLayoutManager(this)
        recyclePlot1.adapter = TestAdapter(plotsList)

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

        search_btn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        selectKind_BTN.setOnClickListener {
            val builder = AlertDialog.Builder(this, R.style.AlertDialog)
            builder.setTitle("เลือกประเภทผลผลิต")
            builder.setIcon(R.drawable.ic_playlist_add_check_black_24dp)
            val kindArray = arrayOf("ไม้หนึ่ง", "ไม้รวม", "ไม้วีเนียร์")
            val checkItem = booleanArrayOf(false, false, false)

            builder.setMultiChoiceItems(kindArray, checkItem) { dialog, which, isChecked->
                checkItem[which] = isChecked
            }

            builder.setPositiveButton("เลือก") { dialog, which ->
                productKind_TV.text = ""
                for (i in kindArray.indices){
                    if (checkItem[i]){
                        productKind_TV.text = "${productKind_TV.text} ${kindArray[i]}"
                    }
                }
            }

            builder.setNegativeButton("ยกเลิก") { dialog: DialogInterface?, which: Int -> dialog?.cancel() }
            val dialog : AlertDialog = builder.create()
            dialog.show()
        }
    }


}
