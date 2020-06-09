package com.example.productpredict.controller.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productpredict.R
import com.example.productpredict.controller.HomeActivity
import com.example.productpredict.model.Plot
import kotlinx.android.synthetic.main.a_plot_listitem.view.*

class PlotAdapter(private val plotList: List<Plot>) : RecyclerView.Adapter<PlotAdapter.PlotViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlotViewHolder {
    return  PlotViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.a_plot_listitem, parent, false))
  }

  override fun getItemCount(): Int {
    return plotList.size
  }

  override fun onBindViewHolder(holder: PlotViewHolder, position: Int) {
    val context = holder.itemView.context

    holder.plotNameTV.text = plotList[position].plot_name

    holder.itemView.setOnClickListener {
      val currentSelectItem = holder.plotNameTV.text.toString()
      val intent = Intent(context, HomeActivity::class.java)

      intent.putExtra("selectPlot", currentSelectItem)
      context.startActivity(intent)

//      val myPreference = MyPreference(context)
//      myPreference.setCurrentPlotPreference(currentSelectItem)

    }
  }

  class PlotViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//    var id = itemView.idTV
    var plotNameTV = itemView.plotNameTV
  }

}