package com.expostore.model

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
         val product = this.product_characteristics[1].characteristics as MutableList
         val models = mutableListOf<ComparisonModel>()
         var state = false
         for (element in productGeneral) {
             val base = element.characteristic
             product.forEach {
                 if (it.characteristic?.id == base?.id) {
                     models.add(
                         ComparisonModel(
                             name = base?.name ?: "",
                             firstProductMean = element.toMean(base?.type!!),
                             secondProductMean = it.toMean(it.characteristic?.type!!)
                         )
                     )
                     product.remove(it)
                     state = true
                     return@forEach
                 }
             }
             if (!state) {
                 models.add(
                     ComparisonModel(
                         name = base?.name ?: "",
                         firstProductMean = element.toMean(base?.type!!),
                         secondProductMean = "не указано"
                     )
                 )
             } else state = false
         }
         product.map {
             models.add(
                 ComparisonModel(
                     name = it.characteristic?.name ?: "", firstProductMean = "не указано",
                     secondProductMean = it.toMean(it.characteristic?.type!!)
                 )
             )
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
