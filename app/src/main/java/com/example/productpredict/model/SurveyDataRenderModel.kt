package com.example.productpredict.model

import java.text.SimpleDateFormat
import java.util.*

data class SurveyDataRenderModel  (val date: String, val garden_id: String) {
    fun toDate(): Date? {
        return SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).parse(date)
    }
}
