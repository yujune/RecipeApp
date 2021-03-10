package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.model.RecipeModel

//extends  RecyclerView.Adapter<RecyclerView.ViewHolder>
class RecipeRecyclerAdapter(val itemClickListener: OnItemClickListener): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var items : MutableList<Recipe> = mutableListOf()

    //tell recylerview create each item a viewholder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // return new recipe view obj
        val layoutInflater = LayoutInflater.from(parent.context)
        return RecipeViewHolder(
            layoutInflater.inflate(R.layout.recommended_recipe_view,parent,false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){
            is RecipeViewHolder -> {
                //bind the data to the view holder that particularly in view on screen.
                //onBindViewHolder will be called for each list item when user scroll on the screen
                holder.bind(items.get(position),itemClickListener)

            }
        }

    }

    // tell recylerview how many items inside the list
    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(recipeList: MutableList<Recipe>){
        this.items = recipeList
        notifyDataSetChanged()
    }

    fun submitListByType(recipeList: MutableList<Recipe>){
        this.items.clear();
        this.items.addAll(recipeList)
        notifyDataSetChanged()
    }

    // this is the custom viewholder class which specify how your view holder should look like.
    class RecipeViewHolder constructor(
        itemView: View
    ): RecyclerView.ViewHolder(itemView){
        val recommendRecipeTitle: TextView = itemView.findViewById(R.id.recommend_recipe_title)
        val recommendRecipeDesc: TextView = itemView.findViewById(R.id.recommend_recipe_desc)
        val recommendRecipeImage: ImageView = itemView.findViewById(R.id.recommend_recipe_image)
        val recommendRecipeTimes : TextView = itemView.findViewById(R.id.recommend_recipe_times)
        val recipeType: TextView = itemView.findViewById(R.id.recommend_recipe_type)

        //taking each individual recipe obj and bind to the view in the layout
        fun bind(recipe: Recipe, clickListener: OnItemClickListener){

            recommendRecipeTitle.setText(recipe.recipeName)
            recommendRecipeDesc.setText(recipe.description)
            recommendRecipeTimes.setText(recipe.minutes.toString() + " min")
            recipeType.setText(recipe.type)

            //create request obj
            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)  // default image
                .error(R.drawable.ic_launcher_foreground)        // error image

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(recipe.image_url)
                .into(recommendRecipeImage)

            itemView.setOnClickListener {
                clickListener.onItemClicked(recipe)
            }

        }
    }

    interface OnItemClickListener{
        fun onItemClicked(recipe: Recipe)
    }

}