package com.expostore.data.local.db.enities.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.data.remote.api.response.ImageResponse
@Entity(tableName = "dddd")
class AvatarDao(
    @PrimaryKey
@ColumnInfo(name = "id") val id: String  ,
@ColumnInfo(name = "file")val file: String )


val ImageResponse.toDaoAvatar : AvatarDao
    get() = AvatarDao(id,file)

