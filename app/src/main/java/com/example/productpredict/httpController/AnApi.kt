package com.example.productpredict.httpController

import com.example.productpredict.model.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AnApi {
  // get plot url
  @get:GET("project/eucapre/api_plot.php/")
  val plots: Call<List<Plot>>

  @GET("project/eucapre/utilize4.php")
  fun getProducts(
    @Query("p")
    productSpec: String
  ): Call<Product>

  // get group name plot
  @get:GET("project/eucapre/sel_groupname.php")
  val groupPlotName: Call<GroupPlotName>

  @GET("project/eucapre/sel_mainname.php")
  fun mainPlotName(
    @Query("x")
    groupPlotName: String
  ): Call<MainPlotName>

  @GET("http://eng.forest.ku.ac.th/project/eucapre/sel_sub.php")
  fun subPlotName(
    @Query("x")
    groupPlotName: String ,
    @Query("y")
    mainPlotName: String
  ): Call<SubPlotName>

}