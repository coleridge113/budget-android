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
    fun `dialog shows when clicking add expense preset`() = runTest {
        viewModel.uiState.test {
            val initial = awaitItem()
            assertThat(initial.isDialogVisible).isFalse()

            viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)

            val final = awaitItem()
            assertThat(final.isDialogVisible).isTrue()
            expectNoEvents()
        }
    }


    @Test
    fun `confirming dialog updates list and dismisses it`() = runTest {
        viewModel.uiState.test {
            skipItems(1)

            viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)
            awaitItem() // shown

            viewModel.onEvent(ViewModelStateEvents.Event.ConfirmDialog("Coffee", "10.0"))
            val dismissed = awaitItem()

            assertThat(dismissed.isDialogVisible).isFalse()
            assertThat(dismissed.expensePresets).hasSize(1)
        }
    }

    @Test
    fun `rapidly clicking confirm should only add one item`() = runTest {
        viewModel.uiState.test {
            awaitItem()

            viewModel.onEvent(ViewModelStateEvents.Event.ConfirmDialog("Coffee", "10.0"))
            viewModel.onEvent(ViewModelStateEvents.Event.ConfirmDialog("Coffee", "10.0"))

            awaitItem()
            assertThat(viewModel.uiState.value.expensePresets).hasSize(1)
            expectNoEvents()
        }
    }
}

