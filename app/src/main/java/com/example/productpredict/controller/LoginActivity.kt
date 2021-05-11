package com.example.productpredict.controller

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.text.Editable
import android.util.Log
import com.example.productpredict.R
import com.example.productpredict.httpController.AnApi
import com.example.productpredict.httpController.RetrofitClient
import com.example.productpredict.model.SubPlotName
import com.example.productpredict.model.User
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var jsonApi: AnApi


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        jsonApi = RetrofitClient.instance!!.create(AnApi::class.java)

        loginButton.setOnClickListener {
            val username = userNameEDT.text!!
            val password = passwordEDT.text!!

            if (username.isNotEmpty() || password.isNotEmpty()){
                jsonApi.verifyUser(username.toString(), password.toString()).enqueue(object: Callback<User>{
                    override fun onFailure(call: Call<User>, t: Throwable) {}
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        val user = response.body() as User

                        if(user.result == "ok"){
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)

                            val pref = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity)
                            val editor = pref.edit()
                            editor.putString("currentUser", user.username).apply()

                            startActivity(intent)
                        }
                        else {
                            Toasty.warning(this@LoginActivity, user.result, 2000, true).show()
                        }
                    }
                })
            }
            else {
                Toasty.warning(this@LoginActivity, "กรุณาใส่ username password", 2000, true).show()
            }



        }
    }
}