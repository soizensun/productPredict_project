package com.example.productpredict.model

import android.content.Context

class MyPreference(context: Context) {
    val PREFERENCE_NAME = "ProductPredictSharePreference"
    val PREFERANCE_CURRENT_PLOT = "current_plot"

    val preference = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getCurrentPlotPreference() : String? {
        return preference.getString(PREFERANCE_CURRENT_PLOT, "")
    }

    fun setCurrentPlotPreference(currentPlotTMP : String) {
        val editor = preference.edit()
        editor.putString(PREFERANCE_CURRENT_PLOT, currentPlotTMP)
        editor.apply()
    }


}