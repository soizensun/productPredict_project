package com.example.productpredict.model

import android.util.Log

//http://eng.forest.ku.ac.th/project/eucapre/utilize4.php?p=[[1],[ไม้1,ไม้รวม],[2.5,0],[20,2.5],[2.5,0],[20,20],[2,0.1],[2.8,2.8],[1400,1000]]
//http://eng.forest.ku.ac.th/project/eucapre/utilize4.php?p=
// [[1],
// [ไม้1,ไม้รวม],
// โคนท่อนจาก   โคนท่อนถึง     ปลายท่อนจาก  ปลายท่อนถึง
// [2.5,0], [20,2.5], [2.5,0], [20,20],
// ยาวจาก      ยาวถึง        ราคา
// [2,0.1], [2.8,2.8], [1400,1000]]

class ProductRequirement() {
    private var plotID: String = ""

    private var productKindList= arrayListOf<String>()

    private var dbhBaseStartList= arrayListOf<String>()
    private var dbhBaseEndList= arrayListOf<String>()

    private var dbhEndStartList= arrayListOf<String>()
    private var dbhEndEndList= arrayListOf<String>()

    private var lengthStartList= arrayListOf<String>()
    private var lengthEndList= arrayListOf<String>()

    private var priceList = arrayListOf<String>()

    fun setPlotID(plotID: String){
        this.plotID = plotID
    }

    fun addProductKindList(productKind: String){
        this.productKindList.add(productKind)
    }

    fun addPriceList(price: String){
        priceList.add(price)
    }

    fun addDbhBaseStartList(dbhBaseStart: String){
        dbhBaseStartList.add(dbhBaseStart)
    }

    fun addDbhBaseEndList(dbhBaseEnd: String){
        this.dbhBaseEndList.add(dbhBaseEnd)
    }

    fun addDbhEndStartList(dbhEndStart: String){
        this.dbhEndStartList.add(dbhEndStart)
    }

    fun addDbhEndEndList(dbhEndEnd: String){
        dbhEndEndList.add(dbhEndEnd)
    }

    fun addLengthStartList(lengthStart: String){
        lengthStartList.add(lengthStart)
    }

    fun addLengthEndList(lengthEnd: String){
        lengthEndList.add(lengthEnd)
    }

    override fun toString(): String {
        return "ProductRequirement(plotID=$plotID, productKindList=$productKindList, dbhBaseStartList=$dbhBaseStartList, dbhBaseEndList=$dbhBaseEndList, dbhEndStartList=$dbhEndStartList, dbhEndEndList=$dbhEndEndList, lengthStartList=$lengthStartList, lengthEndList=$lengthEndList, priceList=$priceList)"
    }

}

