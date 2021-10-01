package com.example.ad340

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    private var viewModelForecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Block Screen Shoot
        //window.setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE)


        viewModelForecastRepository = ViewModelProvider(this)[ForecastRepository::class.java]

        val zipcodeEditText = findViewById<EditText>(R.id.zipcodeEditText)
        val enterButton = findViewById<Button>(R.id.enterButton)

        enterButton.setOnClickListener {
            val zipcode = zipcodeEditText.text.toString()
            if (zipcode.length != 5) {
                Toast.makeText(this, R.string.zipcode_entry_error, Toast.LENGTH_SHORT).show()
            } else {
                viewModelForecastRepository.loadForecast(zipcode)
            }
        }


        viewModelForecastRepository.weeklyForecast.observe(this) {
            // update our list adapter
            val forecastList = findViewById<RecyclerView>(R.id.forecastList)
            forecastList.layoutManager = LinearLayoutManager(this)
            val recyclerViewAdapter = DailyForecastAdapter(it)
            forecastList.adapter = recyclerViewAdapter
        }
    }
}