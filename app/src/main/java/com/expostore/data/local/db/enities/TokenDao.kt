package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "token")
data class TokenDao(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long =0,
    @ColumnInfo(name="refresh") val refresh:String?=null,
    @ColumnInfo(name="access") val access:String?=null
    )
