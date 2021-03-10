package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.database.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeAddViewModel(application: Application):AndroidViewModel(application) {

    private var repository: RecipeRepository

    init {

        val recipeDao = RecipeDatabase.getInstance(application).recipeDao
        repository = RecipeRepository(recipeDao)

    }

    //run in background thread
    fun addRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.addRecipe(recipe)
        }
    }

}