package com.example.productpredict.httpController

import com.example.productpredict.model.Plot
import com.example.productpredict.model.ProductRequirement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Part

interface AnApi {

  // get plot url
  @get:GET("project/eucapre/api_plot.php/")
  val plots: Call<List<Plot>>

  @GET("project/eucapre/utilize3.php?p={productRequest}")
  fun getProduct(@Part("productRequest")productRequest : String ): Call<ProductRequirement>
}