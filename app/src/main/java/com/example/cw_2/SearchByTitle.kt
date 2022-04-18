package com.example.cw_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class SearchByTitle : AppCompatActivity() {
    lateinit var tv: TextView
    private lateinit var title: String
    private var movieList: MutableList<String> = mutableListOf()
    private lateinit var editText: EditText
    private lateinit var searchByTitle: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_by_title)
        tv = findViewById(R.id.tv_title)
        val searchBtn = findViewById<Button>(R.id.search_by_title)
        editText = findViewById(R.id.edit_text_title)


        searchBtn.setOnClickListener {
            // collecting all the JSON string
            var stb = StringBuilder()
            searchByTitle = editText.text.toString()
            tv.text= ""
            if (searchByTitle.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter a Title.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val urlString = "https://www.omdbapi.com/?s=$searchByTitle&apikey=ee34803f"
                val url = URL(urlString)
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                runBlocking {
                    launch {
                        // run the code of the coroutine in a new thread
                        withContext(Dispatchers.IO) {
                            var bf = BufferedReader(InputStreamReader(con.inputStream))
                            var line: String? = bf.readLine()
                            while (line != null) {
                                stb.append(line + "\n")
                                line = bf.readLine()
                            }
                            parseJSON(stb)
                        }
                    }
                }
                for (movie in movieList){
                    tv.append("Movie Title: ${movie}\n")
                }
            }
        }
    }

    private fun parseJSON(stb: java.lang.StringBuilder) {
// this contains the JSON returned by the Web Service
        val json = JSONObject(stb.toString())
        val jsonArray: JSONArray = json.getJSONArray("Search")
        movieList.clear()
        for (movie in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray[movie] as JSONObject
            title = jsonObject.getString("Title")
            movieList.add(title)
        }
    }
}