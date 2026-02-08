package com.luna.budgetapp.presentation.screen.expensepreset

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow

class ExpensePresetViewModel(
    private val useCases: UseCases,
    private val pusherManager: PusherManager,
    private val repository: ExpensePresetRepository
): ViewModel() {

    init {
        initializePusher()
    }

    private val _uiState = MutableStateFlow(ViewModelStateEvents.UiState())
    val uiState = _uiState.asStateFlow()

    private val _effect = Channel<ViewModelStateEvents.UiEffect>()
    val effect = _effect.receiveAsFlow()

    fun onEvent(event: ViewModelStateEvents.Event) {
        when (event) {
            ViewModelStateEvents.Event.LoadTable -> {}
            ViewModelStateEvents.Event.AddExpensePreset -> emitShowDialog()
            ViewModelStateEvents.Event.DismissDialog -> emitDismissDialog()
            is ViewModelStateEvents.Event.ConfirmDialog -> { 
                addExpensePreset(event.category, event.amount.toDouble())
                emitDismissDialog()
            }
        }
    }

    private fun addExpensePreset(category: String, amount: Double) {
        val expensePreset = ExpensePreset(
            id = 4L,
            amount = amount,
            category = category,
            type = "Entertainment"
        )
        _uiState.update { currentState ->
            currentState.copy(
                success = currentState.success + expensePreset
            )
        }
    }

    private fun initializePusher() {
        viewModelScope.launch {
            pusherManager.initPusher()
            pusherManager.subscribeToExpenseChannel {}
        }
    }

    private fun emitShowDialog() {
        viewModelScope.launch {
            _effect.send(ViewModelStateEvents.UiEffect.ShowDialog)
        }
    }

    private fun emitDismissDialog() {
        viewModelScope.launch {
            _effect.send(ViewModelStateEvents.UiEffect.DismissDialog)
        }
    }
}

object ViewModelStateEvents {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val success: List<ExpensePreset> = emptyList()
    )

    sealed interface UiEffect {
        data object DismissDialog : UiEffect
        data object ShowDialog : UiEffect
    }

    sealed interface Event {
        data object LoadTable : Event
        data object AddExpensePreset : Event
        data object DismissDialog : Event
        data class ConfirmDialog(val category: String, val amount: String) : Event
    }

    sealed class Navigation {
    }
}
