package com.example.productpredict.httpController

import com.example.productpredict.model.Plot
import com.example.productpredict.model.Product
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnApi {
  // get plot url
  @get:GET("project/eucapre/api_plot.php/")
  val plots: Call<List<Plot>>

  @GET("project/eucapre/utilize4.php")
  fun getProducts(@Query("p")productSpec: String): Call<Product>
}