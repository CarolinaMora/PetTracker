package com.teammovil.usecases

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    RegisterPetUseCaseTest::class,
    EditPetUseCaseTest::class,
    PetDetailUseCaseTest::class,
    SendEvidenceUseCaseTest::class,
    RegisterAdopterUseCaseTest::class,
    AssignAdopterToPetUseCaseTest::class,
    GetAllAdoptersUseCaseTest::class,
    GetAdopterPetsUseCaseTest::class,
    RegisterRescuerUseCaseTest::class,
    GetRescuerPetsTest::class
)
class UseCasesSuiteTest