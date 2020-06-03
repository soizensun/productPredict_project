package com.example.productpredict.httpController

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var outInstance: Retrofit? = null
    val instance : Retrofit?
        get(){
        if(outInstance == null)
            outInstance = Retrofit.Builder()
                .baseUrl("http://eng.forest.ku.ac.th/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return outInstance!!
    }
}