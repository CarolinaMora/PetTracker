package com.teammovil.usecases


import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Error
import com.teammovil.domain.Result
import com.teammovil.testshared.mockAdopter
import com.teammovil.testshared.mockRescuer
import com.teammovil.usecases.loginadopter.LoginAdopterUseCase
import com.teammovil.usecases.loginrescuer.LoginRescuerUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before


import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class LoginRescuerUseCaseTest {


    @Mock
    lateinit var rescuerRepository: RescuerRepository

    lateinit var loginRescuerUseCase: LoginRescuerUseCase

    @Before
    fun setUp(){
        loginRescuerUseCase = LoginRescuerUseCase(rescuerRepository)
    }

    @Test
    fun LoginRescuerUseCase(){
        Assert.assertTrue(loginRescuerUseCase.rescuerRepository is RescuerRepository)
    }

    @Test
    fun `LoginRescuerUseCase valid when validations return ok`(){
        runBlocking {
            val user = mockRescuer.email
            val password = mockRescuer.password

            val spiedLogin = spy(loginRescuerUseCase)
            whenever(spiedLogin.validateUser(user, password)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.login(user, password)).thenReturn(true)

            val result = spiedLogin.invoke(user, password).success != null

            verify(spiedLogin).validateUser(user, password)
            verify(rescuerRepository).login(user, password)
            Assert.assertTrue(result)
        }
    }

    @Test
    fun `LoginRescuerUseCase invalid when login Return failure`(){
        runBlocking {
            val email = mockRescuer.email
            val password = mockRescuer.password
            val spiedUseCase = spy(loginRescuerUseCase)
            whenever(spiedUseCase.validateUser(email, password)).thenReturn(Result(null, listOf(
                Error(0)
            )))

            val result = spiedUseCase.invoke(email, password).success != null

            verify(spiedUseCase).validateUser(email, password)
            Assert.assertTrue(result)
        }
    }
}