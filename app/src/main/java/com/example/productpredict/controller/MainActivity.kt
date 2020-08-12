package com.example.productpredict.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.example.productpredict.model.GroupPlotName
import com.example.productpredict.model.MainPlotName
import com.example.productpredict.model.SubPlotName
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var jsonApi: AnApi

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonApi = RetrofitClient.instance!!.create(AnApi::class.java)

        var groupPlotNameSelected : String = ""
        var mainPlotNameSelected : String = ""
        var subPlotNameSelected : String = ""

        jsonApi.groupPlotName.enqueue(object: Callback<GroupPlotName> {
            override fun onFailure(call: Call<GroupPlotName>?, t: Throwable) {}
            override fun onResponse(call: Call<GroupPlotName>, response: Response<GroupPlotName>) {
                if(response.isSuccessful){
                    val groupNameList = response.body() as GroupPlotName
                    val options : ArrayList<String>  = groupNameList.group_name
                    options.add("--กรุณาเลือกกลุ่มแปลง--")
                    groupPlot_SP.adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, options)
                    groupPlot_SP.setSelection(options.size - 1)

                    groupPlot_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                            groupPlotNameSelected = options[position]
                             if(groupPlotNameSelected != "--กรุณาเลือกกลุ่มแปลง--"){
                                 jsonApi.mainPlotName(groupPlotNameSelected).enqueue(object : Callback<MainPlotName>{
                                     override fun onFailure(call: Call<MainPlotName>, t: Throwable) {}
                                     override fun onResponse(call: Call<MainPlotName>, response: Response<MainPlotName>) {
                                         val mainNameList = response.body() as MainPlotName
                                         val options  = mainNameList.main_name
                                         options.add("--กรุณาเลือกแปลงหลัก--")
                                         mainPlot_SP.adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, options)
                                         mainPlot_SP.setSelection(options.size - 1)

                                         mainPlot_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                             override fun onNothingSelected(parent: AdapterView<*>?) {}
                                             override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                 mainPlotNameSelected = options[position]
                                                 if(mainPlotNameSelected != "--กรุณาเลือกแปลงหลัก--"){
                                                     jsonApi.subPlotName(groupPlotNameSelected, mainPlotNameSelected).enqueue(object : Callback<SubPlotName>{
                                                         override fun onFailure(call: Call<SubPlotName>, t: Throwable) {}
                                                         override fun onResponse(call: Call<SubPlotName>, response: Response<SubPlotName>) {
                                                             val subPlotNameList = response.body() as SubPlotName
                                                             val options  = subPlotNameList.sub_id
                                                             options.add("--กรุณาเลือกแปลงหลัก--")
                                                             subPlot_SP.adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, options)
                                                             subPlot_SP.setSelection(options.size - 1)

                                                             subPlot_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                                                 override fun onNothingSelected(parent: AdapterView<*>?) {}
                                                                 override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                                     subPlotNameSelected = options[position]
                                                                 }
                                                             }
                                                         }
                                                     })
                                                 }
                                             }
                                         }
                                     }
                                 })
                             }
                        }
                    }
                    return
                }
            }
        })
        confirmPlotInput_BTN.setOnClickListener{
            Log.i("resault", "$groupPlotNameSelected $mainPlotNameSelected $subPlotNameSelected")
//            var tmp =
//            if()
        }
    }
}
