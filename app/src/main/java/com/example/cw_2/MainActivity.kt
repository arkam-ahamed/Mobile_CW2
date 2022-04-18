package com.example.cw_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.cw_2.database.AppDatabase
import com.example.cw_2.model.Movie
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val addBtn = findViewById<Button>(R.id.add)
        val searchMoviesBtn = findViewById<Button>(R.id.search_movies)
        val searchActorsBtn = findViewById<Button>(R.id.search_actors)
        val searchTitleBtn = findViewById<Button>(R.id.search_title)
        val image = findViewById<ImageView>(R.id.imageView)

        val db = Room.databaseBuilder(
            this, AppDatabase::class.java,
            "myDatabase").build()

        //When the SearchMovies button is clicked the app will redirect to the activity_Search.xml file
        searchMoviesBtn.setOnClickListener {
            val intent = Intent(this, SearchMovie::class.java)
            startActivity(intent)
        }

        //When the SearchActors button is clicked the app will redirect to the activity_searchActors.xml.xml file
        searchActorsBtn.setOnClickListener {
            val intent = Intent(this, SearchActors::class.java)
            startActivity(intent)
        }

        //When the SearchTitle button is clicked the app will redirect to the activity_searchTitle.xml file
        searchTitleBtn.setOnClickListener {
            val intent = Intent(this, SearchByTitle::class.java)
            startActivity(intent)
        }

        addBtn.setOnClickListener {
            runBlocking {
                launch {
                    val movie = Movie(
                        1,
                        "The Shawshank Redemption",
                        "1994",
                        "R",
                        "14 Oct 1994",
                        "142 min",
                        "drama",
                        "Frank Darabont",
                        "Stephen King",
                        "Frank Darabont",
                        "Two imprisoned men bond over a number of years, finding solace\n" +
                                "and eventual redemption through acts of common decency"
                    )
                    val movie1 = Movie(
                        2,
                        "Batman: The Dark Knight Returns, Part 1",
                        "2012",
                        "PG-13",
                        "25 Sep 2012",
                        "76 min",
                        "Animation, Action, Crime, Drama, Thriller",
                        "Jay Oliva",
                        "Bob Kane (character created by: Batman), Frank Miller (comic book), Klaus Janson (comic book), Bob Goodman",
                        "Peter Weller, Ariel Winter, David Selby, Wade Williams",
                        "Batman has not been seen for ten years. A new breed\n" +
                                "of criminal ravages Gotham City, forcing 55-year-old Bruce Wayne back\n" +
                                "into the cape and cowl. But, does he still have what it takes to fight\n" +
                                "crime in a new era?"
                    )
                    val movie2 = Movie(
                        3,
                        "The Lord of the Rings: The Return of the King",
                        "2003",
                        "PG-13",
                        "17 Dec 2003",
                        "201 min",
                        "Action, Adventure, Drama",
                        "Peter Jackson",
                        "J.R.R. Tolkien, Fran Walsh, Philippa Boyens",
                        "Elijah Wood, Viggo Mortensen, Ian McKellen",
                        "Gandalf and Aragorn lead the World of Men against Sauron's\n" +
                                "army to draw his gaze from Frodo and Sam as they approach Mount Doom\n" +
                                "with the One Ring"
                    )
                    val movie3 = Movie(
                        4,
                        "Inception",
                        "2010",
                        "PG-13",
                        "16 Jul 2010",
                        "148 min",
                        "Action, Adventure, Sci-Fi",
                        "Christopher Nolan",
                        "Christopher Nolan",
                        "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellio",
                        "A thief who steals corporate secrets through the use of\n" +
                                "dream-sharing technology is given the inverse task of planting an idea\n" +
                                "into the mind of a C.E.O., but his tragic past may doom the project\n" +
                                "and his team to disaster.\""
                    )
                    val movie4 = Movie(
                        5,
                        "The Matrix",
                        "1999",
                        "R",
                        "31 Mar 1999",
                        "136 min",
                        "Action, Sci-Fi",
                        "Lana Wachowski, Lilly Wachowski",
                        "Lilly Wachowski, Lana Wachowski",
                        "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss",
                        "When a beautiful stranger leads computer hacker Neo to a\n" +
                                "forbidding underworld, he discovers the shocking truth--the life he\n" +
                                "knows is the elaborate deception of an evil cyber-intelligence."
                    )
                    val movieDao = db.movieDao()
                    movieDao.insertMovies(movie, movie1, movie2, movie3, movie4)
                    Toast.makeText(
                        applicationContext,
                        "Movies add to the Database successfully.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
