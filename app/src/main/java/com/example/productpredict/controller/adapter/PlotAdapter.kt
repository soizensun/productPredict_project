package com.example.productpredict.controller.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
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

  @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
  override fun onBindViewHolder(holder: PlotViewHolder, position: Int) {
    val context = holder.itemView.context

    holder.plotNameTV.text = plotList[position].plot_name
    holder.plotIDTV.text = plotList[position].id

    holder.itemView.setOnClickListener {
      val currentSelectPlotName = holder.plotNameTV.text.toString()
      val currentSelectPlotID = holder.plotIDTV.text.toString()
      val intent = Intent(context, HomeActivity::class.java)

      intent.putExtra("selectPlotName", currentSelectPlotName)
      intent.putExtra("selectPlotID",  currentSelectPlotID)

      val p1 = Pair.create<View, String>(holder.plotNameTV , holder.plotNameTV.transitionName)

      val options = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ActivityOptions.makeSceneTransitionAnimation(context as Activity?,  p1)
      } else {
        TODO("VERSION.SDK_INT < LOLLIPOP")
      }

      context.startActivity(intent, options.toBundle())

//      val myPreference = MyPreference(context)
//      myPreference.setCurrentPlotPreference(currentSelectItem)

    }
  }

  class PlotViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
//    var id = itemView.idTV
    var plotNameTV = itemView.plotNameTV
    var plotIDTV = itemView.plotIDTV
  }

}