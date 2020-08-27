package com.example.productpredict.controller

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.example.productpredict.model.*
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.a_plot_listitem.view.*
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
                                                             options.add("--กรุณาเลือกแปลงย่อย--")
                                                             subPlot_SP.adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1, options)
                                                             subPlot_SP.setSelection(options.size - 1)

                                                             subPlot_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                                                                 override fun onNothingSelected(parent: AdapterView<*>?) {}
                                                                 override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                                                     subPlotNameSelected = options[position]
                                                                     if(subPlotNameSelected != "--กรุณาเลือกแปลงย่อย--"){
                                                                         jsonApi.surveyDatePlotName(groupPlotNameSelected, mainPlotNameSelected, subPlotNameSelected).enqueue(object : Callback<SurveyDatePlotName>{
                                                                             override fun onFailure(call: Call<SurveyDatePlotName>, t: Throwable) {}
                                                                             @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
                                                                             override fun onResponse(call: Call<SurveyDatePlotName>, response: Response<SurveyDatePlotName>) {
                                                                                 val surveyDateList = ArrayList<SurveyDataForAdapter>()
                                                                                 for (i in 0 until response.body()?.date!!.size) {
                                                                                     surveyDateList.add(SurveyDataForAdapter(response.body()!!.date[i], response.body()!!.garden_id[i]))
                                                                                 }
//                                                                                 recycleSurveyDate.adapter = SurveyDateAdapter(surveyDateList)
                                                                                 surveyTable.removeAllViews()
                                                                                 val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                                                                                 for (i in surveyDateList){
                                                                                     val rowView = inflater.inflate(R.layout.a_plot_listitem, null)
                                                                                     rowView.surveyDateTV.text = i.date
                                                                                     rowView.gardenIdTV.text = i.garden_id
                                                                                     surveyTable.addView(rowView, surveyTable.childCount - 1)
                                                                                     val selectedBackground = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_a_plot_list_selected_bg)
                                                                                     val normalBackground = ContextCompat.getDrawable(this@MainActivity, R.drawable.shape_a_plot_list_bg)
                                                                                     rowView.setOnClickListener{
                                                                                         if (rowView.flagTV.text == "select" ) {
                                                                                             rowView.surveyDateTV.background = normalBackground
                                                                                             rowView.flagTV.text = "unselect"
                                                                                         } else{
                                                                                             rowView.surveyDateTV.background = selectedBackground
                                                                                             rowView.flagTV.text = "select"
                                                                                         }
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
            val childCount = surveyTable.childCount
            Log.i("resault", "$groupPlotNameSelected $mainPlotNameSelected $subPlotNameSelected $childCount")

            val gardenIdList = ArrayList<String>()
            for (i in 0 until childCount){
                val thisChild = surveyTable.getChildAt(i)
                if(thisChild.flagTV.text == "select"){
                    var tmpGardenId = thisChild.gardenIdTV.text.toString()
                    gardenIdList.add(tmpGardenId)
                }
            }

            if(groupPlotNameSelected == "--กรุณาเลือกกลุ่มแปลง--" || mainPlotNameSelected == "--กรุณาเลือกแปลงหลัก--" || subPlotNameSelected == "--กรุณาเลือกแปลงย่อย--" || gardenIdList.size == 0){
                Toasty.warning(this, "เลือกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT, true).show()
            }
            else {
                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("groupPlotNameSelected", groupPlotNameSelected)
                    putExtra("mainPlotNameSelected", mainPlotNameSelected)
                    putExtra("subPlotNameSelected", subPlotNameSelected)
                    putExtra("gardenIdList", gardenIdList)
                }
                startActivity(intent)
            }

        }
    }
}
