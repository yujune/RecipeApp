package com.example.recipeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var recipeID: Long = 0L,
    @ColumnInfo(name = "recipe_title")
    var title: String = "",
    @ColumnInfo(name = "recipe_desc")
    var description: String = "",
    @ColumnInfo(name = "recipe_img")
    var img: String = ""
)