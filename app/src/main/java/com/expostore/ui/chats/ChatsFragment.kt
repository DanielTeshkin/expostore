package com.expostore.ui.chats

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.expostore.MainActivity
import com.expostore.R
import com.expostore.api.Retrofit
import com.expostore.api.ServerApi
import com.expostore.api.pojo.getcategory.Category
import com.expostore.api.pojo.getchats.Chat
import com.expostore.data.AppPreferences
import com.expostore.databinding.ChatsFragmentBinding
import com.expostore.utils.ChatsRecyclerViewAdapter
import com.expostore.utils.OnClickRecyclerViewListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChatsFragment : Fragment() {

    private lateinit var binding: ChatsFragmentBinding
    private lateinit var chatsViewModel: ChatsViewModel
    private lateinit var mAdapter: ChatsRecyclerViewAdapter
    private lateinit var serverApi: ServerApi

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.chats_fragment, container, false)
        chatsViewModel = ViewModelProvider(this)[ChatsViewModel::class.java]
        binding.chatsVM = chatsViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val token = AppPreferences.getSharedPreferences(requireContext()).getString("token", "")
        serverApi = Retrofit.getClient(Retrofit.BASE_URL).create(ServerApi::class.java)

        if (token != null) getChats(token)
        else Toast.makeText(requireContext(), "Токен отсутствует", Toast.LENGTH_SHORT).show()
    }

    private fun getChats(token: String){
        serverApi.getChats("Bearer $token").enqueue(object : Callback<ArrayList<Chat>> {
            override fun onResponse(call: Call<ArrayList<Chat>>, response: Response<ArrayList<Chat>>) {
                if (response.isSuccessful) {
                    if(response.body() != null){
                        mAdapter = ChatsRecyclerViewAdapter(response.body()!!)

                        binding.rvChats.apply {
                            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                            adapter = mAdapter

                        }

                        mAdapter.onClick = onChatClick()
                        setupItemTouchHelper(mAdapter,binding.rvChats)
                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Chat>>, t: Throwable) {}
        })
    }

    private fun setupItemTouchHelper(adapter: ChatsRecyclerViewAdapter,recyclerView: RecyclerView){
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(v: RecyclerView, h: RecyclerView.ViewHolder, t: RecyclerView.ViewHolder) = true
            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) = adapter.removeAt(h.adapterPosition)
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                val itemView = viewHolder.itemView

                val p = Paint()
                p.style = Paint.Style.FILL
                p.color = Color.RED
                val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                c.drawRect(background, p)

                val yPos = (itemView.top + itemView.height / 2 - (p.descent() + p.ascent()) / 2).toInt()

                val title = "Удалить"
                p.style = Paint.Style.FILL
                p.color = Color.WHITE
                p.textSize = 50F
                p.textAlign = Paint.Align.RIGHT
                c.drawText(title, itemView.right.toFloat() - 20F, yPos.toFloat(),p)

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }).attachToRecyclerView(recyclerView)
    }

    private fun onChatClick() : OnClickRecyclerViewListener {
        return object : OnClickRecyclerViewListener {
            override fun onDetailCategoryButton(category: Category) {}
            override fun onProductClick(id: String?) {}
            override fun onLikeClick(like: Boolean, id: String?) {}
            override fun onDetailCategoryProductItemClick(id: String?) {}
            override fun onChatClick() {
                val navController = Navigation.findNavController(binding.root)
                navController.navigate(R.id.action_chatsFragment_to_chatFragment)
            }
        }
    }
}