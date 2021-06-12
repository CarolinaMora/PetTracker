package com.teammovil.usecases

import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.testshared.mockAdopter
import com.teammovil.usecases.loginadopter.LoginAdopterUseCase
import org.junit.Assert
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoginAdopterUseCaseTest {

    @Mock
    lateinit var adopterRepository: AdopterRepository

    lateinit var loginAdopterUseCase: LoginAdopterUseCase

    @Before
    fun setUp(){
        loginAdopterUseCase = LoginAdopterUseCase(adopterRepository)
    }

    @Test
    fun `LoginAdopterUseCase valid when validations return ok`(){
        runBlocking {
            val email = mockAdopter.email
            val password = mockAdopter.password
            val spiedLogin = spy(loginAdopterUseCase)
            whenever(spiedLogin.validateUser(email, password)).thenReturn(Result(Unit, null))
            whenever(adopterRepository.login(email, password)).thenReturn(true)

            val result = spiedLogin.invoke(email, password).success != null

            verify(spiedLogin).validateUser(email, password)
            verify(adopterRepository).login(email, password)
            Assert.assertTrue(result)

        }
    }

    @Test
    fun `LoginAdopterUseCase invalid when login Return failure`(){
        runBlocking {
            val email = mockAdopter.email
            val password = mockAdopter.password
            val spiedUseCase = spy(loginAdopterUseCase)
            whenever(spiedUseCase.validateUser(email, password)).thenReturn(Result(null, listOf(Error(0))))

            val result = spiedUseCase.invoke(email, password).success != null

            verify(spiedUseCase).validateUser(email, password)
            Assert.assertTrue(result)
        }
    }
}