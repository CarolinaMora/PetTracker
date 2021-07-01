package com.teammovil.pettracker.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import com.teammovil.pettracker.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import java.util.regex.Pattern.matches

@HiltAndroidTest
class LoginAdopterUITest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(Welcome::class.java, true, false)


    private fun goToLoginAdopterScreen() {
        Espresso.onView(withId(R.id.adoptante_Btn)).perform(
            ViewActions.click())
    }

    @Test
    fun loginAdopterFromWelcomeUITest() {
        activityRule.launchActivity(null)
        goToLoginAdopterScreen()

        Thread.sleep(2000)

        Espresso.onView(withId(R.id.action_bar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText("Bienvenido Adoptante"))))

    }

    @Test
    fun clickLoginButtonOfVoidAdopterFailed() {
        activityRule.launchActivity(null)
        goToLoginAdopterScreen()

        Espresso.onView(withId(R.id.adopter_login_Btn)).perform(
            ViewActions.click()
        )
        Espresso.onView(withId(R.id.email_adopted))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Correo requerido")))

        Espresso.onView(withId(R.id.pass_adopted))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Contraseña requerida")))

    }
    @Test
    fun clickAdopterRegisterButton() {
        activityRule.launchActivity(null)
        goToLoginAdopterScreen()

        Espresso.onView(withId(R.id.Btn_cancel)).perform(
            ViewActions.click()
        )
        Thread.sleep(2000)
        
        Espresso.onView(withId(R.id.action_bar))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(withText("Registro Adoptante"))))

    }
}
