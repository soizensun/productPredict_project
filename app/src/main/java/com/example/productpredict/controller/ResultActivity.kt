package com.example.productpredict.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.example.productpredict.model.Product
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ResultActivity : AppCompatActivity() {
    private val retrofit = RetrofitClient.instance
    private val jsonApi = retrofit!!.create(AnApi::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val selectPlotID = intent.getStringExtra("selectPlotID")
        val productKindList = intent.getStringExtra("productKindList")
        val dbhBaseStartList = intent.getStringExtra("dbhBaseStartList")
        val dbhBaseEndList = intent.getStringExtra("dbhBaseEndList")
        val dbhEndStartList = intent.getStringExtra("dbhEndStartList")
        val dbhEndEndList = intent.getStringExtra("dbhEndEndList")
        val lengthStartList = intent.getStringExtra("lengthStartList")
        val lengthEndList = intent.getStringExtra("lengthEndList")
        val priceList = intent.getStringExtra("priceList")

        Log.i("qwqw", "selectPlotID" + selectPlotID)
        Log.i("qwqw", "productKindList" + productKindList)
        Log.i("qwqw", "dbhBaseStartList" + dbhBaseStartList)
        Log.i("qwqw", "dbhBaseEndList" + dbhBaseEndList)
        Log.i("qwqw", "dbhEndStartList" + dbhEndStartList)
        Log.i("qwqw", "dbhEndEndList" + dbhEndEndList)
        Log.i("qwqw", "lengthStartList" + lengthStartList)
        Log.i("qwqw", "lengthEndList" + lengthEndList)
        Log.i("qwqw", "priceList" + priceList)

        val tmp = "[[1],[\"ไม้1\",\"ไม้รวม\"],[2.5,0],[20,2.5],[2.5,0],[20,20],[2,0.1],[2.8,2.8],[1400,1000]]"

        jsonApi.getProducts(tmp).enqueue(object: Callback, retrofit2.Callback<Product>{
            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.i("fail response success", t.toString())
            }

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful){
                    Log.i("aaaaa", response.body().toString())
                }
            }
        })
    }
}