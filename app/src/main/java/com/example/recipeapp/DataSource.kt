package com.example.recipeapp

import com.example.recipeapp.model.RecipeModel

class DataSource {

    //In short, companion objects are singleton objects whose properties and functions are tied to a class but not to the instance of that class
    //— basically like the “static” keyword in Java but with a twist.
    companion object {

        fun createDataSet(): ArrayList<RecipeModel> {
            val list = ArrayList<RecipeModel>()
            list.add(
                RecipeModel(
                    "Macarons",
                    "Sweet, Tasty, Colourful!",
                    "https://live.staticflickr.com/65535/50414595221_b1a0015dd5_b.jpg"

                )
            )
            list.add(
                RecipeModel(
                    "Italian Pasta",
                    "Hot and Spicy!",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREO9BGqz7ZOi60lnedwPTVZ9Q6LzvRU5ZkMg&usqp=CAU",

                    )
            )

            list.add(
                RecipeModel(
                    "Peanut Cake",
                    "A crunchy and sweet cake.",
                    "https://www.wholesomeyum.com/wp-content/uploads/2018/03/wholesomeyum-low-carb-keto-sugar-free-carrot-cake-recipe-3.jpg",

                    )
            )
            list.add(
                RecipeModel(
                    "Chocolate Donut",
                    "Sweet + Sweet + Sweet!",
                    "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX2176502.jpg",

                    )
            )
            list.add(
                RecipeModel(
                    "Spagethi",
                    "Spicy and Sour.",
                    "https://i.pinimg.com/originals/6f/b9/9b/6fb99bb17e4ac1bfc65fb1d1d5cc8776.jpg",

                    )
            )
            return list
        }
    }
}