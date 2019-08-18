package com.example.topmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.topmovies.adapter.MovieAdapter
import com.example.topmovies.api.Client
import com.example.topmovies.models.Movie
import com.example.topmovies.models.MoviesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var movieList = ArrayList<Movie>()
    var swipeContainer: SwipeRefreshLayout? = null
  //  var progressBar: ProgressBar? = null
    var recyclerView: RecyclerView ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
//        swipeContainer?.setColorSchemeResources(android.R.color.holo_blue_bright)
//        swipeContainer?.setOnRefreshListener {
//            initViews()
//            Toast.makeText(this,"Movie Refreshed",Toast.LENGTH_SHORT).show()
//        }
        loadJSON()
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.RecyclerViewId)
        movieList = ArrayList()
        var adapter = MovieAdapter(this,movieList)

        recyclerView?.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        recyclerView?.itemAnimator=DefaultItemAnimator()


    }

    private fun loadJSON(){
        try {
            if (BuildConfig.THE_MOVIE_DB_API_TOKEN.isEmpty()) {
                Toast.makeText(applicationContext, "Please fill in API key from themoviedb.org", Toast.LENGTH_LONG)
                    .show()
              //  progressBar!!.visibility = View.GONE
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
                        movieList = response.body()!!.results
                        recyclerView?.adapter = MovieAdapter(applicationContext, movieList)
//                        if (swipeContainer!!.isRefreshing) {
//                            swipeContainer!!.isRefreshing = false
//                        }
                        swipeContainer?.isRefreshing=false
                    //    progressBar!!.visibility = View.GONE
                    }
                }
            })
        }catch (e: Exception){
            Toast.makeText(this, "Error", Toast.LENGTH_LONG).show()
        }
    }

}
