package fariborz.rezara.viewholders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import fariborz.rezara.R
import fariborz.rezara.interfaces.OnVideoClickListener
import fariborz.rezara.models.MovieVideo

/**
Created by Reza*/
class VideoViewHolder(itemView: View?,
                      val context: Context,
                      val movieVideoList: List<MovieVideo>,
                      val listener: OnVideoClickListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var mVideoImage: ImageView
    init{
        mVideoImage = itemView!!.findViewById(R.id.activity_detail_trailer_poster_image)
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        val position:Int = adapterPosition
        if (position!=RecyclerView.NO_POSITION){
            listener.onVideoClickListener(movieVideoList.get(position))
        }
    }

}