package com.example.newsapp

import android.content.Context
import android.gesture.Gesture
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.newsapp.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
/** Global Variables **/
lateinit var mycontext:Context


class MainActivity : AppCompatActivity(){
    private lateinit var adapter: newsadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        mycontext=this

        adapter = newsadapter(arr)
        var rv = findViewById<RecyclerView>(R.id.rcv)
        rv.layoutManager = LinearLayoutManager(this@MainActivity)
        rv.adapter = adapter

        /** Searching keywords and making api call on keywords basis **/
        binding.button.setOnClickListener(){
            var keyword=binding.keywords.text.toString()

            /* If search bar is empty */
            if(keyword == "")
            {
                return@setOnClickListener
            }

            /* API call */
            val retrofitbuilder= Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org")
                .build()
                .create(APIinterface::class.java)

            val mydata = retrofitbuilder.GetData("https://newsapi.org/v2/everything?q=${keyword}&apiKey=fc4bab028eba48f6a198b9d5e1ab1a9e")

            /* Checking response and on handling response and error */
            mydata.enqueue(object : Callback<News?> {
                override fun onFailure(call: Call<News?>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<News?>, response: retrofit2.Response<News?>) {
                    val mybody=response.body()
                    arr=mybody!!.articles

                    /* Checking if there is no response from API call */
                    if(arr.size!=0) {
                        adapter = newsadapter(arr)
                        var rv = findViewById<RecyclerView>(R.id.rcv)
                        rv.layoutManager = LinearLayoutManager(this@MainActivity)
                        rv.adapter = adapter
                    }
                    else{
                        Toast.makeText(applicationContext, "No records found, try a valid keyword and try again..", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }




}