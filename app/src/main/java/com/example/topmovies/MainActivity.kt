package com.example.topmovies

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.room.Room
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.topmovies.adapter.MovieAdapter
import com.example.topmovies.api.Client
import com.example.topmovies.database.AppDB
import com.example.topmovies.models.Movie
import com.example.topmovies.models.MoviesResponse
import com.example.topmovies.service.ConnectivityReceiver
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    var movieList = ArrayList<Movie>()
    var recyclerView: RecyclerView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViews()

        loadJSON()
    }

    private fun initViews() {

        recyclerView = findViewById(R.id.RecyclerViewId)
        movieList = ArrayList()
        recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.itemAnimator=DefaultItemAnimator()
    }
    //retrofit for popular movies
    private fun loadJSON(){
        //var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        //progressBar.visibility = View.VISIBLE
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in API key from themoviedb.org", Toast.LENGTH_LONG)
                    .show()
                //progressBar.visibility = View.GONE
                return
            }
            val apiService = Client.create()
            val call: Call<MoviesResponse> = apiService.getPopularMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN)

            call.enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                    t.printStackTrace()                }

                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        //progressBar.visibility=View.GONE
                        movieList = response.body()!!.results
                        recyclerView?.adapter = MovieAdapter(applicationContext, movieList)
                        recyclerView?.adapter?.notifyDataSetChanged()
                    }
                }
            })
        }catch (e: Exception){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }
    //retrofit for toprated movies
    private fun loadJSON2(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in API key from themoviedb.org", Toast.LENGTH_LONG)
                    .show()
                return
            }
            val apiService = Client.create()
            val call: Call<MoviesResponse> = apiService.getTopRatedMovies(BuildConfig.THE_MOVIE_DB_API_TOKEN)

            call.enqueue(object : Callback<MoviesResponse> {
                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
                    t.printStackTrace()                }

                override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                    if (response.isSuccessful) {
                        movieList = response.body()!!.results
                        recyclerView?.adapter = MovieAdapter(applicationContext, movieList)
                    }
                }
            })
        }catch (e: Exception){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }
    //menu for popular and topRated movies
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.topRated -> {
                loadJSON2()
                true
            }
            R.id.popular -> {
                loadJSON()
                true
            }
            R.id.favorite -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
