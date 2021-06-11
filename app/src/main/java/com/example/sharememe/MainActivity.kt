package com.example.sharememe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

var curr_url="www.google.com"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val image: ImageView = findViewById(R.id.imageView)
        val btnNext: Button = findViewById(R.id.next)
        val btnShare: Button =findViewById(R.id.share)
        load(image)
        btnNext.setOnClickListener{
            load(image)
        }
        btnShare.setOnClickListener{
            val intent =Intent(Intent.ACTION_SEND)
            intent.type="text/plain"
            intent.putExtra(Intent.EXTRA_TEXT,"hey checkout this cool meme, i got from Reddit $curr_url")
            val chooser= Intent.createChooser(intent,"sharing meme")
            startActivity(chooser)
        }
    }

    fun load(imageView: ImageView){
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                 curr_url=response.getString("url")
                Glide.with(this).load(curr_url).into(imageView)
            },
            {
                 Toast.makeText(this,"Some Error Occured",Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }
}