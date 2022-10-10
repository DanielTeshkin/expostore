package com.expostore.ui.general.other

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
                    onClickBottomFragment: OnClickBottomSheetFragment<ProductModel>,flag:Boolean){



    val bottomSheetDialog = BottomSheetDialog(context)
    bottomSheetDialog.setContentView(R.layout.personal_selection_fragment)
    val note = bottomSheetDialog.findViewById<LinearLayout>(R.id.note)
    if (model.elected.notes.isNotEmpty()) bottomSheetDialog.findViewById<TextView>(R.id.note_text)?.text="Редактировать заметку"
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
        onClickBottomFragment.addToComparison(model.id)
        bottomSheetDialog.hide()
    }
    val chat=bottomSheetDialog.findViewById<LinearLayout>(R.id.chat_write)
    chat?.click {
        onClickBottomFragment.chatCreate(model.id)
        bottomSheetDialog.hide()
    }
    val share=bottomSheetDialog.findViewById<LinearLayout>(R.id.share)
    share?.click {
        onClickBottomFragment.share(model.qrcode.qr_code_image)
        bottomSheetDialog.hide()
    }
    val personalSelection=bottomSheetDialog.findViewById<LinearLayout>(R.id.personal)
    if(model.communicationType=="chatting")personalSelection?.visibility= View.GONE
    personalSelection?.click { showSelection(context,list?: listOf(),model.id,onClickBottomFragment)
    bottomSheetDialog.hide()
    }
val block=bottomSheetDialog.findViewById<LinearLayout>(R.id.block)
    block?.click {
        onClickBottomFragment.block(model)
        bottomSheetDialog.hide()
    }
    if(flag){
        val delete= bottomSheetDialog.findViewById<LinearLayout>(R.id.delete)
        delete?.visibility=View.VISIBLE
        delete?.click { onClickBottomFragment.deleteFromSelection(model)
            bottomSheetDialog.hide()
        }
    }

    bottomSheetDialog.show()
}

fun showSelection(
    context: Context,
    list: List<SelectionModel>,
    product: String,
    onClickBottomSheetFragment: OnClickBottomSheetFragment<ProductModel>
){
     val bottomSheetDialog=BottomSheetDialog(context)
    bottomSheetDialog.setContentView(R.layout.list_selections)
    val createSelection=bottomSheetDialog.findViewById<LinearLayout>(R.id.create)
    createSelection?.click { onClickBottomSheetFragment.createSelection(product)
    bottomSheetDialog.hide()
    }
    val selections=bottomSheetDialog.findViewById<RecyclerView>(R.id.rv_selection)
    selections?.apply {
        layoutManager=LinearLayoutManager(context)
        adapter= SelectionBottomAdapter(list,onClickBottomSheetFragment,product,bottomSheetDialog)
    }

    bottomSheetDialog.show()
}

class SelectionBottomAdapter(
    private val list: List<SelectionModel>,
    private val onClickPersonalSelectionFragment: OnClickBottomSheetFragment<ProductModel>,
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


interface OnClickBottomSheetFragment< T>{
    fun createSelection(product:String){}
    fun addToSelection(id:String,product: String){}
    fun call(username:String)
    fun createNote(product:T)
    fun chatCreate(id:String)
    fun share(id: String)
    fun block(model :T)
    fun addToComparison(id: String){}
    fun deleteFromSelection(model: T){}

}


fun showBottomSheetTender(
    context: Context, model:TenderModel,
    onClickBottomFragment: OnClickBottomSheetFragment< TenderModel>
){

    val bottomSheetDialog = BottomSheetDialog(context)
    bottomSheetDialog.setContentView(R.layout.personal_selection_fragment)
    val note = bottomSheetDialog.findViewById<LinearLayout>(R.id.note)
    if (model.elected.notes.isNotEmpty()) bottomSheetDialog.findViewById<TextView>(R.id.note_text)?.text="Редактировать заметку"
    note?.click {
        onClickBottomFragment.createNote(model)
        bottomSheetDialog.hide() }
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
    share?.visibility=View.GONE
    val personalSelection=bottomSheetDialog.findViewById<LinearLayout>(R.id.personal)
    if(model.communicationType=="chatting")calling?.visibility= View.GONE
    personalSelection?.visibility=View.GONE
    compare?.visibility=View.GONE
    val block=bottomSheetDialog.findViewById<LinearLayout>(R.id.block)
    block?.click {
        onClickBottomFragment.block(model)
        bottomSheetDialog.hide()
    }


    bottomSheetDialog.show()
}



