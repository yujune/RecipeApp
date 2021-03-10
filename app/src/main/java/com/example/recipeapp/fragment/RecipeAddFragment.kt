package com.example.recipeapp.fragment

import android.Manifest
import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.recipeapp.R
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.databinding.FragmentRecipeAddBinding
import com.example.recipeapp.viewmodel.RecipeAddViewModel

class RecipeAddFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: FragmentRecipeAddBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recipeSelectedType: String
    private lateinit var recipeAddViewModel: RecipeAddViewModel
    private var recipeImageUrl= "https://as1.ftcdn.net/jpg/02/70/14/90/500_F_270149063_CjS5CmiZsXF5YFFxwhHmrq5F6fJknWgq.jpg"
    private var newRecipeName= ""
    private var newRecipeDescription = ""
    private var newRecipeCalories= ""
    private var newRecipeRates= ""
    private var newRecipeTime= ""
    private var newRecipeIngredients= ""
    private var newRecipeSteps= ""
    private var newRecipeType= ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipeAddBinding.inflate(inflater, container, false)
        val view = binding.root

        recipeAddViewModel = ViewModelProvider(this).get(RecipeAddViewModel::class.java)

        setUpSpinner()
        binding.recipeAddFloatingActionAddRecipe.setOnClickListener { view ->
            addRecipeToRoom()
        }

        binding.recipeAddFloatingActionGallery.setOnClickListener { view ->
            when {
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    pickImageFromGallery()
                }
                else -> {
                    // You can directly ask for the permission.
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    requestPermissions(permissions, PERMISSION_CODE)
                }
            }

        }
        // Inflate the layout for this fragment
        return view
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(
                        requireContext(),
                        resources.getString(R.string.permission_denied),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {

            recipeImageUrl = data?.data.toString()

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)  // default image
                .error(R.drawable.ic_launcher_foreground)        // error image

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(data?.data.toString())
                .into(binding.recipeAddRecipeImage)

        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    fun addRecipeToRoom() {

        if (checkInput()){
            newRecipeType = binding.recipeAddSpinner.selectedItem.toString()

            val updatedRecipe = Recipe(
                0,
                newRecipeName,
                newRecipeDescription,
                newRecipeTime.toInt(),
                newRecipeCalories.toInt(),
                newRecipeRates.toDouble(),
                newRecipeType,
                false,
                recipeImageUrl,
                newRecipeIngredients,
                newRecipeSteps
            )
            recipeAddViewModel.addRecipe(updatedRecipe)
            findNavController().navigate(R.id.action_recipeAddFragment_to_recipeListFragment)

            closeSoftKeyBoard()

        }else{
            Toast.makeText(
                requireContext(),
                R.string.pls_fill_the_required_field.toString(),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun closeSoftKeyBoard() {
        val imm = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    fun checkInput(): Boolean {

        getInputText()

        if(newRecipeName.isEmpty()){
            binding.recipeAddEditTextName.setError(resources.getString(R.string.required_field))

        }else if(newRecipeDescription.isEmpty()){
            binding.recipeAddEditTextDescription.setError(resources.getString(R.string.required_field))

        }else if(newRecipeTime.isEmpty()){
            binding.recipeAddEditTextMinutes.setError(resources.getString(R.string.required_field))

        }else if(newRecipeCalories.isEmpty()){
            binding.recipeAddEditTextCalories.setError(resources.getString(R.string.required_field))

        }else if(newRecipeRates.isEmpty()){
            binding.recipeAddEditTextRate.setError(resources.getString(R.string.required_field))

        }else if(newRecipeIngredients.isEmpty()){
            binding.recipeAddEditTextIngredient.setError(resources.getString(R.string.required_field))

        }else if(newRecipeSteps.isEmpty()){
            binding.recipeAddEditTextSteps.setError(resources.getString(R.string.required_field))

        }else{
            return true
        }

        return false

    }

    fun getInputText(){

        newRecipeName = binding.recipeAddEditTextName.text.toString()
        newRecipeDescription = binding.recipeAddEditTextDescription.text.toString()
        newRecipeCalories = binding.recipeAddEditTextCalories.text.toString()
        newRecipeRates = binding.recipeAddEditTextRate.text.toString()
        newRecipeTime = binding.recipeAddEditTextMinutes.text.toString()
        newRecipeIngredients = binding.recipeAddEditTextIngredient.text.toString()
        newRecipeSteps = binding.recipeAddEditTextSteps.text.toString()

    }

    fun setUpSpinner() {

        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.recipetypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.recipeAddSpinner.adapter = adapter

        }

        binding.recipeAddSpinner.onItemSelectedListener = this


    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {

        recipeSelectedType = parent.getItemAtPosition(position).toString()
        val recipesArray: Array<String> = resources.getStringArray(R.array.recipetypes)

        when (parent.getItemAtPosition(position)) {
            recipesArray[0] -> {
            }
            recipesArray[1] -> {
            }
            recipesArray[2] -> {
            }
            recipesArray[3] -> {
            }
            recipesArray[4] -> {
            }
            else -> {}
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    companion object {
        private val IMAGE_PICK_CODE = 1000
        private val PERMISSION_CODE = 1001
    }


}