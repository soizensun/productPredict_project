package com.example.productpredict.httpController

import com.example.productpredict.model.*
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AnApi {
  // get plot url
  @get:GET("project/eucapre/api_plot.php/")
  val plots: Call<List<Plot>>

  @get:GET("project/eucapre/sel_spec.php")
  val spec: Call<SpecProduct>

  @GET("project/eucapre/utilize5.php")
  fun getProducts(
    @Query("p")
    productSpec: String
  ): Call<JsonObject>

  // get group name plot
  //รายชื่อกลุ่มแปลง
  @get:GET("project/eucapre/sel_groupname.php")
  val groupPlotName: Call<GroupPlotName>

  @GET("project/eucapre/sel_mainname.php")
  fun mainPlotName(
    @Query("x")
    groupPlotName: String
  ): Call<MainPlotName>

  @GET("project/eucapre/sel_sub.php")
  fun subPlotName(
    @Query("x")
    groupPlotName: String,
    @Query("y")
    mainPlotName: String
  ): Call<SubPlotName>

  @GET("project/eucapre/sel_date.php")
  fun surveyDatePlotName(
    @Query("x")
    groupPlotName: String,
    @Query("y")
    mainPlotName: String,
    @Query("z")
    subPlotName: String
  ): Call<SurveyDatePlotName>

  @POST("project/eucapre/api_login.php")
  fun verifyUser(
    @Query("username")
    username: String,
    @Query("password")
    password: String
  ): Call<User>

}