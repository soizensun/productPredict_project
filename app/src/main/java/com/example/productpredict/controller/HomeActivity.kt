package com.example.productpredict.controller

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.example.productpredict.R
import com.example.productpredict.model.ProductRequirement
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private var productRequirement = ProductRequirement()

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val myPreference = MyPreference(this)

        val typeListOption = arrayOf("ไม้หนึ่ง", "ไม้รวม", "ไม้วีเนียร์", "ไม้เสา")
        val selectPlotName = intent.getStringExtra("selectPlotName")
        val selectPlotID  = intent.getStringExtra("selectPlotID")

        if (selectPlotName != null)  plotName_TV.text = selectPlotName
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

        productKind_SP.adapter = ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, typeListOption)
        productKind_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.i("spinner", typeListOption.get(position))
            }
        }

        calulate_BTN.setOnClickListener {
            val childCount = parent_linear_layout.childCount

            for (i in 1 until childCount - 1){
                val thisChild = parent_linear_layout.getChildAt(i)

                val productKind_SP_value = thisChild.findViewById<Spinner>(R.id.productKind_SP).selectedItem.toString()

                val dbh_base_start_value = thisChild.findViewById<EditText>(R.id.dbh_base_startTV).text.toString()
                val dbh_base_end_value = thisChild.findViewById<EditText>(R.id.dbh_base_endTV).text.toString()

                val dbh_end_start_value = thisChild.findViewById<EditText>(R.id.dbh_end_startTV).text.toString()
                val dbh_end_end_value = thisChild.findViewById<EditText>(R.id.dbh_end_endTV).text.toString()

                val length_start_value = thisChild.findViewById<EditText>(R.id.length_startTV).text.toString()
                val length_end_value = thisChild.findViewById<EditText>(R.id.length_endTV).text.toString()

                val price_value = thisChild.findViewById<EditText>(R.id.price_TV).text.toString()

                if (dbh_base_start_value == "" || dbh_base_end_value == "" || dbh_end_start_value == "" ||
                    dbh_end_end_value == "" || length_start_value == "" || length_end_value == "" ||
                    price_value == "" || productKind_SP_value == ""){
                    thisChild.setBackgroundResource(R.drawable.shape_softpink_error_bg)
                }
                else{
                    var tmp = "[[1],[ไม้1,ไม้รวม],[2.5,0],[20,2.5],[2.5,0],[20,20],[2,0.1],[2.8,2.8],[1400,1000]]"
                    productRequirement.setPlotID(selectPlotID)
                    productRequirement.addProductKindList(productKind_SP_value)
                    productRequirement.addDbhBaseStartList(dbh_base_start_value)
                    productRequirement.addDbhBaseEndList(dbh_base_end_value)
                    productRequirement.addDbhEndStartList(dbh_end_start_value)
                    productRequirement.addDbhEndEndList(dbh_end_end_value)
                    productRequirement.addLengthStartList(length_start_value)
                    productRequirement.addLengthEndList(length_end_value)
                    productRequirement.addPriceList(price_value)

                    Log.i("prrrrr", productRequirement.toString())

                }

            }

        }

    }

}
