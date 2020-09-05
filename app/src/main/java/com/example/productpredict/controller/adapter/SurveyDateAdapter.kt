package com.example.productpredict.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productpredict.R
import com.example.productpredict.model.SurveyDataRenderModel
import kotlinx.android.synthetic.main.a_plot_listitem.view.*

class SurveyDateAdapter(private val surveySurveyDateList: List<SurveyDataRenderModel>) : RecyclerView.Adapter<SurveyDateAdapter.PlotViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlotViewHolder {
        return  PlotViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.a_plot_listitem, parent, false))
    }

    override fun getItemCount(): Int {
        return surveySurveyDateList.size
    }

    override fun onBindViewHolder(holder: PlotViewHolder, position: Int) {
        val context = holder.itemView.context

    }

    class PlotViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var surveyPlotCB = itemView.surveyDateTV
    }

}