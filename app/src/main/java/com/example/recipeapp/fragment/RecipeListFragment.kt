package com.example.recipeapp.fragment

import android.os.Bundle
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


class RecipeListFragment : Fragment(), RecipeRecyclerAdapter.OnItemClickListener, AdapterView.OnItemSelectedListener {


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
        setHasOptionsMenu(true)

        recipeViewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)


//        var recipe1 = Recipe(
//            100,
//            "Macarons",
//            "Sweet, Tasty, Colourful!",
//            30,
//            300,
//            4.5,
//            "Dessert",
//            false,
//            "https://live.staticflickr.com/65535/50414595221_b1a0015dd5_b.jpg",
//            "- eggs\n- flour\n- sugar\n- chocolate\n- milk\n",
//            "1. Prepare a bowl of hot water. \n2. Pour the hot water and mix with the flour,eggs.\n3. Add 100g sugar and 200g chocolate.\n4. Put into oven and wait for 20m.\n"
//        )
//
//        var recipe2 = Recipe(
//            101,
//            "Italian Pasta",
//            "Hot and Spicy!",
//            60,
//            12000,
//            4.9,
//            "Main Course",
//            false,
//            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcREO9BGqz7ZOi60lnedwPTVZ9Q6LzvRU5ZkMg&usqp=CAU",
//            "- pasta\n- flour\n- sugar\n- chilli sauce\n- soy sauce\n- salt\n",
//            "1. Prepare a bowl of hot water. \n2. Pour the hot water and mix with the flour,eggs.\n3. Add 100g sugar and 200g chocolate.\n4. Put into oven and wait for 20m.\n"
//        )
//
//        var recipe3 = Recipe(
//            102,
//            "Peanut Cake",
//            "A crunchy and sweet cake.",
//            100,
//            5000,
//            4.0,
//            "Dessert",
//            false,
//            "https://www.wholesomeyum.com/wp-content/uploads/2018/03/wholesomeyum-low-carb-keto-sugar-free-carrot-cake-recipe-3.jpg",
//            "- peanut\n- flour\n- sugar\n- butter\n- water\n- white cream\n- eggs\n",
//            "1. Prepare a bowl of hot water. \n2. Pour the hot water and mix with the flour,eggs.\n3. Add 100g sugar and 200g chocolate.\n4. Put into oven and wait for 20m.\n"
//        )
//
//        var recipe4 = Recipe(
//            103,
//            "Chocolate Donut",
//            "Sweet + Sweet + Sweet!",
//            20,
//            1000,
//            4.9,
//            "Dessert",
//            false,
//            "https://d2gg9evh47fn9z.cloudfront.net/800px_COLOURBOX2176502.jpg",
//            "- chocolate cream\n- flour\n- sugar\n- butter\n- water\n- white cream\n- eggs\n",
//            "1. Prepare a bowl of hot water. \n2. Pour the hot water and mix with the flour,eggs.\n3. Add 100g sugar and 200g chocolate.\n4. Put into oven and wait for 20m.\n"
//        )
//
//
//        var recipe5 = Recipe(
//            104,
//            "Spagethi",
//            "Spicy and Sour.",
//            200,
//            19800,
//            5.0,
//            "Main Course",
//            false,
//            "https://i.pinimg.com/originals/6f/b9/9b/6fb99bb17e4ac1bfc65fb1d1d5cc8776.jpg",
//            "- spagethi noodles\n- flour\n- salt\n- hot water\n- soy sauce\n- eggs\n",
//            "1. Prepare a bowl of hot water. \n2. Pour the hot water and mix with the flour,eggs.\n3. Add 100g sugar and 200g chocolate.\n4. Put into oven and wait for 20m.\n"
//        )
//
//        recipeViewModel.addRecipe(recipe1)
//        recipeViewModel.addRecipe(recipe2)
//        recipeViewModel.addRecipe(recipe3)
//        recipeViewModel.addRecipe(recipe4)
//        recipeViewModel.addRecipe(recipe5)


        //observe the livedata obj, whenener we chg the data, the adpater can set the value
        recipeViewModel.readAllData.observe(viewLifecycleOwner, Observer { recipe ->
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
        val item : MenuItem = menu.findItem(R.id.spinner)
        val spinner: Spinner = item.actionView as Spinner
        val adapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.recipetypes,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.setAdapter(adapter);
        spinner.onItemSelectedListener = this
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        (parent.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(parent.context,R.color.white))
        Toast.makeText(getActivity(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show()
        val recipesArray: Array<String> = resources.getStringArray(R.array.recipetypes)

        when(parent.getItemAtPosition(pos)){
            recipesArray[0] -> Toast.makeText(
                getActivity(),
                "Breakfast from spinner",
                Toast.LENGTH_SHORT
            ).show()
            recipesArray[1] -> Toast.makeText(
                getActivity(),
                "Lunch from spinner",
                Toast.LENGTH_SHORT
            ).show()
            recipesArray[2] -> Toast.makeText(
                getActivity(),
                "Dinner from spinner",
                Toast.LENGTH_SHORT
            ).show()
            recipesArray[3] -> Toast.makeText(
                getActivity(),
                "Snacks from spinner",
                Toast.LENGTH_SHORT
            ).show()
            recipesArray[4] -> Toast.makeText(
                getActivity(),
                "Dessert from spinner",
                Toast.LENGTH_SHORT
            ).show()
            recipesArray[5] -> Toast.makeText(
                getActivity(),
                "Main Course from spinner",
                Toast.LENGTH_SHORT
            ).show()
            else -> {

        }
        }
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }

    companion object{
        const val RECIPE_ID = "com.example.recipeapp.fragment.recipeId"
    }
}