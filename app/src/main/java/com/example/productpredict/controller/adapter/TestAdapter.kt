package com.example.productpredict.controller.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.productpredict.R
import com.example.productpredict.model.Plot
import kotlinx.android.synthetic.main.a_test.view.*


class TestAdapter(private val plotList: List<Plot>) : RecyclerView.Adapter<TestAdapter.TestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return  TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.a_test, parent, false))
    }

    override fun getItemCount(): Int {
        return plotList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.plotNameTV1.text = plotList[position].plot_name

    }

    class TestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var plotNameTV1 = itemView.plotNameTV1
    }

}