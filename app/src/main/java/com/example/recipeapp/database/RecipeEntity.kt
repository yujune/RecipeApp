package com.example.recipeapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_table")
data class Recipe(
    @PrimaryKey(autoGenerate = true)
    var recipeID: Long = 0L,
    @ColumnInfo(name = "recipe_title")
    var recipeName: String = "",
    @ColumnInfo(name = "recipe_desc")
    var description: String = "",
    @ColumnInfo(name = "minutes")
    var minutes: Int = 30,
    @ColumnInfo(name = "calories")
    var calories: Int = 100,
    @ColumnInfo(name = "rate")
    var rate: Double = 5.0,
    @ColumnInfo(name = "type")
    var type: String = "",
    @ColumnInfo(name = "saved")
    var saved: Boolean = false,
    @ColumnInfo(name = "recipe_img")
    var image_url: String = "",
    @ColumnInfo(name = "ingredients")
    var ingredients: String = "",
    @ColumnInfo(name = "steps")
    var steps: String = "",
)