import android.content.Context
import com.expostore.utils.Dots
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.expostore.R
import com.expostore.api.pojo.getcategory.CategoryProductImage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.detail_product_image_item.view.*

class ProductImageRecyclerViewAdapter(private val images: ArrayList<CategoryProductImage>?, private val context: Context) : RecyclerView.Adapter<ProductImageRecyclerViewAdapter.ProductImagesViewHolder>() {

    private var dots: Dots = Dots()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImagesViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.detail_product_image_item, parent, false)
        return ProductImagesViewHolder(v)
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = images!!.size

    inner class ProductImagesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.iv_detail_product
        var llDots: LinearLayout = itemView.ll_dot
        var btnLike: ImageButton = itemView.ib_detail_product_like
    }

    override fun onBindViewHolder(holder: ProductImagesViewHolder, position: Int) {
        val image = images!![position]

        if (position == 0) holder.btnLike.visibility = View.VISIBLE
        else holder.btnLike.visibility = View.GONE

        dots.addDot(context,images.size,15,0,15,0,holder.llDots,R.drawable.dot_inactive)
        dots.currentDot(context,position,holder.llDots,R.drawable.dot_active,R.drawable.dot_inactive)


        if (image.file != null) Picasso.get().load(image.file).into(holder.image)
    }
}