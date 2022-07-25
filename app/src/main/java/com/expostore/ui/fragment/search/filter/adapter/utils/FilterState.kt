package com.expostore.ui.fragment.search.filter.adapter.utils

interface FilterState {
   fun inputListener(left:String,right: String?=null,name:String)
   fun radioListener(id:String,name: String)
   fun selectListener(id:String,name: String,checked: Boolean)
   fun checkBoxListener(name:String,checked:Boolean)
}