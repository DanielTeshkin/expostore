package com.expostore.ui.fragment.search.other

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.expostore.R
import com.expostore.databinding.CategorySingleItemBinding
import com.expostore.model.category.SelectionModel
import com.expostore.model.product.ProductModel
import com.expostore.model.tender.TenderModel
import com.expostore.ui.fragment.profile.profile_edit.click
import com.google.android.material.bottomsheet.BottomSheetDialog


fun showBottomSheet(context: Context, model:ProductModel, list: List<SelectionModel>?,
                    onClickBottomFragment: OnClickBottomSheetFragment,personalOrNot:Boolean=false){



    val bottomSheetDialog = BottomSheetDialog(context)
    bottomSheetDialog.setContentView(R.layout.personal_selection_fragment)
    val note = bottomSheetDialog.findViewById<LinearLayout>(R.id.note)
    note?.click {
        onClickBottomFragment.createNote(model)
        bottomSheetDialog.hide()
    }


    val calling= bottomSheetDialog.findViewById<LinearLayout>(R.id.calling)
    calling?.click {
        onClickBottomFragment.call(model.author.username)
        bottomSheetDialog.hide()
    }
    val compare = bottomSheetDialog.findViewById<LinearLayout>(R.id.compare)
    compare?.click {
        onClickBottomFragment.share()
        bottomSheetDialog.hide()
    }
    val chat=bottomSheetDialog.findViewById<LinearLayout>(R.id.chat_write)
    chat?.click {
        onClickBottomFragment.chatCreate(model.id)
        bottomSheetDialog.hide()
    }
    val share=bottomSheetDialog.findViewById<LinearLayout>(R.id.share)
    share?.click {
        onClickBottomFragment.share()
        bottomSheetDialog.hide()
    }
    val personalSelection=bottomSheetDialog.findViewById<LinearLayout>(R.id.personal)
    if(model.communicationType=="chatting")personalSelection?.visibility= View.GONE
    personalSelection?.click { showSelection(context,list?: listOf(),model.id,onClickBottomFragment)
    bottomSheetDialog.hide()
    }
val block=bottomSheetDialog.findViewById<LinearLayout>(R.id.block)
    block?.click {
        onClickBottomFragment.block()
        bottomSheetDialog.hide()
    }

    bottomSheetDialog.show()
}

fun showSelection(
    context: Context,
    list: List<SelectionModel>,
    product: String,
    onClickBottomSheetFragment: OnClickBottomSheetFragment
){
     val bottomSheetDialog=BottomSheetDialog(context)
    bottomSheetDialog.setContentView(R.layout.list_selections)
    val createSelection=bottomSheetDialog.findViewById<LinearLayout>(R.id.create)
    createSelection?.click { onClickBottomSheetFragment.createSelection(product)
    bottomSheetDialog.hide()
    }
    val selections=bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_selections)
    selections?.apply {
        layoutManager=LinearLayoutManager(context)
        adapter=SelectionBottomAdapter(list,onClickBottomSheetFragment,product,bottomSheetDialog)
    }

    bottomSheetDialog.show()
}

class SelectionBottomAdapter(
    private val list: List<SelectionModel>,
    private val onClickPersonalSelectionFragment: OnClickBottomSheetFragment,
    private val product: String,
   private val bottomSheetDialog: BottomSheetDialog
):
    RecyclerView.Adapter<SelectionBottomAdapter.SelectionHolder>() {
    inner class SelectionHolder(val binding:CategorySingleItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(selectionModel: SelectionModel){
            binding.category.text=selectionModel.name
            binding.llAddProductConnections.click {
                  onClickPersonalSelectionFragment.addToSelection(selectionModel.id, product =product )
                bottomSheetDialog.hide()

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectionHolder {
        return  SelectionHolder(CategorySingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SelectionHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int =list.size
}

interface OnClickBottomSheetFragment{
    fun createSelection(product:String)
    fun addToSelection(id:String,product: String)
    fun call(username:String)
    fun createNote(product:ProductModel)
    fun chatCreate(id:String)
    fun share()
    fun block()

}

interface OnClickBottomSheetTenderFragment{

    fun call(username:String)
    fun createNote(id:String)
    fun chatCreate(id:String)
    fun share()
    fun block()

}
fun showBottomSheetTender(context: Context, model:TenderModel,
                    onClickBottomFragment: OnClickBottomSheetTenderFragment){



    val bottomSheetDialog = BottomSheetDialog(context)
    bottomSheetDialog.setContentView(R.layout.personal_selection_fragment)
    val note = bottomSheetDialog.findViewById<LinearLayout>(R.id.note)
    note?.click {
        onClickBottomFragment.createNote(model.id)
        bottomSheetDialog.hide()
    }
    val calling= bottomSheetDialog.findViewById<LinearLayout>(R.id.calling)
    calling?.click {
        onClickBottomFragment.call(model.author.username)
        bottomSheetDialog.hide()
    }
    val compare = bottomSheetDialog.findViewById<LinearLayout>(R.id.compare)

    val chat=bottomSheetDialog.findViewById<LinearLayout>(R.id.chat_write)
    chat?.click {
        onClickBottomFragment.chatCreate(model.id)
        bottomSheetDialog.hide()
    }
    val share=bottomSheetDialog.findViewById<LinearLayout>(R.id.share)
    share?.click {
        onClickBottomFragment.share()
        bottomSheetDialog.hide()
    }
    val personalSelection=bottomSheetDialog.findViewById<LinearLayout>(R.id.personal)
    if(model.communicationType=="chatting")calling?.visibility= View.GONE
    personalSelection?.visibility=View.GONE
    compare?.visibility=View.GONE
    val block=bottomSheetDialog.findViewById<LinearLayout>(R.id.block)
    block?.click {
        onClickBottomFragment.block()
        bottomSheetDialog.hide()
    }


    bottomSheetDialog.show()
}