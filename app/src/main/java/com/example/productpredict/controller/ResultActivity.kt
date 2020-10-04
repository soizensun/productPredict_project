package com.example.productpredict.controller

import ProductType
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.annotation.IntegerRes
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.google.gson.Gson
import com.google.gson.JsonObject
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.a_product_result_listitem.*
import kotlinx.android.synthetic.main.a_product_result_listitem.view.*
import kotlinx.android.synthetic.main.a_product_result_listitem.view.allWeightTV
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_result.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import kotlin.math.log
import kotlin.properties.Delegates

class ResultActivity : AppCompatActivity() {
    private val retrofit = RetrofitClient.instance
    private val jsonApi = retrofit!!.create(AnApi::class.java)
    private var gson = Gson()
    private var amountTrees: Float? = null
    private var size1: Float? = null
    private var size2: Float? = null
    private var area: Float? = null
    private var apiParam : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tmpArray = arrayListOf<String>()

        val gardenIdList = intent.getStringExtra("gardenIdList")
        val mainSpecIdList = intent.getStringExtra("mainSpecIdList")
        val moreSpecNameList = intent.getStringExtra("moreSpecNameList")
        val dbhBaseStartList = intent.getStringExtra("dbhBaseStartList")
        val dbhBaseEndList = intent.getStringExtra("dbhBaseEndList")
        val dbhEndStartList = intent.getStringExtra("dbhEndStartList")
        val dbhEndEndList = intent.getStringExtra("dbhEndEndList")
        val lengthStartList = intent.getStringExtra("lengthStartList")
        val lengthEndList = intent.getStringExtra("lengthEndList")
        
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
        renderDataFromApi(apiParam, amountTrees)

        getSizePlotBTN.setOnClickListener {
            resultTable.removeViews(1, resultTable.childCount - 1)
            size1 = size1ET.text.toString().toFloatOrNull()
            size2 = size2ET.text.toString().toFloatOrNull()
            area = areaET.text.toString().toFloatOrNull()

            if (size1 == null || size2 == null || area == null)
                Toasty.warning(this, "กรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT, true).show()
            else {
                amountTrees =  (1600 / (size1!! * size2!!)) * area!!
                renderDataFromApi(apiParam, amountTrees)
            }
        }


    }

    private fun showToast(msg: String){
        Toasty.warning(this, msg, Toast.LENGTH_SHORT, true).show()
    }

    private fun renderDataFromApi(apiParam: String, amountTrees: Float?){
        jsonApi.getProducts(apiParam).enqueue(object: Callback, retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) { Log.i("fail response success", t.toString()) }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){
                    val json = response.body()
                    val jsonSize = json!!.size()
                    var plotDetail = json["name"].toString()
                    val treesNumber = json["trees"].toString()
                    for (i in 0..(jsonSize - 3)) {
                        val productType = gson.fromJson(json[i.toString()], ProductType::class.java)
                        val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val rowView = inflater.inflate(R.layout.a_product_result_listitem, null)

                        rowView.productNameTV.text = productType.type
                        rowView.logNumberTV.text = productType.log.toString()

                        rowView.productVolumeTV.text = ( "%.3f".format(productType.volume).toDouble() ).toString()
                        rowView.productWeightTV.text = ( "%.3f".format(productType.weight).toDouble() ).toString()

                        if(amountTrees != null){
                            rowView.allVolumeTV.text = ( "%.3f".format( ( productType.volume / Integer.parseInt(treesNumber) ) * amountTrees ).toDouble() ).toString()
                            rowView.allWeightTV.text = ( "%.3f".format( ( (productType.weight / Integer.parseInt(treesNumber) ) * amountTrees ) / 1000 ).toDouble() ).toString()
                        }

                        rowView.calulatePriceBTN.setOnClickListener {
                            var totalPrice = 0.0

                            if(amountTrees != null){
                                var allWeight : Float? = null
                                val aPrice = rowView.priceET.text.toString().toFloatOrNull()

                                if(rowView.allWeightTV.text != "-")  allWeight = rowView.allWeightTV.text.toString().toFloatOrNull()
                                else Log.i("fff", "pls fill in area plot")

                                if(allWeight != null && aPrice != null){
                                    rowView.priceAllTV.text = "%.2f".format(aPrice * allWeight).toDouble().toString()
                                }
                                else showToast("กรุณากรอกราคาต่อตัน")
                            }
                            else showToast("กรุณากรอกขนาดแปลงก่อน")


                            for (i in 1 until resultTable.childCount){
                                val thisChild = resultTable.getChildAt(i)

                                if (thisChild.priceAllTV.text != "-"){
                                    totalPrice += thisChild.priceAllTV.text.toString().toFloat()
                                }
                            }
                            priceTotalTV.text = ( "%.2f".format(totalPrice).toDouble() ).toString()

                        }
                        resultTable.addView(rowView, resultTable.childCount)
                    }
                }
            }
        })
    }
}