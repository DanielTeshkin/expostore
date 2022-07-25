package com.expostore.ui.fragment.favorites

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.expostore.R
import com.expostore.ui.fragment.favorites.tabs.favorites.TabFavoritesFragment
import com.expostore.ui.fragment.favorites.tabs.savedsearches.TabSavedSearchesFragment
import com.expostore.ui.fragment.favorites.tabs.selections.TabSelectionsFragment
import com.expostore.ui.fragment.favorites.tabs.tenders.FavoriteTendersFragment
import kotlinx.android.synthetic.main.favorites_tab_item.view.*

class FavoritesTabsViewPagerAdapter(
    val fragment: Fragment,
    val context: Context,
   val installClickListener: FavoritesClickListener
): FragmentStateAdapter(fragment) {

    private val tabs = listOf("Продукты","Тендеры", "Сохранённые поиски", "Подборки")

    override fun getItemCount(): Int = tabs.size

    @SuppressLint("InflateParams")
    fun getTabView(position: Int): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.favorites_tab_item, null)
        view.text.text = tabs[position]

        return view
    }

    override fun createFragment(position: Int): Fragment {
        var result: Fragment? = null

        when(position){
            0 -> { result = TabFavoritesFragment(installClickListener) }
            1 -> {result=FavoriteTendersFragment(installClickListener)}
            2 -> { result = TabSavedSearchesFragment(installClickListener) }
            3 -> { result = TabSelectionsFragment(installClickListener) }

        }
        return result!!
    }
}