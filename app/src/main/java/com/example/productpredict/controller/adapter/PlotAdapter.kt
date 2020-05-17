package com.example.productpredict.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productpredict.R
import com.example.productpredict.model.Plot
import kotlinx.android.synthetic.main.a_plot.view.*

class PlotAdapter(private val plotList: List<Plot>) : RecyclerView.Adapter<PlotAdapter.PlotViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlotViewHolder {
        return  PlotViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.a_plot, parent, false))
    }

    override fun getItemCount(): Int {
        return plotList.size
    }

    override fun onBindViewHolder(holder: PlotViewHolder, position: Int) {
        holder.id.text = plotList[position].id
        holder.plotNameTV.text = plotList[position].plot_name
    }

    // plot holder
    class PlotViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {
        var id = itemView.idTV
        var plotNameTV = itemView.plotNameTV
    }

}