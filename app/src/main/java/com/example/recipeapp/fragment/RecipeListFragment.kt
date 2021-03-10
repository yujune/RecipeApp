package com.example.recipeapp.fragment

import android.os.Bundle
import android.os.Environment
import android.view.*
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recipeapp.R
import com.example.recipeapp.RecipeRecyclerAdapter
import com.example.recipeapp.database.Recipe
import com.example.recipeapp.databinding.FragmentRecipeListBinding
import com.example.recipeapp.viewmodel.RecipeViewModel


class RecipeListFragment : Fragment(), RecipeRecyclerAdapter.OnItemClickListener,
    AdapterView.OnItemSelectedListener {

    private lateinit var recipeAdapter: RecipeRecyclerAdapter
    private var _binding: FragmentRecipeListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recipeViewModel: RecipeViewModel
    private var recipeSelectedType: String = "Breakfast"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        val view = binding.root
        initRecylerView()
        setHasOptionsMenu(true)

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)

        binding.recipeFloatingActionAdd.setOnClickListener { view ->
            navigateToRecipeAddFragment()
        }

        return view
    }

    private fun navigateToRecipeAddFragment() {
        findNavController().navigate(R.id.action_recipeListFragment_to_recipeAddFragment)
    }

    private fun initRecylerView() {
        binding.recommendRecylerView.apply {
            layoutManager = LinearLayoutManager(context.applicationContext)
            recipeAdapter = RecipeRecyclerAdapter(this@RecipeListFragment)
            adapter = recipeAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(recipe: Recipe) {
        Toast.makeText(context, "${recipe.recipeName}", Toast.LENGTH_LONG)
            .show()
        val bundle = Bundle()
        bundle.putLong(RECIPE_ID, recipe.recipeID)
        findNavController().navigate(
            R.id.action_recipeListFragment_to_recipeDetailsFragment,
            bundle
        )
    }

    //overflow menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.recipetypes_spinner, menu)
        val item: MenuItem = menu.findItem(R.id.spinner)
        val spinner: Spinner = item.actionView as Spinner
        ArrayAdapter.createFromResource(
            this.requireContext(),
            R.array.recipetypes,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

        }
        spinner.onItemSelectedListener = this
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        (parent.getChildAt(0) as TextView).setTextColor(
            ContextCompat.getColor(
                parent.context,
                R.color.white
            )
        )
        recipeSelectedType = parent.getItemAtPosition(pos).toString()
        val recipesArray: Array<String> = resources.getStringArray(R.array.recipetypes)
        recipeViewModel.getRecipeByType(recipeSelectedType).observe(viewLifecycleOwner, { recipe ->
            recipeAdapter.submitListByType(recipe)
        })

        when (parent.getItemAtPosition(pos)) {
            recipesArray[0] -> {}
            recipesArray[1] -> {}
            recipesArray[2] -> {}
            recipesArray[3] -> {}
            recipesArray[4] -> {}
            recipesArray[5] -> {}
            else -> {}
        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    companion object {
        const val RECIPE_ID = "com.example.recipeapp.fragment.recipeId"
    }
}