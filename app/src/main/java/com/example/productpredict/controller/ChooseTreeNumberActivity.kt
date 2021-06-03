package com.example.productpredict.controller

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.google.gson.JsonObject
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_choose_tree_number.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.dialog_view2.view.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ChooseTreeNumberActivity : AppCompatActivity() {
    private lateinit var amountTreeSended: String
    private val retrofit = RetrofitClient.instance
    private val jsonApi = retrofit!!.create(AnApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_tree_number)

        val gardenIdList = intent.getStringExtra("gardenIdList")
        val mainSpecIdList = intent.getStringExtra("mainSpecIdList")
        val moreSpecNameList = intent.getStringExtra("moreSpecNameList")
        val dbhBaseStartList = intent.getStringExtra("dbhBaseStartList")
        val dbhBaseEndList = intent.getStringExtra("dbhBaseEndList")
        val dbhEndStartList = intent.getStringExtra("dbhEndStartList")
        val dbhEndEndList = intent.getStringExtra("dbhEndEndList")
        val lengthStartList = intent.getStringExtra("lengthStartList")
        val lengthEndList = intent.getStringExtra("lengthEndList")



        val tmpArray = arrayListOf<String>()
        var apiParam : String = ""

        if(dbhBaseStartList == null){
            tmpArray.add(gardenIdList)
            tmpArray.add(mainSpecIdList)
        }
        else {
            tmpArray.add(gardenIdList)
            tmpArray.add(mainSpecIdList)
            tmpArray.add(moreSpecNameList)
            tmpArray.add(dbhBaseStartList)
            tmpArray.add(dbhBaseEndList)
            tmpArray.add(dbhEndStartList)
            tmpArray.add(dbhEndEndList)
            tmpArray.add(lengthStartList)
            tmpArray.add(lengthEndList)
        }
        apiParam = tmpArray.toString()

        jsonApi.getProducts(apiParam).enqueue(object: Callback, retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) { Log.i("fail response success", t.toString()) }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){

                    val json = response.body()
                    Log.i("ttt", json.toString())

                    if(json?.get("total_trees") != null){
                        amountTreesTV.setText(json["total_trees"].toString())
                    }else {
                        amountTreesTV.setText("0")
                    }

                    if(json?.get("spacing1") != null){
                        size1ET.setText(json["spacing1"].toString())
                    }else {
                        size1ET.setText("0")
                    }

                    if(json?.get("spacing2") != null){
                        size2ET.setText(json["spacing2"].toString())
                    }else {
                        size2ET.setText("0")
                    }

                    if(json?.get("area") != null){
                        areaET.setText(json["area"].toString())
                    }else {
                        areaET.setText("0")
                    }

                    submitAmountTree.setOnClickListener {
                        val intent = Intent(this@ChooseTreeNumberActivity, ResultActivity::class.java)
                        intent.putExtra("apiParam", apiParam)

                        if(amountTreesTV.text.isEmpty() || amountTreesTV.text.toString() == "0"){
                            if(size1ET.text.isEmpty() || size2ET.text.isEmpty() || areaET.text.isEmpty() || size1ET.text.toString() == "0" || size2ET.text.toString() == "0" || areaET.text.toString() == "0")
                                Toasty.warning(this@ChooseTreeNumberActivity, "กรุณาตรวจสอบข้อมูลที่กรอก", Toast.LENGTH_SHORT, true).show()
                            else {
                                amountTreeSended =  ((1600 / (size1ET.text.toString().toFloat() * size2ET.text.toString().toFloat())) * areaET.text.toString().toFloat()).toString()
                                intent.putExtra("amountTrees", amountTreeSended)
                                intent.putExtra("flag", "spaceAndAreaChoice")
                                startActivity(intent)
                            }
                        }
                        else {
                            intent.putExtra("amountTrees", amountTreesTV.text.toString())
                            intent.putExtra("flag", "totalTreesChoice")
                            startActivity(intent)
                        }
                    }
                }
            }
        })


    }
}