package com.expostore.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.pojo.getchats.UserResponse
import com.expostore.api.response.ImageResponse
import com.expostore.model.ImageModel
import com.expostore.model.chats.DataMapping.User
import com.expostore.model.toModel


@Entity(tableName = "users")
data class UserDao(
    @PrimaryKey
    @ColumnInfo(name="id") val id : String ,
    @ColumnInfo(name="last_name") val lastName  : String = "",
    @ColumnInfo(name = "first_name") val firstName : String ="",
    @ColumnInfo(name = "online" ) val online    : String="",
    @ColumnInfo(name="username") val username  : String="",
    @ColumnInfo(name="avatar") val avatar  : ImageModel?
)

val UserResponse.toDao:UserDao
   get() = UserDao(id?:"", lastName?:"", firstName?:"", online?:"", username?:"", avatar?.toModel)


val User.toDao:UserDao
get() = UserDao(id,lastName?:"",firstName?:"",online,username,avatar)
