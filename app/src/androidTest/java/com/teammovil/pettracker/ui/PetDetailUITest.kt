package com.teammovil.pettracker.ui

import android.util.Log

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Pet
import com.teammovil.pettracker.R
import com.teammovil.usecases.rescuerPets.GetRescuerPets
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PetDetailUITest {
    @get:Rule
    var hitRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(Welcome::class.java, true, false)


    @Test
    fun loadingPets(){
        activityRule.launchActivity(null)
        goToFirstRescuerPetsScreen()


        Espresso.onView(withId(R.id.txtName)).check(
            matches(withText("búho "))
        )
        Espresso.onView(withId(R.id.txtGender)).check(
            matches(withText("FEMALE"))
        )
        Espresso.onView(withId(R.id.txtDesc)).check(
            matches(withText("xxx"))
        )
    }

    private fun goToFirstRescuerPetsScreen() {
        Espresso.onView(withId(R.id.rescatista_Btn)).perform(
            ViewActions.click()
        )

        Espresso.onView(withId(R.id.rescuer_email)).perform(
            ViewActions.replaceText("neicaro@gmail.com"),
            ViewActions.closeSoftKeyboard(),
        )

        Espresso.onView(withId(R.id.rescuer_password)).perform(
            ViewActions.replaceText("neicaro8"),
            ViewActions.closeSoftKeyboard(),
        )

        Espresso.onView(withId(R.id.rescuer_login_Btn)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )

        Thread.sleep(3000)

        Espresso.onView(withId(R.id.registered_pets_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )
        Thread.sleep(3000)
    }
}