package com.expostore.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.api.response.ImageResponse
@Entity
class AvatarDao(
    @PrimaryKey
@ColumnInfo(name = "id") val id: String  ,
@ColumnInfo(name = "file")val file: String )


val ImageResponse.toDaoAvatar : AvatarDao
    get() = AvatarDao(id,file)