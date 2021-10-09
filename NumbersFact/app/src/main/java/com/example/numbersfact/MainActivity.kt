package com.example.numbersfact

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var User_Num:EditText? = null
    private var Get_Fact:Button? = null
    private var Fact:TextView? = null



    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        User_Num = findViewById(R.id.user_num)
        Get_Fact = findViewById(R.id.get_fact)
        Fact = findViewById(R.id.Fact)

        Get_Fact?.setOnClickListener{
            if(User_Num?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this,"Enter the number",Toast.LENGTH_LONG).show()
            else{
                val number = User_Num?.text.toString()
                val url = "http://numbersapi.com/$number?json"

                doAsync {
                    val apiResponse = URL(url).readText()

                    Log.d("INFO", apiResponse)

                    val FactAboutNum = JSONObject(apiResponse).getString("text")

                    Fact?.text = "Fact: $FactAboutNum"

                }
            }
        }
    }
}