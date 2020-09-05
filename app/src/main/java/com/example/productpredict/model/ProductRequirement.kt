package com.example.productpredict.model

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

    var mainSpecIdList = arrayListOf<String>()
    var moreSpecNameList= arrayListOf<String>()

    var dbhBaseStartList= arrayListOf<String>()
    var dbhBaseEndList= arrayListOf<String>()

    var dbhEndStartList= arrayListOf<String>()
    var dbhEndEndList= arrayListOf<String>()

    var lengthStartList= arrayListOf<String>()
    var lengthEndList= arrayListOf<String>()


    fun setPlotID(plotID: String){
        this.plotID = plotID
    }

    fun addMainProductKindList(specId: String) {
        this.mainSpecIdList.add(specId)
    }

    fun addProductKindList(productKind: String){
        this.moreSpecNameList.add(productKind)
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
        return "ProductRequirement(plotID=$plotID, productKindList=$moreSpecNameList, dbhBaseStartList=$dbhBaseStartList, dbhBaseEndList=$dbhBaseEndList, dbhEndStartList=$dbhEndStartList, dbhEndEndList=$dbhEndEndList, lengthStartList=$lengthStartList, lengthEndList=$lengthEndList)"
    }



}

