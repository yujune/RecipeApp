package com.example.recipeapp.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
    private lateinit var imgUrl: String
    private var updatedName = ""
    private var updatedDescription = ""
    private var updatedCalories = ""
    private var updatedRates = ""
    private var updatedTime = ""
    private var updatedIngredients = ""
    private var updatedSteps = ""
    private var updatedType = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeEditBinding.inflate(inflater, container, false)
        val view = binding.root
        setHasOptionsMenu(true)

        setUpSpinner()

        recipeID = requireArguments().getLong(RecipeListFragment.RECIPE_ID)
        recipeEditViewModel = ViewModelProvider(this).get(RecipeEditViewModel::class.java)
        recipeEditViewModel.getRecipe(recipeID).observe(viewLifecycleOwner, { recipe ->
            binding.recipeEditTextName.setText(recipe.recipeName)
            binding.recipeEditTextDesc.setText(recipe.description)
            binding.recipeEditIconTextCalories.setText(recipe.calories.toString())
            binding.recipeEditTextRates.setText(recipe.rate.toString())
            binding.recipeEditTextTime.setText(recipe.minutes.toString())
            binding.recipeEditTextIngredients.setText(recipe.ingredients)
            binding.recipeEditTextSteps.setText(recipe.steps)
            imgUrl = recipe.image_url

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.icon_food)  // default image
                .error(R.drawable.icon_error)        // error image

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

    private fun setUpSpinner() {
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
    }

    fun updateRoomDatabase(recipeID: Long) {

        if(checkInput()){

            updatedType = binding.recipeEditSpinner.selectedItem.toString()

            val updatedRecipe = Recipe(
                recipeID,
                updatedName,
                updatedDescription,
                updatedTime.toInt(),
                updatedCalories.toInt(),
                updatedRates.toDouble(),
                updatedType,
                false,
                imgUrl,
                updatedIngredients,
                updatedSteps
            )
            recipeEditViewModel.updateRecipe(updatedRecipe)
            Toast.makeText(
                requireContext(),
                getString(R.string.updated_successfully),
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_recipeEditDetailsFragment_to_recipeListFragment)
            closeSoftKeyBoard()

        }else{
           return
        }

    }

    private fun closeSoftKeyBoard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

     private fun checkInput(): Boolean {

        getInputText()

        if(updatedName.isEmpty()){
            binding.recipeEditTextName.setError(resources.getString(R.string.required_field))

        }else if(updatedDescription.isEmpty()){
            binding.recipeEditTextDesc.setError(resources.getString(R.string.required_field))

        }else if(updatedTime.isEmpty()){
            binding.recipeEditTextTime.setError(resources.getString(R.string.required_field))

        }else if(updatedCalories.isEmpty()){
            binding.recipeEditIconTextCalories.setError(resources.getString(R.string.required_field))

        }else if(updatedRates.isEmpty()){
            binding.recipeEditTextRates.setError(resources.getString(R.string.required_field))

        }else if(updatedIngredients.isEmpty()){
            binding.recipeEditTextIngredients.setError(resources.getString(R.string.required_field))

        }else if(updatedSteps.isEmpty()){
            binding.recipeEditTextSteps.setError(resources.getString(R.string.required_field))

        }else{
            return true
        }

        return false

    }

    private fun getInputText(){

        updatedName = binding.recipeEditTextName.text.toString()
        updatedDescription = binding.recipeEditTextDesc.text.toString()
        updatedCalories = binding.recipeEditIconTextCalories.text.toString()
        updatedRates = binding.recipeEditTextRates.text.toString()
        updatedTime = binding.recipeEditTextTime.text.toString()
        updatedIngredients = binding.recipeEditTextIngredients.text.toString()
        updatedSteps = binding.recipeEditTextSteps.text.toString()

    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

        (parent.getChildAt(0) as TextView).setTextColor(
            ContextCompat.getColor(
                this.requireContext(),
                R.color.black
            )
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_fragment_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                Toast.makeText(requireContext(), getString(R.string.Delete), Toast.LENGTH_SHORT)
                    .show()
                recipeEditViewModel.deleteByID(recipeID)
            }
            else -> {

            }

        }

        findNavController().navigate(R.id.action_recipeEditDetailsFragment_to_recipeListFragment)
        return super.onOptionsItemSelected(item)
    }
}