package com.teammovil.pettracker.sendevidence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.teammovil.pettracker.R
import com.teammovil.pettracker.fakes.FakeData
import com.teammovil.pettracker.ui.sendevidence.SendEvidenceViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class SendEvidenceIntegrationTests {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<SendEvidenceViewModel.UiModel>

    lateinit var vm: SendEvidenceViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vm = FakeData.fakeSendEvidenceViewModel
    }

    @Test
    fun `loading and SuccessNotification Test` (){
        vm.model.observeForever(observer)

        vm.onSaveEvidence("1", FakeData.fakeEvidenceView)

        verify(observer).onChanged(SendEvidenceViewModel.UiModel.Loading)
        verify(observer).onChanged(SendEvidenceViewModel.UiModel.SuccessNotification(R.string.send_evidence_ok) )
    }
}