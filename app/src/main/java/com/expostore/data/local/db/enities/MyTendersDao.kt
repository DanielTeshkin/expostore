package com.expostore.data.local.db.enities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.expostore.model.tender.TenderModel

@Entity(tableName = "my_tenders")
data class MyTendersDao(
    @PrimaryKey
    @ColumnInfo(name = "status") val status:String="",
    @ColumnInfo(name = "items") val items:List<TenderModel>,
)