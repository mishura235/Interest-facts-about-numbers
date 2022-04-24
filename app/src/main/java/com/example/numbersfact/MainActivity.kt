package com.example.numbersfact

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private var User_Num: EditText? = null
    private var Get_Fact: Button? = null
    private var Fact: TextView? = null
    lateinit var Get_Random: Button
    lateinit var recyclerView: RecyclerView
    val URL = "http://numbersapi.com/"
    val API = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequests::class.java)


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        User_Num = findViewById(R.id.user_num)
        Get_Fact = findViewById(R.id.get_fact)
        Fact = findViewById(R.id.Fact)
        Get_Random = findViewById(R.id.get_fact2)
        recyclerView = findViewById(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        recyclerView.adapter = CustomAdapter(fillList())



        Get_Fact?.setOnClickListener {
            if (User_Num?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Enter the number", Toast.LENGTH_LONG).show()
            else {
                val num = User_Num?.text.toString()

                getFact(num)
            }
        }

        Get_Random.setOnClickListener {
            getRandom()
        }
    }

    fun getRandom(){
        CoroutineScope(Dispatchers.IO).launch {
            try{
            val response = API.randomFact().awaitResponse()
            if (response.isSuccessful) {
                val FactAboutNum = response.body()!!
                withContext(Dispatchers.Main) {
                    Fact?.text = "Fact: ${FactAboutNum.text}"
                }
            }
        }catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    applicationContext,
                    "Seems like something went wrong in random fact",
                    Toast.LENGTH_SHORT
                ).show()
                }
            }
        }
    }


    @SuppressLint("SetTextI18n")
    fun getFact(num: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try{
            val response = API.fact(num).awaitResponse()
            if (response.isSuccessful) {
                val FactAboutNum = response.body()!!
                withContext(Dispatchers.Main) {
                    Fact?.text = "Fact: ${FactAboutNum.text}"
                    }
                }
            } catch (e: java.lang.Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        applicationContext,
                        "Seems like something went wrong in fact",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        repeat(10){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    val response = API.randomFact().awaitResponse()
                    if (response.isSuccessful) {
                        val FactAboutNum = response.body()!!
                        data.add(FactAboutNum.text)

                    }
                }catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "Seems like something went wrong in list of facts",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        return data
    }


}
