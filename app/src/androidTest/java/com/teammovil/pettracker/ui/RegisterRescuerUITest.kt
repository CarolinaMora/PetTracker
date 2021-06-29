package com.teammovil.pettracker.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.teammovil.pettracker.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class RegisterRescuerUITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(Welcome::class.java, true, false)

    @Test
    fun registerRescuerFromWelcomeUITest() {
        activityRule.launchActivity(null)
        goToRescuerRegistrationScreen()

        Espresso.onView(withId(R.id.action_bar))
            .check(ViewAssertions.matches(hasDescendant(ViewMatchers.withText("Registro Rescatista"))))
    }

    @Test
    fun clickRegisterButtonOfVoidRescuerFailed() {
        activityRule.launchActivity(null)
        goToRescuerRegistrationScreen()

        Espresso.onView(withId(R.id.btn_rescuer_register)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )

        Espresso.onView(withId(R.id.rescuer_registration_name))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Este campo es requerido")))

    }

    private fun goToRescuerRegistrationScreen(){
        Espresso.onView(withId(R.id.rescatista_Btn)).perform(
            ViewActions.click()
        )

        Espresso.onView(withId(R.id.Btn_cancel)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )
    }


}