package com.teammovil.pettracker.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import com.teammovil.pettracker.R
import com.teammovil.pettracker.ui.common.GoToScreen
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class EditPetUITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule var activityScenarioRule = activityScenarioRule<Welcome>()

    @Test
    fun openEditPetScreenFromDetailLoadData(){
        GoToScreen.goToRescuerPetsScreen()

        Thread.sleep(3000)

        Espresso.onView(withId(R.id.registered_pets_recycler)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click())
        )

        Thread.sleep(3000)

        Espresso.onView(withId(R.id.bt_edit)).perform(
            ViewActions.click()
        )

        Thread.sleep(3000)

        Espresso.onView(withId(R.id.pet_registration_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("Max")))
    }
}