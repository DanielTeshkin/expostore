package com.expostore.db.enities.chat

import androidx.room.TypeConverter
import com.expostore.db.enities.ProfileDao
import com.expostore.db.enities.product.ProductDao

import com.expostore.model.ImageModel
import com.expostore.model.chats.DataMapping.Message
import com.expostore.model.chats.DataMapping.Product
import com.expostore.model.chats.DataMapping.User
import com.expostore.model.product.*
import com.expostore.model.profile.ProfileModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

abstract class Convert <T> {
    @TypeConverter
    fun mapListToString(value: List<T>): String {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): T {
        val gson = Gson()
        val type = object :TypeToken<T>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: T): String {
        val gson = Gson()
        val type = object :TypeToken<T>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<T> {
        val gson = Gson()
        val type = object : TypeToken<List<T>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConvertMessage{
    @TypeConverter
    fun mapListToString(value: List<Message>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Message>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): Message {
        val gson = Gson()
        val type = object :TypeToken<Message>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: Message): String {
        val gson = Gson()
        val type = object :TypeToken<Message>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<Message> {
        val gson = Gson()
        val type = object : TypeToken<List<Message>>() {}.type
        return gson.fromJson(value, type)
    }
}


class ConvertChat{
    @TypeConverter
    fun mapListToString(value: List<ChatDao>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ItemChatDao>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): ChatDao {
        val gson = Gson()
        val type = object :TypeToken<ChatDao>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: ChatDao): String {
        val gson = Gson()
        val type = object :TypeToken<ChatDao>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ChatDao> {
        val gson = Gson()
        val type = object : TypeToken<List<ChatDao>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConvertItem{
    @TypeConverter
    fun mapListToString(value: List<ItemChatDao>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ItemChatDao>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): ItemChatDao {
        val gson = Gson()
        val type = object :TypeToken<ItemChatDao>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: ItemChatDao): String {
        val gson = Gson()
        val type = object :TypeToken<ItemChatDao>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ItemChatDao> {
        val gson = Gson()
        val type = object : TypeToken<List<ItemChatDao>>() {}.type
        return gson.fromJson(value, type)
    }
}


class ConvertFile: Convert<FileDao>()
class ConvertUser{
    @TypeConverter
    fun mapListToString(value: List<User>): String {
        val gson = Gson()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): User {
        val gson = Gson()
        val type = object :TypeToken<User>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: User): String {
        val gson = Gson()
        val type = object :TypeToken<User>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<User> {
        val gson = Gson()
        val type = object : TypeToken<List<User>>() {}.type
        return gson.fromJson(value, type)
    }
}

class ConvertProduct{
    @TypeConverter
    fun mapListToString(value: List<Product>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): Product {
        val gson = Gson()
        val type = object :TypeToken<Product>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: Product): String{
        val gson = Gson()
        val type = object :TypeToken<Product>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<Product> {
        val gson = Gson()
        val type = object : TypeToken<List<Product>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConvertImage{
    @TypeConverter
    fun mapListToString(value: List<ImageModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ImageModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): ImageModel {
        val gson = Gson()
        val type = object :TypeToken<ImageModel>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: ImageModel): String {
        val gson = Gson()
        val type = object :TypeToken<ImageModel>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ImageModel> {
        val gson = Gson()
        val type = object : TypeToken<List<ImageModel>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConverterProfile{
    @TypeConverter
    fun mapListToString(value: List<ProfileDao>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ProfileDao>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): ProfileDao {
        val gson = Gson()
        val type = object :TypeToken<ProfileDao>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: ProfileDao): String{
        val gson = Gson()
        val type = object :TypeToken<ProfileDao>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ProfileDao> {
        val gson = Gson()
        val type = object : TypeToken<List<ProfileDao>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConverterShop{
    @TypeConverter
    fun mapListToString(value: List<ProfileModel.ShopModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ProfileModel.ShopModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): ProfileModel.ShopModel {
        val gson = Gson()
        val type = object :TypeToken<ProfileModel.ShopModel>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value: ProfileModel.ShopModel): String{
        val gson = Gson()
        val type = object :TypeToken<ProfileModel.ShopModel>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ProfileModel.ShopModel> {
        val gson = Gson()
        val type = object : TypeToken<List<ProfileModel.ShopModel>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConvertProductModel{
    @TypeConverter
    fun mapListToString(value: List<ProductModel>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ProductModel>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): ProductModel {
        val gson = Gson()
        val type = object :TypeToken<ProductModel>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value:ProductModel): String{
        val gson = Gson()
        val type = object :TypeToken<ProductModel>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ProductModel> {
        val gson = Gson()
        val type = object : TypeToken<List<ProductModel>>() {}.type
        return gson.fromJson(value, type)
    }
}
class ConvertUserDaoModel{
    @TypeConverter
    fun mapListToString(value: List<UserDao>): String {
        val gson = Gson()
        val type = object : TypeToken<List<UserDao>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapObjectToString(value: String): UserDao {
        val gson = Gson()
        val type = object :TypeToken<UserDao>(){}.type
        return gson.fromJson(value,type)
    }


    @TypeConverter
    fun mapObjectToString(value:UserDao): String{
        val gson = Gson()
        val type = object :TypeToken<UserDao>(){}.type
        return gson.toJson(value,type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<UserDao> {
        val gson = Gson()
        val type = object : TypeToken<List<UserDao>>() {}.type
        return gson.fromJson(value, type)
    }
}

