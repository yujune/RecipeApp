package com.example.recipeapp.database

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDatabaseDao){

    val readAllData: LiveData<List<Recipe>> = recipeDao.readAllData()

    //val getRecipeDetails : LiveData<Recipe> = recipeDao.getRecipe()

    suspend fun addRecipe(recipe: Recipe){
        recipeDao.addRecipe(recipe)

    }

    fun getRecipe(recipeID:Long): LiveData<Recipe>{
        return recipeDao.getRecipe(recipeID)
    }

}