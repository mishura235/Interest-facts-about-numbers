package com.example.numbersfact

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    lateinit var userNum: EditText
    lateinit var getFact: Button
    lateinit var tvFact: TextView
    private lateinit var getRandom: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FactListAdapter


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel: FactViewModel = ViewModelProvider(this).get(FactViewModel::class.java)

        viewModel.fact.observe(this, Observer {
            tvFact.text = viewModel.fact.value
        })

        viewModel.listOfFacts.observe(this,Observer{
            adapter.setFacts(viewModel.listOfFacts.value ?: mutableListOf())
        })

        userNum = findViewById(R.id.user_num)
        getFact = findViewById(R.id.get_fact)
        tvFact = findViewById(R.id.Fact)
        getRandom = findViewById(R.id.get_fact2)

        recyclerView = findViewById(R.id.rcView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = FactListAdapter()

        recyclerView.adapter = adapter

        viewModel.updateFactList()


        getFact.setOnClickListener {
            if (userNum.text.toString().trim() == "")
                Toast.makeText(this, "Enter the number", Toast.LENGTH_LONG).show()
            else {
                val num = userNum.text.toString()
                viewModel.updateFact(num)
            }
        }

        getRandom.setOnClickListener {
            viewModel.updateFact("random")
        }



    }


}
