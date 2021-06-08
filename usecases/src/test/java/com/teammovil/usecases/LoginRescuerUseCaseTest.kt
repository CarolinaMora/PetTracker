package com.teammovil.usecases


import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.domain.Result
import com.teammovil.testshared.mockRescuer
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
    fun `LoginAdopterUseCase valid when validations return ok`(){
        runBlocking {
            val user = mockRescuer.email
            val password = mockRescuer.password

            val spiedLogin = com.nhaarman.mockitokotlin2.spy(loginRescuerUseCase)
            whenever(spiedLogin.validateUser(user, password)).thenReturn(Result(Unit, null))
            whenever(rescuerRepository.login(user, password)).thenReturn(true)

            val result = spiedLogin.invoke(user, password).success != null

            verify(spiedLogin).validateUser(user, password)
            verify(rescuerRepository).login(user, password)
            Assert.assertTrue(result)
        }
    }
}