import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.data.remote.api.pojo.gettenderlist.TenderImage

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tender_image_item.view.*

class TenderImageRecyclerViewAdapter(private val images: ArrayList<TenderImage>) :RecyclerView.Adapter<TenderImageRecyclerViewAdapter.TenderImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenderImageViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.tender_image_item, parent, false)
        return TenderImageViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = images.size

    inner class TenderImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.tender_image
    }

    override fun onBindViewHolder(holder: TenderImageViewHolder, position: Int) {
        val image = images[position]
        if (image.file != null) Picasso.get().load(image.file).into(holder.image)
    }
}