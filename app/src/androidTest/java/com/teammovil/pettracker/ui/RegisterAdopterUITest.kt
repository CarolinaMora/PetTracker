package com.teammovil.pettracker.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.teammovil.pettracker.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@LargeTest
@HiltAndroidTest
class RegisterAdopterUITest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule var activityScenarioRule = activityScenarioRule<Welcome>()

    @Test
    fun registerAdopterFromWelcomeUITest(){
        goToAdopterRegistrationScreen()

        Espresso.onView(withId(R.id.action_bar)).check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText("Registro Adoptante"))))
    }

    @Test
    fun clickRegisterButtonOfVoidAdopterFailed(){
        goToAdopterRegistrationScreen()

        Espresso.onView(withId(R.id.btn__adopter_register)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )

        Espresso.onView(withId(R.id.adopter_registration_name))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Este campo es requerido")))

    }

    private fun goToAdopterRegistrationScreen(){
        Espresso.onView(withId(R.id.adoptante_Btn)).perform(
            ViewActions.click()
        )

        Espresso.onView(withId(R.id.Btn_cancel)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )
    }
}