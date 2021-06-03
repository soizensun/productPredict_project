package com.example.productpredict.controller

import ProductType
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.google.gson.Gson
import com.google.gson.JsonObject
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.a_product_result_listitem.view.*
import kotlinx.android.synthetic.main.activity_result.*
import kotlinx.android.synthetic.main.dialog_view.view.close_BTN
import kotlinx.android.synthetic.main.dialog_view.view.mainPlot_TV
import kotlinx.android.synthetic.main.dialog_view.view.plotGroup_TV
import kotlinx.android.synthetic.main.dialog_view.view.subMainPlot_TV
import kotlinx.android.synthetic.main.dialog_view2.view.*
import retrofit2.Call
import retrofit2.Response
import java.text.DecimalFormat
import javax.security.auth.callback.Callback

class ResultActivity : AppCompatActivity() {
    private val retrofit = RetrofitClient.instance
    private val jsonApi = retrofit!!.create(AnApi::class.java)
    private var gson = Gson()
//    private var amountTrees: Double = 12.00
    private var size1: Float? = null
    private var size2: Float? = null
    private var area: Float? = null
    private var apiParam : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val apiParam = intent.getStringExtra("apiParam")
        val amountTrees = intent.getStringExtra("amountTrees")
        Log.i("data", amountTrees)
        val flag = intent.getStringExtra("flag")
        Log.i("data", flag)

        renderDataFromApi(apiParam, amountTrees.toDouble(), flag)

        backToHomeBTN.setOnClickListener{
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showToast(msg: String){
        Toasty.warning(this, msg, Toast.LENGTH_SHORT, true).show()
    }


    private fun renderDataFromApi(
        apiParam: String,
        amountTrees: Double?,
        flag: String?
    ){
        val formatter = DecimalFormat("#,###,###.##")
        jsonApi.getProducts(apiParam).enqueue(object: Callback, retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) { Log.i("fail response success", t.toString()) }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){
                    val json = response.body()
                    Log.i("tt", json.toString())
                    val jsonSize = json!!.size()
                    val plotDetail = json["name"].toString()

                    getPlotInfoBTN.setOnClickListener {
                        val dialogBuilder = AlertDialog.Builder(this@ResultActivity)
                        val view = View.inflate(this@ResultActivity , R.layout.dialog_view2, null)
                        dialogBuilder.setView(view)

                        val dialog = dialogBuilder.create()
                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                        dialog.show()

                        val plotDetail =  plotDetail.replace("\"", "")
                        val plotGroup = plotDetail.split(" ")[0].replace("กลุ่มแปลง", "")
                        val mainPlot = plotDetail.split(" ")[1].replace("แปลงหลัก", "")
                        val subMainPlot = plotDetail.split(" ")[3]

                        view.plotGroup_TV.text = plotGroup
                        view.mainPlot_TV.text = mainPlot
                        view.subMainPlot_TV.text = subMainPlot
                        view.amountTrees_TV.text = amountTrees!!.toInt().toString()

                        view.close_BTN.setOnClickListener { dialog.dismiss() }
                    }

                    val treesNumber = json["trees"].toString()
                    val aliveTressNumber = json["alive_trees"].toString()
                    var totalMass = 0.0

                    for (i in 0..(jsonSize - 8)) {
                        val productType = gson.fromJson(json[i.toString()], ProductType::class.java)
                        val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                        val rowView = inflater.inflate(R.layout.a_product_result_listitem, null)

                        rowView.productNameTV.text = productType.type
                        rowView.logNumberTV.text =  formatter.format(productType.log).toString()

                        rowView.productVolumeTV.text = ( formatter.format(productType.volume.toDouble() )).toString()
                        rowView.productWeightTV.text = ( formatter.format(productType.weight.toDouble() )).toString()

                        if(amountTrees != null){
                            var div = 1.00
                            if(flag == "totalTreesChoice"){
                                div = Integer.parseInt(aliveTressNumber).toDouble()
                            }
                            if(flag == "spaceAndAreaChoice"){
                                div = Integer.parseInt(treesNumber).toDouble()
                            }

                            rowView.allVolumeTV.text = ( formatter.format( ( productType.volume / div ) * amountTrees ) ).toString()
                            rowView.allWeightTV.text = ( formatter.format( ( ( productType.weight / div ) * amountTrees ) / 1000 ) ).toString()
                            totalMass += ( ( productType.weight / div ) * amountTrees ) / 1000
                        }

                        rowView.calulatePriceBTN.setOnClickListener {
                            var totalPrice = 0.0
                            if(amountTrees != null){
                                var allWeight : Float? = null
                                val aPrice = rowView.priceET.text.toString().toFloatOrNull()

                                if(rowView.allWeightTV.text != "-")  allWeight = rowView.allWeightTV.text.toString().toFloatOrNull()
                                else Log.i("fff", "pls fill in area plot")

                                if(allWeight != null && aPrice != null){
                                    rowView.priceAllTV.text = (formatter.format((aPrice * allWeight).toDouble())).toString()
                                }
                                else showToast("กรุณากรอกราคาต่อตัน")
                            }
                            else showToast("กรุณากรอกขนาดแปลงก่อน")

                            for (i in 1 until resultTable.childCount){
                                val thisChild = resultTable.getChildAt(i)
                                if (thisChild.priceAllTV.text != "-"){
                                    totalPrice += formatter.parse(thisChild.priceAllTV.text.toString()).toDouble()
                                }
                            }
                            priceTotalTV.text = formatter.format(totalPrice)
                        }
                        resultTable.addView(rowView, resultTable.childCount)
                    }
                    massTotalTV.text = formatter.format(totalMass)
                }
            }
        })
    }
}