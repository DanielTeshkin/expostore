package com.expostore.api.pojo.getchats.DataMapping



data class Product( var id     : String?           = null,
                 var name   : String?           = null,
                   var images : ArrayList<ImageChat> = arrayListOf())

