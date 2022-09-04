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
    val product = this.product_characteristics[1].characteristics as MutableList
    val models = mutableListOf<ComparisonModel>()
    var state = false
    for (i in 0..productGeneral.size) {
        val means = productGeneral[i]
        val base = means.characteristic
        product.forEach {
            if (it.characteristic?.id == base?.id) {
                models.add(
                    ComparisonModel(name = base?.name?: "", firstProductMean = means.toMean(base?.type!!),
                        secondProductMean = it.toMean(it.characteristic?.type!!)))
                product.remove(it)
                state = true
                return@forEach
            }
        }
        if (!state) {
            models.add(
                ComparisonModel(name = base?.name?: "",
                    firstProductMean = means.toMean(base?.type!!),
                    secondProductMean = "")
            )
        }
        else state=false
    }
    product.map {
        models.add(
            ComparisonModel( name = it.characteristic?.name?: "", firstProductMean ="",
                secondProductMean = it.toMean(it.characteristic?.type!!)))
    }
    return models
}

fun CharacteristicComparison.toMean(type:String) :String= when(type){
        "select"-> this.selected_items?.map { it.value }?.joinToString(",")?:""
        "radio"->this.selected_item?.value?:""
        "input"-> this.char_value?:""
        "checkbox"->if (this.bool_value!!)"да"
        else "нет"
        else -> ""
    }
