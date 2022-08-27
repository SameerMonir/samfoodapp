package com.example.samfoodapp.ui.viewModels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.samfoodapp.FoodApplication
import com.example.samfoodapp.models.CategoriesResponse
import com.example.samfoodapp.models.FilterResponse
import com.example.samfoodapp.models.Meal
import com.example.samfoodapp.models.RecipeResponse
import com.example.samfoodapp.repository.FoodRepository
import com.example.samfoodapp.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.util.regex.Matcher
import java.util.regex.Pattern

class FoodViewModel(
    app: Application,
    private val repository: FoodRepository
) : AndroidViewModel(app) {


    val categories = MutableLiveData<Resource<CategoriesResponse>>()
    var categoriesResponse: CategoriesResponse? = null

    val filters = MutableLiveData<Resource<FilterResponse>>()
    var filtersResponse: FilterResponse? = null

    val recipes = MutableLiveData<Resource<RecipeResponse>>()
    var recipeResponse: RecipeResponse? = null

    fun getCategories() = viewModelScope.launch {
        safeCategoriesCall()
    }

    private suspend fun safeCategoriesCall() {
        categories.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getCategories()
                categories.postValue(handleCategoriesResponse(response = response))
            } else {
                categories.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> categories.postValue(Resource.Error(message = "Network failure"))
                else -> categories.postValue(Resource.Error(message = "Conversion error"))
            }
        }


    }

    private fun handleCategoriesResponse(response: Response<CategoriesResponse>): Resource<CategoriesResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

    fun getIngredientList() = viewModelScope.launch {
        safeIngredientListCall()
    }

    private suspend fun safeIngredientListCall() {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getIngredientList()
                filters.postValue(handleListResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }

    }

    fun getAreaList() = viewModelScope.launch {
        safeAreaListCall()
    }

    private suspend fun safeAreaListCall() {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getAreaList()
                filters.postValue(handleListResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }

    }

    private fun handleListResponse(response: Response<FilterResponse>): Resource<FilterResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

    fun getFilterByCategory(filterQuery: String) = viewModelScope.launch {
        safeFilterByCategoryCall(filterQuery)
    }

    private suspend fun safeFilterByCategoryCall(filterQuery: String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getFilterByCategory(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }


    }

    fun getFilterByArea(filterQuery: String) = viewModelScope.launch {
        safeFilterByAreaCall(filterQuery)
    }

    private suspend fun safeFilterByAreaCall(filterQuery: String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getFilterByArea(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    fun getFilterByIngredient(filterQuery: String) = viewModelScope.launch {
        safeFilterByIngredientCall(filterQuery)
    }

    private suspend fun safeFilterByIngredientCall(filterQuery: String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getFilterByIngredient(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }


    }

    fun search(filterQuery: String) = viewModelScope.launch {
        safeSearch(filterQuery)
    }

    private suspend fun safeSearch(filterQuery: String) {
        filters.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.search(filterQuery)
                filters.postValue(handleFilterResponse(response = response))
            } else {
                filters.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> filters.postValue(Resource.Error(message = "Network failure"))
                else -> filters.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    private fun handleFilterResponse(response: Response<FilterResponse>): Resource<FilterResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }


    fun getRecipeById(id: String) = viewModelScope.launch {
        safeGetRecipe(id)
    }

    private suspend fun safeGetRecipe(id: String) {
        recipes.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getRecipeById(id)
                recipes.postValue(handleRecipeResponse(response = response))
            } else {
                recipes.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> recipes.postValue(Resource.Error(message = "Network failure"))
                else -> recipes.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    fun getRandomRecipe() = viewModelScope.launch {
        safeRandomRecipe()
    }

    private suspend fun safeRandomRecipe() {
        recipes.postValue(Resource.Loading())

        try {
            if (hasInternetConnection()) {
                val response = repository.getRandomRecipe()
                recipes.postValue(handleRecipeResponse(response = response))
            } else {
                recipes.postValue(Resource.Error(message = "No internet connection"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> recipes.postValue(Resource.Error(message = "Network failure"))
                else -> recipes.postValue(Resource.Error(message = "Conversion error"))
            }
        }
    }

    private fun handleRecipeResponse(response: Response<RecipeResponse>): Resource<RecipeResponse> {
        if (response.isSuccessful) {
            response.body()?.let {
                return Resource.Success(it)
            }
        }

        return Resource.Error(message = response.message())
    }

    private fun hasInternetConnection(): Boolean {
        // for checking the internet connectivity we need a connectivity manager which is system service which requires context and for that we need a context
        // So rather than using the activity context which gets destroyed when the activity get destroyed we use application context

        val connectivityManager = getApplication<FoodApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

    fun getIngredients(meal: Meal): String {

        var ingredients = ""

        if (meal.strIngredient1?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure1 + " " + meal.strIngredient1 + "\n"
        }
        if (meal.strIngredient2?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure2 + " " + meal.strIngredient2 + "\n"
        }
        if (meal.strIngredient3?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure3 + " " + meal.strIngredient3 + "\n"
        }
        if (meal.strIngredient4?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure4 + " " + meal.strIngredient4 + "\n"
        }
        if (meal.strIngredient5?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure5 + " " + meal.strIngredient5 + "\n"
        }
        if (meal.strIngredient6?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure6 + " " + meal.strIngredient6 + "\n"
        }
        if (meal.strIngredient7?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure7 + " " + meal.strIngredient7 + "\n"
        }
        if (meal.strIngredient8?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure8 + " " + meal.strIngredient8 + "\n"
        }
        if (meal.strIngredient9?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure9 + " " + meal.strIngredient9 + "\n"
        }
        if (meal.strIngredient10?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure10 + " " + meal.strIngredient10 + "\n"
        }
        if (meal.strIngredient11?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure11 + " " + meal.strIngredient11 + "\n"
        }
        if (meal.strIngredient12?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure12 + " " + meal.strIngredient12 + "\n"
        }
        if (meal.strIngredient13?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure13 + " " + meal.strIngredient13 + "\n"
        }
        if (meal.strIngredient14?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure14 + " " + meal.strIngredient14 + "\n"
        }
        if (meal.strIngredient15?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure15 + " " + meal.strIngredient15 + "\n"
        }
        if (meal.strIngredient16?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure16 + " " + meal.strIngredient16 + "\n"
        }
        if (meal.strIngredient17?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure17 + " " + meal.strIngredient17 + "\n"
        }
        if (meal.strIngredient18?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure18 + " " + meal.strIngredient18 + "\n"
        }
        if (meal.strIngredient19?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure19 + " " + meal.strIngredient19 + "\n"
        }
        if (meal.strIngredient20?.isNotEmpty() == true) {
            ingredients = ingredients + meal.strMeasure20 + " " + meal.strIngredient20 + "\n"
        }

        return ingredients

    }


    fun getVideoId(meal: Meal): String? {

        val videoUrl=meal.strYoutube

        val expression =
            "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*"

        if (videoUrl == null || videoUrl.trim { it <= ' ' }.isEmpty()) {
            return null
        }
        val pattern: Pattern = Pattern.compile(expression)
        val matcher: Matcher = pattern.matcher(videoUrl)
        try {
            if (matcher.find()) return matcher.group()
        } catch (ex: ArrayIndexOutOfBoundsException) {
            ex.printStackTrace()
        }
        return null
    }
}