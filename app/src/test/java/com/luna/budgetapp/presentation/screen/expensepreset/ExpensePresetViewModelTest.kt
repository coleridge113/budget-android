package com.luna.budgetapp.presentation.screen.expensepreset

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.luna.budgetapp.MainDispatcherRule
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.usecase.UseCases
import org.junit.Rule
import io.mockk.mockk
import io.mockk.coVerify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ExpensePresetViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCases = mockk<UseCases>(relaxed = true)
    private val pusherManager = mockk<PusherManager>(relaxed = true)
    private val repository = mockk<ExpensePresetRepository>(relaxed = true)

    private lateinit var viewModel: ExpensePresetViewModel

    @Before
    fun setup() {
        viewModel = ExpensePresetViewModel(useCases, pusherManager, repository)
    }

    @Test
    fun `init block should initialize and subscribe to pusher`() = runTest {
        coVerify { pusherManager.initPusher() }
        coVerify { pusherManager.subscribeToExpenseChannel(any()) }
    }

    @Test
    fun `AddExpensePreset event should add a preset to the success list`() = runTest {
        viewModel.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.success).isEmpty()

            viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)

            val updatedState = awaitItem()
            assertThat(updatedState.success).hasSize(1)
            assertThat(updatedState.success.first().category).isEqualTo("Streaming")
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `multiple AddExpensePreset events should append to the list`() = runTest {
        viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)
        viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)

        val finalState = viewModel.uiState.value
        assertThat(finalState.success).hasSize(2)
    }
}

