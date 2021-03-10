package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.database.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeEditViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository

    init {

        val recipeDao = RecipeDatabase.getInstance(application).recipeDao
        repository = RecipeRepository(recipeDao)
    }

    fun getRecipe(recipeID: Long): LiveData<Recipe> {

        return repository.getRecipe(recipeID)

    }

    fun updateRecipe(recipe: Recipe) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecipe(recipe)
        }
    }

    fun deleteByID(key: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteByID(key)
        }
    }


}