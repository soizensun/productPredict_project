package com.example.productpredict.controller

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.example.productpredict.model.*
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.a_plot_listitem.view.flagTV
import kotlinx.android.synthetic.main.a_plot_listitem.view.surveyDateTV
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.dialog_view.view.*
import kotlinx.android.synthetic.main.spec_product_listitem.view.*
import kotlinx.android.synthetic.main.user_detail_dialog.*
import retrofit2.Call
import retrofit2.Response
import kotlin.math.log
import kotlin.system.exitProcess

@Suppress("DEPRECATION")
class HomeActivity : AppCompatActivity() {
    private lateinit var jsonApi: AnApi
    private var doubleBackToExitPressedOnce = false

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        jsonApi = RetrofitClient.instance!!.create(AnApi::class.java)

        val groupPlotNameSelected = intent.getStringExtra("groupPlotNameSelected")
        val mainPlotNameSelected  = intent.getStringExtra("mainPlotNameSelected")
        val subPlotNameSelected  = intent.getStringExtra("subPlotNameSelected")
        val gardenIdList  = intent.getStringArrayListExtra("gardenIdList")
        val gardenDetailList  = intent.getStringArrayListExtra("gardenDetailList")
        val pref = PreferenceManager.getDefaultSharedPreferences(this)

        if (groupPlotNameSelected != null &&
            mainPlotNameSelected != null &&
            subPlotNameSelected != null)   {
            plotName_TV.text = "$groupPlotNameSelected  $mainPlotNameSelected"

            val icon : Drawable = resources.getDrawable(R.drawable.ic_baseline_info_24_white);
            val background = resources.getDrawable(R.drawable.shape_homeactivity_header_bg)
            search_btn.setImageDrawable(icon)
            search_btn.background = background

            fun showDialog() {
                val dialogBuilder = AlertDialog.Builder(this)
                val view = View.inflate(this, R.layout.dialog_view, null)
                dialogBuilder.setView(view)

                val dialog = dialogBuilder.create()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.show()

                view.plotGroup_TV.text = groupPlotNameSelected
                view.mainPlot_TV.text = mainPlotNameSelected
                view.subMainPlot_TV.text = subPlotNameSelected
//                view.surveyData_TV.text = gardenDetailList.toString()

                val inflater: LayoutInflater  = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                for (i in gardenDetailList){
                    val rowView = inflater.inflate(R.layout.spec_product_listitem, null)

                    rowView.surveyDateTV.text = i
                    view.surveyTableDialogView.addView(rowView)
                }

                view.close_BTN.setOnClickListener { dialog.dismiss() }
                view.changePlot_BTN.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
            }

            plotInput.setOnClickListener { showDialog() }
            search_btn.setOnClickListener { showDialog() }
        }
        else {
            plotName_TV.text = "เลือกแปลง"
            search_btn.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            plotInput.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        addField_BTN.setOnClickListener{
            val inflater : LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView = inflater.inflate(R.layout.a_product_input_listitem, null)
            parent_linear_layout.addView(rowView, parent_linear_layout.childCount - 1)
        }

        userStatusBTN.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setView(View.inflate(this, R.layout.user_detail_dialog, null))
            val dialog = dialogBuilder.create()
            dialog.show()
            pref.apply {
                val user = getString("currentUser", "-")
                dialog.currentUserTV.text = "ผู้ใช้ปัจจุบัน : $user"
            }
            dialog.logoutBTN.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                val pref = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = pref.edit()
                editor.putString("currentUser", "-").apply()
                startActivity(intent)
            }
        }

        jsonApi.spec.enqueue(object: retrofit2.Callback<SpecProduct> {
            override fun onFailure(call: Call<SpecProduct>, t: Throwable) {
                Toasty.error(this@HomeActivity, "กรุณาตรวจสอบอินเทอร์เน็ต", Toast.LENGTH_SHORT, true).show()
            }
            override fun onResponse(call: Call<SpecProduct>, response: Response<SpecProduct>) {
                val specTypeList = ArrayList<SpecTypeRenderModel>()
                Log.i("fff", response.body().toString())
                for (i in 0 until response.body()?.spec_id!!.size) {
                    if(response.body()!!.type_name[i] == "ไม้ 3 (ไม้ฟืน)" || response.body()!!.type_name[i] == "ไม้ 2"  || response.body()!!.type_name[i] == "ไม้ 1") specTypeList.add(SpecTypeRenderModel(response.body()!!.spec_id[i], response.body()!!.type_name[i], "select"))
                    else specTypeList.add(SpecTypeRenderModel(response.body()!!.spec_id[i], response.body()!!.type_name[i], "unselect"))
                }

                val inflater: LayoutInflater  = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                for (i in specTypeList){
                    val rowView = inflater.inflate(R.layout.spec_product_listitem, null)
                    val selectedBackground = ContextCompat.getDrawable(this@HomeActivity, R.drawable.shape_a_plot_list_selected_bg)
                    val normalBackground = ContextCompat.getDrawable(this@HomeActivity, R.drawable.shape_a_spec_product_list_bg)

                    rowView.surveyDateTV.text = i.type_name
                    rowView.specIdTV.text = i.spec_id
                    rowView.flagTV.text = i.selectedFlag
                    productTypeTable.addView(rowView, productTypeTable.childCount)
                    if (rowView.flagTV.text == "unselect") {
                        rowView.surveyDateTV.background = normalBackground
                        rowView.surveyDateTV.setTextColor(Color.parseColor("#313131"))
                    } else{
                        rowView.surveyDateTV.background = selectedBackground
                        rowView.surveyDateTV.setTextColor(Color.parseColor("#FFFFFF"))
                    }
                    rowView.setOnClickListener{
                        if (rowView.flagTV.text == "select") {
                            rowView.surveyDateTV.background = normalBackground
                            rowView.flagTV.text = "unselect"
                            rowView.surveyDateTV.setTextColor(Color.parseColor("#313131"))
                        } else{
                            rowView.surveyDateTV.background = selectedBackground
                            rowView.flagTV.text = "select"
                            rowView.surveyDateTV.setTextColor(Color.parseColor("#FFFFFF"))
                        }
                    }
                }
            }
        })

        calulate_BTN.setOnClickListener {
            val childCount = parent_linear_layout.childCount
            val mainSpecChildCount = productTypeTable.childCount
            val productRequirement = ProductRequirement()

            for (i in 0 until mainSpecChildCount){
                val thisChild = productTypeTable.getChildAt(i)
                val flag = thisChild.findViewById<TextView>(R.id.flagTV).text.toString()
                val specId = thisChild.findViewById<TextView>(R.id.specIdTV).text.toString()
                if (flag == "select") productRequirement.addMainProductKindList(specId)
            }
            if (gardenIdList == null){
                Toasty.warning(this, "กรุณาเลือกแปลง", Toast.LENGTH_SHORT, true).show()
            }
            else{
                if(childCount == 3){
                    val intent = Intent(this, ChooseTreeNumberActivity::class.java)
                    intent.putExtra("gardenIdList", gardenIdList.toString())
                    intent.putExtra("mainSpecIdList", productRequirement.mainSpecIdList.toString())

                    startActivity(intent)
                }
                else if (childCount > 3){
                    for (i in 2 until childCount - 1){
                        val thisChild = parent_linear_layout.getChildAt(i)

                        val productKind_value = thisChild.findViewById<EditText>(R.id.product_kindTV).text.toString()
                        val dbh_base_start_value = thisChild.findViewById<EditText>(R.id.dbh_base_startTV).text.toString()
                        val dbh_base_end_value = thisChild.findViewById<EditText>(R.id.dbh_base_endTV).text.toString()
                        val dbh_end_start_value = thisChild.findViewById<EditText>(R.id.dbh_end_startTV).text.toString()
                        val dbh_end_end_value = thisChild.findViewById<EditText>(R.id.dbh_end_endTV).text.toString()
                        val length_start_value = thisChild.findViewById<EditText>(R.id.length_startTV).text.toString()
                        val length_end_value = thisChild.findViewById<EditText>(R.id.length_endTV).text.toString()

                        if (dbh_base_start_value == "" || dbh_base_end_value == "" || dbh_end_start_value == "" ||
                            dbh_end_end_value == "" || length_start_value == "" || length_end_value == "" || productKind_value == ""){
                            thisChild.setBackgroundResource(R.drawable.shape_softpink_error_bg)
                            Toasty.warning(this, "กรอกข้อมูลให้ครบถ้วน", Toast.LENGTH_SHORT, true).show()
                        }
                        else {
                            thisChild.setBackgroundResource(R.drawable.shape_a_product_input_list_bg)
                            val tmpProductKindList = "\"" + productKind_value + "\""

                            productRequirement.addProductKindList(tmpProductKindList)
                            productRequirement.addDbhBaseStartList(dbh_base_start_value)
                            productRequirement.addDbhBaseEndList(dbh_base_end_value)
                            productRequirement.addDbhEndStartList(dbh_end_start_value)
                            productRequirement.addDbhEndEndList(dbh_end_end_value)
                            productRequirement.addLengthStartList(length_start_value)
                            productRequirement.addLengthEndList(length_end_value)

                            if (i == childCount - 2) {
                                val intent = Intent(this, ChooseTreeNumberActivity::class.java)

                                intent.putExtra("gardenIdList", gardenIdList.toString())
                                intent.putExtra("mainSpecIdList", productRequirement.mainSpecIdList.toString())
                                intent.putExtra("moreSpecNameList", productRequirement.moreSpecNameList.toString())
                                intent.putExtra("dbhBaseStartList", productRequirement.dbhBaseStartList.toString())
                                intent.putExtra("dbhBaseEndList", productRequirement.dbhBaseEndList.toString())
                                intent.putExtra("dbhEndStartList", productRequirement.dbhEndStartList.toString())
                                intent.putExtra("dbhEndEndList", productRequirement.dbhEndEndList.toString())
                                intent.putExtra("lengthStartList", productRequirement.lengthStartList.toString())
                                intent.putExtra("lengthEndList", productRequirement.lengthEndList.toString())

                                startActivity(intent)
                            }
                        }
                    }
                }
            }
        }
    }

    fun onDelete(v: View) {
        parent_linear_layout.removeView(v.parent as View)
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            exitProcess(-1)
        }

        this.doubleBackToExitPressedOnce = true
        Toasty.info(this, "กด \"ย้อนกลับ\" อีกครั้งเพื่อออก ", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}
