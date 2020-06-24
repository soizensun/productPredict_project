package com.example.productpredict.controller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
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
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var jsonApi: AnApi
    private var plotList: ArrayList<Plot> = ArrayList()
    private var displayList: ArrayList<Plot> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = RetrofitClient.instance
        jsonApi = retrofit!!.create(AnApi::class.java)

//        val plotsList = ArrayList<Plot>()
//        plotsList.add(Plot("1", "11111"))
//        plotsList.add(Plot("2", "22222"))

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
                    displayList.addAll(plotList)
                    recyclePlot.adapter = plotList?.let { PlotAdapter(displayList) }
                    return
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.search_bar)
        if (searchItem != null){
            val searchView : SearchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    displayList.clear()
                    if(newText!!.isNotEmpty()){
                        val search = newText.toLowerCase()
                        Log.i("ffff", search)
                        plotList.forEach {
                        if(it.plot_name.toLowerCase().contains(search)){
                                displayList.add(it)
                            }
                        }
                    }else{
                        displayList.addAll(plotList)
                    }
                    recyclePlot.adapter?.notifyDataSetChanged()
                    return true
                }
            })
        }
        return super.onCreateOptionsMenu(menu)
    }

}
