package com.luna.budgetapp.presentation.screen.expensepreset

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.luna.budgetapp.MainDispatcherRule
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.domain.usecase.UseCases
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExpensePresetViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val useCases = mockk<UseCases>(relaxed = true)
    private val pusherManager = mockk<PusherManager>(relaxed = true)
    private val expensePresetRepo = mockk<ExpensePresetRepository>(relaxed = true)
    private val expenseRepo = mockk<ExpenseRepository>(relaxed = true)

    private lateinit var viewModel: ExpensePresetViewModel

    @Before
    fun setup() {
        viewModel = ExpensePresetViewModel(useCases, pusherManager, expensePresetRepo, expenseRepo)
    }

    @Test
    fun `dialog shows when clicking add expense preset`() = runTest {
        val initial = viewModel.uiState.value
        assertThat(initial.isDialogVisible).isFalse()

        viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)

        advanceUntilIdle()

        val final = viewModel.uiState.value
        assertThat(final.isDialogVisible).isTrue()
    }

    @Test
    fun `confirming dialog updates preset list and dismisses it`() = runTest {
        viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)

        viewModel.onEvent(
            ViewModelStateEvents.Event.ConfirmDialog(
                "Beverage",
                "Coffee",
                "10.0"
            )
        )

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertThat(state.isDialogVisible).isFalse()
        assertThat(state.expensePresets).hasSize(1)
    }

    @Test
    fun `rapidly clicking confirm should not add multiple expense presets`() = runTest {
        viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)

        viewModel.onEvent(ViewModelStateEvents.Event.ConfirmDialog("Beverage", "Coffee", "10.0"))
        viewModel.onEvent(ViewModelStateEvents.Event.ConfirmDialog("Beverage", "Coffee", "10.0"))
        viewModel.onEvent(ViewModelStateEvents.Event.ConfirmDialog("Beverage", "Coffee", "10.0"))

        advanceUntilIdle()

        val state = viewModel.uiState.value

        assertThat(state.isDialogVisible).isFalse()
        assertThat(state.expensePresets).hasSize(1)
    }

    @Test
    fun `clicking an expense preset adds an expense`() = runTest {
        viewModel.uiState.test {
            awaitItem()

            val preset = ExpensePreset(
                id = 0L,
                amount = 100.0,
                category = "Beverage",
                type = "Coffee"
            )
            viewModel.onEvent(ViewModelStateEvents.Event.AddExpense(preset))
            val state = awaitItem()
            assertThat(state.expenses).hasSize(1)
            assertThat(state.totalAmount).isAtLeast(preset.amount)
        }
    }

    @Test
    fun `confirming an expense preset with empty type defaults to use category string`() = runTest {
        val default = "Food"
        viewModel.onEvent(ViewModelStateEvents.Event.AddExpensePreset)
        viewModel.onEvent(
            ViewModelStateEvents.Event.ConfirmDialog(
                category = default,
                type = "",
                amount = "100"
            )
        )
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.expensePresets.first().type).isEqualTo(default)
    }

    @Test
    fun `clicking the expense preset icon opens a dialog`() = runTest {

        val preset = ExpensePreset(
            id = 0L,
            amount = 100.0,
            category = "Beverage",
            type = "Coffee"
        )
        viewModel.onEvent(ViewModelStateEvents.Event.AddCustomExpense(preset))
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.isDialogVisible).isTrue()
        assertThat(state.selectedPreset).isNotNull()
    }

    @Test
    fun `confirming a custom expense nullifies selectedPreset value`() = runTest {
        viewModel.onEvent(
            ViewModelStateEvents.Event.ConfirmDialog(
                category = "Food",
                type = "",
                amount = "100"
            )
        )
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertThat(state.selectedPreset).isNull()
    }

    @Test
    fun `adding an expense updates the total amount via flow`() = runTest {
        val initialAmount = viewModel.uiState.value.totalAmount
        val preset = ExpensePreset(
            id = 0L,
            amount = 100.0,
            category = "Beverage",
            type = "Coffee"
        )
        viewModel.onEvent(ViewModelStateEvents.Event.AddExpense(preset))
        advanceUntilIdle()
        val newAmount = viewModel.uiState.value.totalAmount
        assertThat(newAmount).isGreaterThan(initialAmount)
    }
}

