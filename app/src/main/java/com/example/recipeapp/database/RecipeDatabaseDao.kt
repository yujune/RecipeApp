package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeDatabaseDao {

    @Insert
    suspend fun addRecipe(recipe: Recipe)

    //Notice the :key. You use the colon notation in the query to reference arguments in the function.
    @Query("SELECT * from recipe_table WHERE recipeID = :key")
    fun getRecipe(key: Long): LiveData<Recipe>

    @Query("SELECT * from recipe_table WHERE type = :type")
    fun getRecipeByType(type: String): LiveData<MutableList<Recipe>>

    @Query("SELECT * from recipe_table")
    fun readAllData(): LiveData<MutableList<Recipe>>

    @Query("DELETE FROM recipe_table")
    fun clear()

    @Update
    fun updateRecipe(recipe: Recipe)

}