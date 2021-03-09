package com.example.recipeapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.databinding.FragmentRecipeEditBinding
import com.example.recipeapp.viewmodel.RecipeEditViewModel

class RecipeEditFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentRecipeEditBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recipeEditViewModel: RecipeEditViewModel
    private var recipeID: Long = 0
    private lateinit var recipeName: String
    private lateinit var recipeDesc: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeEditBinding.inflate(inflater, container, false)
        val view = binding.root

        val spinner: Spinner = binding.recipeEditSpinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.recipetypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = this

        recipeID = requireArguments().getLong(RecipeListFragment.RECIPE_ID)
        recipeEditViewModel = ViewModelProvider(this).get(RecipeEditViewModel::class.java)
        recipeEditViewModel.getRecipe(recipeID).observe(viewLifecycleOwner,{recipe ->
            binding.recipeEditIconTextCalories.setText(recipe.calories.toString())
            binding.recipeEditTextRates.setText(recipe.rate.toString())
            binding.recipeEditTextTime.setText(recipe.minutes.toString())
            binding.recipeEditTextIngredients.setText(recipe.ingredients)
            binding.recipeEditTextSteps.setText(recipe.steps)
            recipeName = recipe.recipeName
            recipeDesc = recipe.description



            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)  // default image
                .error(R.drawable.ic_launcher_foreground)        // error image

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(recipe.image_url)
                .into(binding.recipeEditImage)


        })


        binding.recipeEditFloatingActionDone.setOnClickListener { view ->
            updateRoomDatabase(recipeID)
        }

        return view
    }

    fun updateRoomDatabase(recipeID: Long) {

//        val recipeName = recipeName
//        val
//
//        val updatedCalories = binding.recipeEditIconTextCalories.text.toString().toInt()
//        val updatedRates = binding.recipeEditTextRates.text.toString().toDouble()
//        val updatedTime = binding.recipeEditTextTime.text.toString().toInt()
//        val updatedIngredients = binding.recipeEditTextIngredients.text.toString()
//        val updatedSteps = binding.recipeEditTextSteps.text.toString()
//        val updatedType =
//
//        val updatedRecipe = Recipe(recipeID,recipeName,recipeDesc,updatedTime,updatedCalories,updatedRates,)

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

        (parent.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(this.requireContext(),R.color.black))
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

        TODO("Not yet implemented")
    }


}