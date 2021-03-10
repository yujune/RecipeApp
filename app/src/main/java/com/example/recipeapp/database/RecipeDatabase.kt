package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase(){
    // or abstract fun recipeDao:RecipeDatabaseDao
    abstract val recipeDao: RecipeDatabaseDao
    companion object {
        //The INSTANCE variable will keep a reference to the database, when one has been created. This helps you avoid repeatedly opening connections to the database, which is computationally expensive.
        //@Volatile. The value of a volatile variable will never be cached,
        @Volatile
        private var INSTANCE: RecipeDatabase? = null
        fun getInstance(context: Context): RecipeDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "recipe_history_database")
                        .createFromAsset("database/recipe_database.db")
                        //.fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}