package com.example.productpredict.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productpredict.R
import com.example.productpredict.model.Plot
import com.example.productpredict.controller.adapter.PlotAdapter
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var jsonApi: AnApi
    private var plotList: ArrayList<Plot> = ArrayList()
    private var displayList: ArrayList<Plot> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.instance
        jsonApi = retrofit!!.create(AnApi::class.java)

        val plotsList = ArrayList<Plot>()
        plotsList.add(Plot("1", "11111"))
        plotsList.add(Plot("2", "22222"))

        recyclePlot.setHasFixedSize(true)
        recyclePlot.layoutManager = LinearLayoutManager(this)
//        recyclePlot.adapter = PlotAdapter(plotsList)

        jsonApi.plots.enqueue(object: Callback<List<Plot>> {
            override fun onFailure(call: Call<List<Plot>>?, t: Throwable) {
                Log.i("http response fail", t.toString())
            }

            override fun onResponse(call: Call<List<Plot>>, response: Response<List<Plot>>) {
                if(response.isSuccessful){
                    Log.i("http response success", response.body().toString())
                    plotList = response.body() as ArrayList<Plot>
                    Log.i("list", plotsList.toString())
//                    displayList.addAll(plotsList)
                    recyclePlot.adapter = plotsList?.let { PlotAdapter(plotList) }
                    return
                }
            }
        })
    }
}
