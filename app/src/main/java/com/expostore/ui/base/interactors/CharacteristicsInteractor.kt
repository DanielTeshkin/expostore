package com.expostore.ui.base.interactors

import com.expostore.data.repositories.CategoryRepository

abstract class CharacteristicsInteractor {
    abstract val category: CategoryRepository
    fun getCategories()=category.getCategories()
    fun getCategoryCharacteristic(id:String)=category.getCategoryCharacteristic(id)
}