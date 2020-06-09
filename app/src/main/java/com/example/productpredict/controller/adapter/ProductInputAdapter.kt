package com.example.productpredict.controller.adapter

import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.productpredict.R
import com.example.productpredict.controller.HomeActivity
import com.example.productpredict.model.Plot
import kotlinx.android.synthetic.main.a_product_input_listitem.view.*
import kotlinx.android.synthetic.main.activity_home.*

class ProductInputAdapter(private val plotList: List<Int>, private val testList : List<Int>) : RecyclerView.Adapter<ProductInputAdapter.TestViewHolder>() {

    private val scrollStates = hashMapOf<String, Parcelable>()
    private var items = listOf<Int>()
    

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.a_product_input_listitem, parent, false))
    }

    override fun getItemCount(): Int {
        return plotList.size
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {

        val context = holder.itemView.context
        var typeListOption = arrayOf("ไม้หนึ่ง", "ไม้รวม", "ไม้วีเนียร์", "ไม้เสา")

        Log.i("pos", plotList.toString())


        holder.productKind_SP.adapter = ArrayAdapter<String>(context, R.layout.support_simple_spinner_dropdown_item, typeListOption)
        holder.productKind_SP.setSelection(testList[position])
        holder.productKind_SP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Log.i("spinner", typeListOption.get(position))
            }
        }
//
//        holder.test.setOnClickListener {
//            val homeActivity = HomeActivity()
//            homeActivity.addNewListItem()
//        }
    }

    class TestViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var productKind_SP  = itemView.productKind_SP
        var test = itemView.test
    }

}