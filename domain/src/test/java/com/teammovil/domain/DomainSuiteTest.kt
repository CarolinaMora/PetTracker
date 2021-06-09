package com.teammovil.domain

import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    AdopterValidationTest::class,
    RescuerValidatorTest::class,
    PetValidationTest::class,
    EvidenceTest::class
)
class DomainSuiteTest