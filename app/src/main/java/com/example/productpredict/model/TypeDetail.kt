package com.example.productpredict.model

data class TypeDetail(
    val log: Int,
    val name: String,
    val trees: List<List<Double>>,
    val v: Double,
    val w: Double
)
