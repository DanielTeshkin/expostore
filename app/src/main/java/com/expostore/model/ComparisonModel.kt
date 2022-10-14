package com.expostore.model

import android.util.Log
import com.expostore.data.remote.api.pojo.comparison.ComparisonResult
import com.expostore.data.remote.api.response.CharacteristicComparison


data class ComparisonModel(
    val name:String,
    val firstProductMean:String,
    val secondProductMean:String
)
fun ComparisonResult.toDate():List<ComparisonModel> {
    val productGeneral = this.product_characteristics[0].characteristics

     return if (this.product_characteristics.size>1) {
         Log.i("compar", this.product_characteristics[1].characteristics.size.toString())
         val product = this.product_characteristics[1].characteristics as MutableList
         val models = mutableListOf<ComparisonModel>()
         var state = false
         Log.i("compar1",state.toString())
         for (element in productGeneral) {
             Log.i("compari1","j")
             val base = element.characteristic
             Log.i("compari2",base?.id?:"")
             Log.i("compar2",base?.id?:"")
             val listForDelete= mutableListOf<CharacteristicComparison>()
             product.forEach {
                 if (it.characteristic?.id == base?.id) {
                     Log.i("compari3",base?.id?:"")
                     models.add(
                         ComparisonModel(
                             name = base?.name ?: "",
                             firstProductMean = element.toMean(base?.type!!),
                             secondProductMean = it.toMean(it.characteristic?.type!!)
                         )
                     )
                     Log.i("compari4",base?.id?:"")
                 listForDelete.add(it)
                     state = true
                     Log.i("compar3",base?.id?:"")
                         return@forEach
                 }
                 Log.i("compar33",base?.id?:"")
             }
             product.removeAll(listForDelete)
             Log.i("compar4",base?.id?:"")
             if (!state) {
                 models.add(
                     ComparisonModel(
                         name = base?.name ?: "",
                         firstProductMean = element.toMean(base?.type!!),
                         secondProductMean = "не указано"
                     )
                 )
                 Log.i("compar5",base?.id?:"")
             } else state = false
         }
         product.map {
             models.add(
                 ComparisonModel(
                     name = it.characteristic?.name ?: "", firstProductMean = "не указано",
                     secondProductMean = it.toMean(it.characteristic?.type!!)
                 )
             )
             Log.i("compar6","fff")
         }
         models
     }

    else mutableListOf()
}

fun CharacteristicComparison.toMean(type:String) :String= when(type){
        "select"-> this.selected_items?.map { it.value }?.joinToString(",")?:"не указано"
        "radio"->this.selected_item?.value?:"не указано"
        "input"-> this.char_value?:"не указано"
        "checkbox"->if (this.bool_value!!)"да"
        else "нет"
        else -> ""
    }
