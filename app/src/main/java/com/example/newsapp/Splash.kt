package com.example.newsapp

import android.animation.Animator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
var arr : List<Article> = listOf()
class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        var myanimation:LottieAnimationView=findViewById(R.id.animationView)
        var ended=false
        myanimation.playAnimation()
        myanimation.addAnimatorListener(object:Animator.AnimatorListener{
            override fun onAnimationStart(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                if(ended) {
                    startActivity(Intent(this@Splash, MainActivity::class.java))
                    finish()
                }
                myanimation.playAnimation()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationRepeat(p0: Animator?) {

            }

        })
        /** API call **/
        val retrofitbuilder= Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://newsapi.org")
            .build()
            .create(APIinterface::class.java)

        val mydata=retrofitbuilder.GetData("https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=fc4bab028eba48f6a198b9d5e1ab1a9e")

        /* Checking response and on handling response and error */
        mydata.enqueue(object : Callback<News?> {
            override fun onFailure(call: Call<News?>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<News?>, response: retrofit2.Response<News?>) {
                val mybody=response.body()
                arr=mybody!!.articles
                ended=true
            }
        })

    }
}