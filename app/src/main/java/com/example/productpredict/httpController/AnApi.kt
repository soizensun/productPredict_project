package com.example.productpredict.httpController

import com.example.productpredict.model.Plot
import retrofit2.Call
import retrofit2.http.GET

interface AnApi {

  // get plot url
  @get:GET("project/eucapre/api_plot.php")
  val plots: Call<List<Plot>>
}