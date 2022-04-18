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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class SearchMovie : AppCompatActivity() {
    lateinit var tv: TextView
    private lateinit var title: String
    private lateinit var year: String
    private lateinit var rated: String
    private lateinit var released: String
    private lateinit var runtime: String
    private lateinit var plot: String
    private lateinit var genre: String
    private lateinit var director: String
    private lateinit var writer: String
    private lateinit var actors: String
    private lateinit var editText: EditText
    private lateinit var searchByMovieName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_movie)
        tv = findViewById(R.id.tv_movie)
        val saveBtn = findViewById<Button>(R.id.save)
        val retrieveBtn = findViewById<Button>(R.id.retrieve_movies)
        editText = findViewById(R.id.edit_text)


        retrieveBtn.setOnClickListener {
            // collecting all the JSON string
            var stb = StringBuilder()
            searchByMovieName = editText.text.toString()

            if (searchByMovieName.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter a Movie Name.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val urlString = "https://www.omdbapi.com/?t=$searchByMovieName&apikey=ee34803f"
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
                tv.text =
                    "Title: " + title+ "\n"+
                            "Year: " + year + "\n" +
                            "Rated: " + rated + "\n" +
                            "Released: " + released + "\n" +
                            "Runtime: " + runtime + "\n" +
                            "Genre: " + genre + "\n" +
                            "Director: " + director + "\n" +
                            "Writer: " + writer + "\n" +
                            "Actors: " + actors + "\n" +
                            "Plot: " + plot
            }
        }

        saveBtn.setOnClickListener {
            val db = Room.databaseBuilder(
                this, AppDatabase::class.java,
                "myDatabase"
            ).build()
            searchByMovieName = editText.text.toString()
            if (searchByMovieName.isEmpty()) {
                Toast.makeText(applicationContext, "Please enter a Movie Name.", Toast.LENGTH_SHORT)
                    .show()
            } else {
                val movieDao = db.movieDao()
                runBlocking {
                    launch {
                        val movies: List<Movie> = movieDao.getAll()
                        val id = movies.size
                        val newMovie = Movie(
                            id + 1,
                            title,
                            year,
                            rated,
                            released,
                            runtime,
                            genre,
                            director,
                            writer,
                            actors,
                            plot
                        )
                        movieDao.insertMovies(newMovie)
                        Toast.makeText(
                            applicationContext,
                            "The Movie has successfully been added",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun parseJSON(stb: java.lang.StringBuilder) {
// this contains the full JSON returned by the Web Service
        val json = JSONObject(stb.toString())
        title = json.getString("Title")
        year = json.getString("Year")
        rated = json.getString("Rated")
        released = json.getString("Released")
        runtime = json.getString("Runtime")
        plot = json.getString("Plot")
        genre = json.getString("Genre")
        director = json.getString("Director")
        writer = json.getString("Writer")
        actors = json.getString("Actors")
    }
}






