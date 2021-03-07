package com.example.recipeapp.database

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDatabaseDao){

    val readAllData: LiveData<List<Recipe>> = recipeDao.readAllData()

    suspend fun addRecipe(recipe: Recipe){
        recipeDao.addRecipe(recipe)

    }

}