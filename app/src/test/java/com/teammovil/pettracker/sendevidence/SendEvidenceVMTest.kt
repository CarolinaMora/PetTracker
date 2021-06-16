package com.teammovil.pettracker.sendevidence

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.teammovil.pettracker.ui.sendevidence.SendEvidenceViewModel
import com.teammovil.usecases.SaveEvidenceUseCase
import org.junit.Rule
import org.mockito.Mock
import com.teammovil.domain.Result
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.teammovil.pettracker.R
import com.teammovil.pettracker.fakes.mockEvidenceView
import com.teammovil.pettracker.ui.common.Mapper
import com.teammovil.testshared.mockEvidence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class SendEvidenceVMTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var sendEvidenceUseCase: SaveEvidenceUseCase

    @Mock
    lateinit var observer: Observer<SendEvidenceViewModel.UiModel>

    private lateinit var vm: SendEvidenceViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        vm = SendEvidenceViewModel(sendEvidenceUseCase, Dispatchers.Unconfined)
    }

    @Test
    fun `when send evidence button, loading is shown`() {
        runBlocking {
            whenever(sendEvidenceUseCase.invoke("1", mockEvidence)).thenReturn(Result(Unit,null))
            vm.model.observeForever(observer)

            vm.onSaveEvidence("1", mockEvidenceView)

            verify(observer).onChanged(SendEvidenceViewModel.UiModel.Loading)

        }
    }

    @Test
    fun `when called sendEvidence use case, show success advice is called`() {
        runBlocking {
            whenever(sendEvidenceUseCase.invoke("1", Mapper.map(mockEvidenceView))).thenReturn(Result(Unit,null))
            vm.model.observeForever(observer)

            vm.onSaveEvidence("1", mockEvidenceView)

            verify(observer).onChanged(SendEvidenceViewModel.UiModel.Loading)
            verify(observer).onChanged(SendEvidenceViewModel.UiModel.SuccessNotification(R.string.send_evidence_ok))

        }
    }
}