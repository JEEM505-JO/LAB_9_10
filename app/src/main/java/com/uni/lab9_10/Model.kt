package com.uni.lab9_10

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CONTACT")
data class Model(
    @PrimaryKey
    @ColumnInfo("Id")
    val id : Int,
    @ColumnInfo("Nombre")
    val Nombre: String,
    @ColumnInfo("Numero")
    val Numero: String,
    @ColumnInfo("Referencia")
    val Referencia: String
)