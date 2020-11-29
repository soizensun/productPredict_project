package com.example.productpredict.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.productpredict.R
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_choose_tree_number.*

class ChooseTreeNumberActivity : AppCompatActivity() {
    private lateinit var amountTreeSended: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_tree_number)


        val gardenIdList = intent.getStringExtra("gardenIdList")
        val mainSpecIdList = intent.getStringExtra("mainSpecIdList")
        val moreSpecNameList = intent.getStringExtra("moreSpecNameList")
        val dbhBaseStartList = intent.getStringExtra("dbhBaseStartList")
        val dbhBaseEndList = intent.getStringExtra("dbhBaseEndList")
        val dbhEndStartList = intent.getStringExtra("dbhEndStartList")
        val dbhEndEndList = intent.getStringExtra("dbhEndEndList")
        val lengthStartList = intent.getStringExtra("lengthStartList")
        val lengthEndList = intent.getStringExtra("lengthEndList")

        val amountTrees = amountTreesTV.text

        val size1 = size1ET.text
        val size2 = size2ET.text
        val area = areaET.text


        submitAmountTree.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)

            intent.putExtra("gardenIdList", gardenIdList.toString())
            intent.putExtra("mainSpecIdList", mainSpecIdList)
            intent.putExtra("moreSpecNameList", moreSpecNameList)
            intent.putExtra("dbhBaseStartList", dbhBaseStartList)
            intent.putExtra("dbhBaseEndList", dbhBaseEndList)
            intent.putExtra("dbhEndStartList", dbhEndStartList)
            intent.putExtra("dbhEndEndList", dbhEndEndList)
            intent.putExtra("lengthStartList", lengthStartList)
            intent.putExtra("lengthEndList", lengthEndList)

            if(amountTrees.isEmpty()){
                if(size1.isEmpty() || size2.isEmpty() || area.isEmpty())
                    Toasty.warning(this, "กรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT, true).show()
                else {
                    amountTreeSended =  ((1600 / (size1.toString().toFloat() * size2.toString().toFloat())) * area.toString().toFloat()).toString()
                    intent.putExtra("amountTrees", amountTreeSended)
                    startActivity(intent)
                }
            }
            else {
                amountTreeSended = amountTrees.toString()
                intent.putExtra("amountTrees", amountTreeSended)
                startActivity(intent)
            }
        }
    }
}