package com.example.productpredict.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class ResultActivity : AppCompatActivity() {
    private val retrofit = RetrofitClient.instance
    private val jsonApi = retrofit!!.create(AnApi::class.java)

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
        var apiParam = tmpArray.toString()
        jsonApi.getProducts(apiParam).enqueue(object: Callback, retrofit2.Callback<JsonObject>{
            override fun onFailure(call: Call<JsonObject>, t: Throwable) { Log.i("fail response success", t.toString()) }
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful){
                    Log.i("aaaaa", response.body().toString())
                    val json = response.body()
                    Log.i("aaaaa", json!!.size().toString())
                    Log.i("aaaaa", json!!["name"].toString())
                }
            }
        })
    }
}