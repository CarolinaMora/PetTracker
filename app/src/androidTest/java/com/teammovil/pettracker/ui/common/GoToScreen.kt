package com.teammovil.pettracker.ui.common

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.teammovil.pettracker.R

object GoToScreen {

    fun goToRescuerPetsScreen() {
        Espresso.onView(ViewMatchers.withId(R.id.rescatista_Btn)).perform(
            ViewActions.click()
        )

        Espresso.onView(ViewMatchers.withId(R.id.rescuer_email)).perform(
            ViewActions.replaceText("jorge@rescuer.com"),
            ViewActions.closeSoftKeyboard(),
        )

        Espresso.onView(ViewMatchers.withId(R.id.rescuer_password)).perform(
            ViewActions.replaceText("jorge123"),
            ViewActions.closeSoftKeyboard(),
        )

        Espresso.onView(ViewMatchers.withId(R.id.rescuer_login_Btn)).perform(
            ViewActions.scrollTo(),
            ViewActions.click()
        )

    }
}