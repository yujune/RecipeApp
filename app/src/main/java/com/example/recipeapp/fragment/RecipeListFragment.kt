package com.example.recipeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.DataSource
import com.example.recipeapp.R
import com.example.recipeapp.RecipeRecyclerAdapter
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.databinding.FragmentRecipeListBinding
import com.example.recipeapp.model.RecipeModel
import com.example.recipeapp.viewmodel.RecipeViewModel

class RecipeListFragment : Fragment(), RecipeRecyclerAdapter.OnItemClickListener {


    lateinit var recipeAdapter: RecipeRecyclerAdapter
    private var _binding: FragmentRecipeListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recipeViewModel: RecipeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        val view = binding.root
        initRecylerView()
        //addDataSet()
//        var recipe1 = Recipe(
//            100,
//            "Macarons",
//            "Sweet, Tasty, Colourful!",
//            30,
//            300,
//            4.5,
//            "https://live.staticflickr.com/65535/50414595221_b1a0015dd5_b.jpg",
//            "- eggs\n- flour\n- sugar\n- chocolate\n- milk\n"
//        )
//
//        var recipe2 = Recipe(
//            101,
//            "Italian Pasta",
//            "Hot and Spicy!",
//            60,
//            12000,
//            4.9,
//            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREO9BGqz7ZOi60lnedwPTVZ9Q6LzvRU5ZkMg&usqp=CAU",
//            "- pasta\n- flour\n- sugar\n- chilli sauce\n- soy sauce\n- salt\n"
//        )
//
//        var recipe3 = Recipe(
//            102,
//            "Peanut Cake",
//            "A crunchy and sweet cake.",
//            100,
//            5000,
//            4.0,
//            "https://www.wholesomeyum.com/wp-content/uploads/2018/03/wholesomeyum-low-carb-keto-sugar-free-carrot-cake-recipe-3.jpg",
//            "- peanut\n- flour\n- sugar\n- butter\n- water\n- white cream\n- eggs\n"
//        )
//
//        var recipe4 = Recipe(
//            103,
//            "Chocolate Donut",
//            "Sweet + Sweet + Sweet!",
//            20,
//            1000,
//            4.9,
//            "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX2176502.jpg",
//            "- chocolate cream\n- flour\n- sugar\n- butter\n- water\n- white cream\n- eggs\n"
//        )
//
//        var recipe5 = Recipe(
//            104,
//            "Spagethi",
//            "Spicy and Sour.",
//            200,
//            19800,
//            5.0,
//            "https://i.pinimg.com/originals/6f/b9/9b/6fb99bb17e4ac1bfc65fb1d1d5cc8776.jpg",
//            "- spagethi noodles\n- flour\n- salt\n- hot water\n- soy sauce\n- eggs\n"
//        )

//        recipeViewModel.addRecipe(recipe1)
//        recipeViewModel.addRecipe(recipe2)
//        recipeViewModel.addRecipe(recipe3)
//        recipeViewModel.addRecipe(recipe4)
//        recipeViewModel.addRecipe(recipe5)

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        //observe the livedata obj, whenener we chg the data, the adpater can set the value
        recipeViewModel.readAllData.observe(viewLifecycleOwner, Observer {recipe ->
            recipeAdapter.submitList(recipe)
        })

        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    private fun initRecylerView(){
        binding.recommendRecylerView.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            recipeAdapter = RecipeRecyclerAdapter(this@RecipeListFragment)
            adapter = recipeAdapter
        }
    }

//    private fun addDataSet(){
//        val data = DataSource.createDataSet()
//        recipeAdapter.submitList(data)
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(recipe: Recipe) {
        Toast.makeText(context,"User name ${recipe.recipeName}", Toast.LENGTH_LONG)
            .show()
        val bundle = Bundle()
        bundle.putLong(RECIPE_ID,recipe.recipeID)
        findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailsFragment,bundle)
    }

    companion object{
        const val RECIPE_ID = "com.example.recipeapp.fragment.recipeId"
    }
}