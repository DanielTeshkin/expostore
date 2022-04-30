package com.expostore.api.pojo.getcategory

data class Shop(
    val address: String,
    val author: AuthorX,
    val id: String,
    val image: ImageX,
    val lat: Double,
    val long: Double,
    val name: String,
    val shopping_center: String
)