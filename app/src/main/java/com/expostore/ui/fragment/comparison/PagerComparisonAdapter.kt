package com.expostore.ui.fragment.comparison

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.expostore.databinding.ComparisonItemBinding
import com.expostore.databinding.ItemImageBinding
import com.expostore.extension.load
import com.expostore.model.product.ProductModel
import com.expostore.ui.fragment.profile.profile_edit.click

class PagerComparisonAdapter : RecyclerView.Adapter<PagerComparisonAdapter.PagerComparisonViewHolder>() {

    var onGoClickListener: ((ProductModel) -> Unit)? = null
    var onDeleteClickListener: ((String) -> Unit)? = null

    var items: List<ProductModel> = emptyList()
        set(value) {
            field = value
            if (field.isNotEmpty()) notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PagerComparisonViewHolder(
            ComparisonItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PagerComparisonViewHolder, position: Int) {
        holder.bind(items[position])

    }

    override fun getItemCount() = items.size


    inner class PagerComparisonViewHolder(private val binding: ComparisonItemBinding) :
        RecyclerView.ViewHolder(binding.root) {



        fun bind(model:ProductModel) {
              binding.apply {
                  iconProduct.load(model.images[0].file)
                  nameProduct.text=model.name
                  priceProduct.text=model.price
                  delete.click { onDeleteClickListener?.invoke(model.id) }
                  go.click { onGoClickListener?.invoke(model) }
              }

/*            binding.root.setOnClickWithDelay {
                handleImageClick(it.context, items, absoluteAdapterPosition)
            }*/
        }
    }

/*    private fun handleImageClick(context: Context, items: List<String>, position: Int) {
        StfalconImageViewer.Builder(context, items) { view, image ->
            Glide.with(context)
                .load(image)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(view)
        }
            .withStartPosition(position)
            .show(true)
    }*/
}