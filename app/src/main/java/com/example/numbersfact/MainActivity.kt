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

const val URL = "http://numbersapi.com/"

class MainActivity : AppCompatActivity() {
    private var userNum: EditText? = null
    private var getFact: Button? = null
    private var tvFact: TextView? = null
    private lateinit var getRandom: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter

    private val apiRequests = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequests::class.java)

    private val data = mutableListOf<String>()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        userNum = findViewById(R.id.user_num)
        getFact = findViewById(R.id.get_fact)
        tvFact = findViewById(R.id.Fact)
        getRandom = findViewById(R.id.get_fact2)
        recyclerView = findViewById(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = CustomAdapter()
        recyclerView.adapter = adapter

        fillList()

        getFact?.setOnClickListener {
            if (userNum?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Enter the number", Toast.LENGTH_LONG).show()
            else {
                val num = userNum?.text.toString()
                getFact(num)
            }
        }

        getRandom.setOnClickListener {
            getRandom()
        }
    }

    private fun getRandom() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiRequests.randomFact().awaitResponse()
                if (response.isSuccessful) {
                    val factAboutNum = response.body()!!
                    withContext(Dispatchers.Main) {
                        tvFact?.text = "Fact: ${factAboutNum.text}"
                    }
                }
            } catch (e: Exception) {
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
            try {
                val response = apiRequests.fact(num).awaitResponse()
                if (response.isSuccessful) {
                    val factAboutNum = response.body()!!
                    withContext(Dispatchers.Main) {
                        tvFact?.text = "Fact: ${factAboutNum.text}"
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

    private fun fillList() {
        repeat(10) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val response = apiRequests.randomFact().awaitResponse()
                    if (response.isSuccessful) {
                        val factAboutNum = response.body()!!
                        data.add(factAboutNum.text)
                        adapter.addData(factAboutNum.text) // закидываем данные в адаптер (мне так больше нравится) :)
                    }
                } catch (e: Exception) {
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
    }
}
