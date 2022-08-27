package com.example.samfoodapp


import com.example.samfoodapp.models.CategoriesResponse
import com.example.samfoodapp.models.FilterResponse
import com.example.samfoodapp.repository.FoodRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class FoodRepositoryTest {

    @MockK
    lateinit var foodRepository: FoodRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun checkIfMockedCategoriesareMatchingorNot(){
        val categoriesResponse = mockk<retrofit2.Response<CategoriesResponse>>()
        every { runBlocking { foodRepository.getCategories() } } returns (categoriesResponse)
        runBlocking {
            val result = foodRepository.getCategories()
            MatcherAssert.assertThat(
                "Received result [$result] & mocked [$categoriesResponse] must be matches on each other!",
                result,
                CoreMatchers.`is`(categoriesResponse)
            )
        }
    }


    @Test
    fun checkIfMockedFilterByCategoryareMatchingorNot(){
        val filterResponse = mockk<retrofit2.Response<FilterResponse>>()
        every { runBlocking { foodRepository.getFilterByCategory(filterCategory) } } returns (filterResponse)
        runBlocking {
            val result = foodRepository.getFilterByCategory(filterCategory)
            MatcherAssert.assertThat(
                "Received result [$result] & mocked [$filterResponse] must be matches on each other!",
                result,
                CoreMatchers.`is`(filterResponse)
            )
        }
    }

    @Test
    fun checkIfMockedFilterByAreaareMatchingorNot(){
        val filterResponse = mockk<retrofit2.Response<FilterResponse>>()
        every { runBlocking { foodRepository.getFilterByArea(filterArea) } } returns (filterResponse)
        runBlocking {
            val result = foodRepository.getFilterByArea(filterArea)
            MatcherAssert.assertThat(
                "Received result [$result] & mocked [$filterResponse] must be matches on each other!",
                result,
                CoreMatchers.`is`(filterResponse)
            )
        }
    }

    @Test
    fun checkIfMockedFilterByIngredientareMatchingorNot(){
        val filterResponse = mockk<retrofit2.Response<FilterResponse>>()
        every { runBlocking { foodRepository.getFilterByIngredient(filterIngredient) } } returns (filterResponse)
        runBlocking {
            val result = foodRepository.getFilterByIngredient(filterIngredient)
            MatcherAssert.assertThat(
                "Received result [$result] & mocked [$filterResponse] must be matches on each other!",
                result,
                CoreMatchers.`is`(filterResponse)
            )
        }
    }

    @Test
    fun checkIfMockedAreaListareMatchingorNot(){
        val areaListResponse = mockk<retrofit2.Response<FilterResponse>>()
        every { runBlocking { foodRepository.getAreaList() } } returns (areaListResponse)
        runBlocking {
            val result = foodRepository.getAreaList()
            MatcherAssert.assertThat(
                "Received result [$result] & mocked [$areaListResponse] must be matches on each other!",
                result,
                CoreMatchers.`is`(areaListResponse)
            )
        }
    }



    @Test
    fun checkSearchByNameResultIsValidOrNot(){
        val searchResponse = mockk<retrofit2.Response<FilterResponse>>()
        every { runBlocking { foodRepository.search(searchKey) } } returns (searchResponse)
        runBlocking {
            val result = foodRepository.search(searchKey)
            org.junit.Assert.assertNotNull(result)
            MatcherAssert.assertThat(
                "Received result [$result] & mocked [$searchResponse] must be matches on each other!",
                result,
                CoreMatchers.`is`(searchResponse)
            )
        }
    }

    companion object{
        const val filterCategory="chicken"
        const val filterArea="british"
        const val filterIngredient="milk"
        const val searchKey="tuna"
    }

}