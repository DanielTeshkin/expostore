package com.expostore.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.pojo.getchats.UserResponse
import com.expostore.api.response.ImageResponse



@Entity
data class UserDao(
    @PrimaryKey
    @ColumnInfo(name="id") val id : String ,
    @ColumnInfo(name="last_name") val lastName  : String = "",
    @ColumnInfo(name = "first_name") val firstName : String ="",
    @ColumnInfo(name = "online" ) val online    : String="",
    @ColumnInfo(name="username") val username  : String="",
    @ColumnInfo(name="avatar") val avatar  : AvatarDao?
)

val UserResponse.toDao:UserDao
   get() = UserDao(id?:"", lastName?:"", firstName?:"", online?:"", username?:"", avatar?.toDaoAvatar)



