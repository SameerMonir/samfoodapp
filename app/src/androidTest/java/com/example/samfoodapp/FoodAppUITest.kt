package com.example.samfoodapp

import android.view.View
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.samfoodapp.ui.MainActivity
import org.junit.Assert
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class FoodAppUITest {
    @Rule
    @JvmField
    val activityRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testRecyclerVisible() {
        activityRule.scenario.onActivity { mainActivity ->
            Assert.assertEquals(View.VISIBLE,mainActivity.findViewById<View?>(R.id.rvCategories).visibility)
        }
    }
}