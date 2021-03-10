package com.example.recipeapp.database

import androidx.lifecycle.LiveData

class RecipeRepository(private val recipeDao: RecipeDatabaseDao) {

    val readAllData: LiveData<MutableList<Recipe>> = recipeDao.readAllData()

    //val getRecipeDetails : LiveData<Recipe> = recipeDao.getRecipe()

    suspend fun addRecipe(recipe: Recipe) {
        recipeDao.addRecipe(recipe)

    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeDao.updateRecipe(recipe)
    }

    fun getRecipe(recipeID: Long): LiveData<Recipe> {
        return recipeDao.getRecipe(recipeID)
    }

    fun getRecipeByType(type: String): LiveData<MutableList<Recipe>> {
        return recipeDao.getRecipeByType(type)
    }

    suspend fun deleteByID(key: Long) {
        recipeDao.deleteByID(key)
    }

}