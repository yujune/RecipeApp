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

class RecipeViewModel(application: Application):AndroidViewModel(application) {

    val readAllData: LiveData<MutableList<Recipe>>
    private val repository: RecipeRepository

    //always called when this viewmodel is called.
    init {
        val recipeDao = RecipeDatabase.getInstance(application).recipeDao
        repository = RecipeRepository(recipeDao)
        readAllData = repository.readAllData
    }

    //run in background thread
    fun addRecipe(recipe: Recipe){
        viewModelScope.launch(Dispatchers.IO){
            repository.addRecipe(recipe)
        }
    }

    fun getRecipeByType(type: String): LiveData<MutableList<Recipe>>{
        return repository.getRecipeByType(type)
    }

}