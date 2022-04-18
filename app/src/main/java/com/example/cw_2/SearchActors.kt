package com.example.cw_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.cw_2.database.AppDatabase
import com.example.cw_2.model.Movie
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SearchActors : AppCompatActivity() {
    lateinit var tv: TextView
    private lateinit var editText: EditText
    private lateinit var searchByActorName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_actors)
        tv = findViewById(R.id.tv_actors)
        editText = findViewById(R.id.edit_text_actors)
        val searchBtn = findViewById<Button>(R.id.search_actor)

        searchBtn.setOnClickListener {
            val db = Room.databaseBuilder(
                this, AppDatabase::class.java,
                "myDatabase"
            ).build()
            val movieDao = db.movieDao()
            searchByActorName = editText.text.toString().uppercase()
            if (searchByActorName.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter a Movie Name.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                tv.text = ""
                runBlocking {
                    launch {
                        val movies: List<Movie> = movieDao.getAllByActor(searchByActorName)
                        for (movie in movies) {
                            tv.append("Movie Title: ${movie.title}\n")
                        }
                    }
                }
            }
            tv.text
        }
    }
}