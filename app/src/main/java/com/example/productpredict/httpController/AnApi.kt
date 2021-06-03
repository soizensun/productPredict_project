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

  //รายการการใช้ประโยชน์
  @get:GET("eucapre/sel_spec.php")
  val spec: Call<SpecProduct>

  //คำนวณ
  @GET("eucapre/utilize_mobile.php")
  fun getProducts(
    @Query("p")
    productSpec: String
  ): Call<JsonObject>

  // get group name plot
  //รายชื่อกลุ่มแปลง
  @get:GET("eucapre/get_group.php")
  val groupPlotName: Call<GroupPlotName>

  //รายชื่อแปลงหลัก
  @GET("eucapre/get_main.php")
  fun mainPlotName(
    @Query("group_name")
    groupPlotName: String
  ): Call<MainPlotName>

  //รายชื่อแปลงย่อย
  @GET("eucapre/get_sub.php")
  fun subPlotName(
    @Query("group_name")
    groupPlotName: String,
    @Query("main_name")
    mainPlotName: String
  ): Call<SubPlotName>

  //รายการวันที่สำรวจ
  @GET("eucapre/get_plot.php")
  fun surveyDatePlotName(
    @Query("group_name")
    groupPlotName: String,
    @Query("main_name")
    mainPlotName: String,
    @Query("sub_id")
    subPlotName: String
  ): Call<SurveyDatePlotName>

  @POST("eucapre/api_login.php")
  fun verifyUser(
    @Query("username")
    username: String,
    @Query("password")
    password: String
  ): Call<User>

}
