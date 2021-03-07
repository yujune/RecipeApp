package com.example.recipeapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.DataSource
import com.example.recipeapp.R
import com.example.recipeapp.RecipeRecyclerAdapter
import com.example.recipeapp.databinding.FragmentRecipeListBinding
import com.example.recipeapp.model.Recipe

class RecipeListFragment : Fragment(), RecipeRecyclerAdapter.OnItemClickListener {

    lateinit var recipeAdapter: RecipeRecyclerAdapter
    private var _binding: FragmentRecipeListBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeListBinding.inflate(inflater, container, false)
        val view = binding.root
        initRecylerView()
        addDataSet()
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

    private fun addDataSet(){
        val data = DataSource.createDataSet()
        recipeAdapter.submitList(data)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClicked(recipe: Recipe) {
        Toast.makeText(context,"User name ${recipe.title}", Toast.LENGTH_LONG)
            .show()
        findNavController().navigate(R.id.action_recipeListFragment_to_recipeDetailsFragment)
    }


}