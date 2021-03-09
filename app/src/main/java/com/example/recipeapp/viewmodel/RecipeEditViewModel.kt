package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.database.RecipeRepository

class RecipeEditViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RecipeRepository

    init {

        val recipeDao = RecipeDatabase.getInstance(application).recipeDao
        repository = RecipeRepository(recipeDao)
    }

    fun getRecipe(recipeID: Long): LiveData<Recipe> {

        return repository.getRecipe(recipeID)

    }


}