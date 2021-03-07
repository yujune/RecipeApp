package com.example.recipeapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecipeDatabaseDao {

    @Insert
    suspend fun addRecipe(recipe: Recipe)

    //Notice the :key. You use the colon notation in the query to reference arguments in the function.
    @Query("SELECT * from recipe_table WHERE recipeID = :key")
    fun get(key: Long): Recipe?

    @Query("SELECT * from recipe_table")
    fun readAllData(): LiveData<List<Recipe>>

    @Query("DELETE FROM recipe_table")
    fun clear()

}