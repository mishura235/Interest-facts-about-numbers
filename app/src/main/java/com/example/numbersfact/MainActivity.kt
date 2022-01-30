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
import com.example.numbersfact.api.ApiRequests
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var User_Num:EditText? = null
    private var Get_Fact:Button? = null
    private var Fact:TextView? = null
    lateinit var Get_Random:Button
    val URL = "http://numbersapi.com/"
    val API = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequests::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        User_Num = findViewById(R.id.user_num)
        Get_Fact = findViewById(R.id.get_fact)
        Fact = findViewById(R.id.Fact)
        Get_Random = findViewById(R.id.get_fact2)

        Get_Fact?.setOnClickListener{
            if(User_Num?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this,"Enter the number",Toast.LENGTH_LONG).show()
            else{
                val num = User_Num?.text.toString()

                getFact(num)

            }
        }
        Get_Random.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch{
                val response = API.randomFact().awaitResponse()
                if (response.isSuccessful){
                    val FactAboutNum = response.body()!!
                    withContext(Dispatchers.Main){
                        Fact?.text = "Fact: ${FactAboutNum.text}"
                    }

                }
            }
        }
    }


    fun getFact(num: String) {
        CoroutineScope(Dispatchers.IO).launch{
            val response = API.fact(num).awaitResponse()
            if (response.isSuccessful){
                val FactAboutNum = response.body()!!
                withContext(Dispatchers.Main){
                    Fact?.text = "Fact: ${FactAboutNum.text}"
                }
            }
        }
    }
}
