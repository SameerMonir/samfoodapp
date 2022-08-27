package com.example.samfoodapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.samfoodapp.R
import com.example.samfoodapp.databinding.FragmentRecipeBinding
import com.example.samfoodapp.models.Meal
import com.example.samfoodapp.ui.MainActivity
import com.example.samfoodapp.ui.viewModels.FoodViewModel
import com.example.samfoodapp.util.Resource
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.fragment_recipe.*

class RecipeFragment : Fragment(R.layout.fragment_recipe) {

    companion object {
        const val TAG = "RecipeFragment"
    }
    private lateinit var viewModel:FoodViewModel
    private lateinit var binding:FragmentRecipeBinding
    private val args:RecipeFragmentArgs by navArgs()
    private lateinit var youTubePlayerView: YouTubePlayerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_recipe,container,false)

        viewModel=(activity as MainActivity).viewModel

        val id=args.recipeID

        viewModel.getRecipeById(id)

        viewModel.recipes.observe(viewLifecycleOwner, Observer { response->
            when(response){
                is Resource.Success->{
                    hideProgressBar()
                    response.data?.let{
                        val meal= it.meals?.get(0)
                        setRecipe(meal)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(requireContext(),"An error occurred", Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })

        return binding.root
    }

    private fun setRecipe(meal: Meal?) {
        Glide.with(binding.root)
            .load(meal?.strMealThumb)
            .into(binding.recipeImage)

        binding.recipeText.text = meal?.strMeal

        binding.recipeIngredient.text = meal?.let { viewModel.getIngredients(it) }

        binding.recipeInstruction.text = meal?.strInstructions

        youTubePlayerView=binding.youtubePlayerView
        lifecycle.addObserver(youTubePlayerView)
        youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = meal?.let { viewModel.getVideoId(it) }
                youTubePlayer.pause()
                if (videoId != null) {
                    youTubePlayer.cueVideo(videoId, 0f)
                }
            }
        })

    }

    private fun hideProgressBar() {
        binding.recipeScrollView.visibility=View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.recipeScrollView.visibility=View.INVISIBLE
        binding.progressBar.visibility = View.VISIBLE
    }
}