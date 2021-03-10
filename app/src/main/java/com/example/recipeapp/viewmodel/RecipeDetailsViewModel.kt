package com.example.recipeapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.database.RecipeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailsViewModel(application: Application) : AndroidViewModel(application) {
    //var recipe: LiveData<Recipe>
    private val repository: RecipeRepository


    init {
        val recipeDao = RecipeDatabase.getInstance(application).recipeDao
        repository = RecipeRepository(recipeDao)
        //recipe =
    }

    fun getRecipe(recipeID: Long): LiveData<Recipe> {

        return repository.getRecipe(recipeID)

    }


}