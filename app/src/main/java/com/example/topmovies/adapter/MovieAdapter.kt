package com.example.topmovies.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topmovies.DetailActivity
import com.example.topmovies.R
import com.example.topmovies.api.CostomItemClickListener
import com.example.topmovies.models.Movie

class MovieAdapter(val context: Context,val movieList: ArrayList<Movie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie: Movie = movieList[position]

        holder.title.text = movie.title

        holder.setOnCostomClickListener(object: CostomItemClickListener{
            override fun onCostomItemClickListener(view: View, pos: Int) {
                var pos = holder.adapterPosition
                if(pos != RecyclerView.NO_POSITION){
                    var clickDataItem = movieList[pos]
                    var intent = Intent(context,DetailActivity::class.java)
                    intent.putExtra("title",clickDataItem.title)
                    intent.putExtra("original_title",clickDataItem.originalTitle)
                    intent.putExtra("overview",clickDataItem.overview)
                    intent.putExtra("rating",clickDataItem.rating)
                    intent.putExtra("release",clickDataItem.releaseDate)
                    intent.putExtra("poster","https://image.tmdb.org/t/p/w500${clickDataItem.posterPath}")
                    context.startActivity(intent)
                }
            }
        })
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .placeholder(R.drawable.loading)
            .into(holder.poster)
    }


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var costomItemClickListener: CostomItemClickListener?= null


        val title = itemView.findViewById<TextView>(R.id.recyclerTitle)
        val poster = itemView.findViewById<ImageView>(R.id.thumbnail)
        init {
            itemView.setOnClickListener(this)
        }


        fun setOnCostomClickListener(itemClickListener: CostomItemClickListener){
            this.costomItemClickListener = itemClickListener
        }

        override fun onClick(p0: View?) {
            this.costomItemClickListener!!.onCostomItemClickListener(p0!!,adapterPosition)
        }
    }

}