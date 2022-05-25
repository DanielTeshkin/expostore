package com.expostore.ui.fragment.product.reviews

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.getreviews.Review
import com.expostore.model.product.name
import com.expostore.model.review.ReviewModel
import com.expostore.ui.fragment.chats.loadAvatar
import com.expostore.utils.SmallImageRecyclerViewAdapter
import kotlinx.android.synthetic.main.product_review_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ReviewRecyclerViewAdapter(private val reviews: List<ReviewModel>) : RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.product_review_item, parent, false)
        return ReviewViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = reviews.size

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var author: TextView = itemView.tv_review_name
        var date: TextView = itemView.tv_review_date
        var rating: RatingBar = itemView.rb_review_rating
        var description: TextView = itemView.tv_review_description
        var images: RecyclerView = itemView.rv_review_images
        var avatar:ImageView=itemView.iv_review_image
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]

        holder.author.text = review.author.name()
       holder.avatar.loadAvatar(review.author.avatar?.file ?: "")
        if (!review.dateCreated.isNullOrEmpty())
            holder.date.text = review.dateCreated
        holder.description.text = review.text
        if (review.rating != null)
            holder.rating.rating = review.rating.toFloat()

        if (!review.images.isNullOrEmpty()) {
            holder.images.apply {
                layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
                adapter = SmallImageRecyclerViewAdapter(review.images)
            }
        }
        else holder.images.visibility = View.GONE
    }
}