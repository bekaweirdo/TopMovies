package com.example.topmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.topmovies.database.AppDB
import com.example.topmovies.database.EmpEntity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val imageView = findViewById<ImageView>(R.id.poster)
        val title = findViewById<TextView>(R.id.title)
        val overview = findViewById<TextView>(R.id.overview)
        val originalTitle = findViewById<TextView>(R.id.originalTitle)
        val rating = findViewById<TextView>(R.id.rating)
        val release = findViewById<TextView>(R.id.release)


        val thumbnail: String = getIntent().getStringExtra("poster")
        val movieName: String = getIntent().getStringExtra("title")
        val plot: String = getIntent().getStringExtra("overview")
        val releaseDate: String = getIntent().getStringExtra("release")
        val original: String = getIntent().getStringExtra("original_title")
        val rate: String = getIntent().getStringExtra("rating")

        Glide.with(this)
            .load(thumbnail)
            .placeholder(R.drawable.loading)
            .into(imageView)
        title.text = movieName
        overview.text = plot
        release.text = releaseDate
        rating.text = rate
        originalTitle.text = original


        //Database
        var db = Room.databaseBuilder(applicationContext, AppDB::class.java, "MovieDB").allowMainThreadQueries().build()

        val FabButton = findViewById<FloatingActionButton>(R.id.favouriteButton)
        FabButton.setOnClickListener {
            var movieDb = EmpEntity()
            movieDb.empTitle = movieName
            movieDb.empOverView = plot
            movieDb.empOriginalTitle = original
            movieDb.empRelease = releaseDate
            movieDb.empRating = rate
            movieDb.empPosterPath = thumbnail

            db.empDAO().saveAll(movieDb)
            Snackbar.make(it, "Movie added to Favourites", Snackbar.LENGTH_LONG).setAction("Action", null)
                .show()
        }

        //initCollapseToolbar()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}