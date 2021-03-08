package com.example.recipeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.recipeapp.R
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.databinding.FragmentRecipeDetailsBinding
import com.example.recipeapp.databinding.FragmentRecipeListBinding
import com.example.recipeapp.viewmodel.RecipeDetailsViewModel


class RecipeDetailsFragment : Fragment() {

    private var _binding: FragmentRecipeDetailsBinding? = null
    private var recipeID: Long = 0
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recipeDetailViewModel: RecipeDetailsViewModel
    private lateinit var recipe : Recipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        recipeID = requireArguments().getLong(RecipeListFragment.RECIPE_ID)

        recipeDetailViewModel = ViewModelProvider(this).get(RecipeDetailsViewModel::class.java)

        recipeDetailViewModel.recipe.observe(viewLifecycleOwner, Observer {recipe ->
            binding.recipeDetialsIconTextCalories.setText(recipe.calories)
            binding.recipeDetialsTextRates.setText(recipe.rate.toString())
        })
        //recipe = recipeDetailViewModel.getRecipe(recipeID)
//
//        binding.recipeDetialsTextTime.setText(recipe.minutes)
//        binding.recipeDetialsIconTextCalories.setText(recipe.calories)

        return view
    }


}