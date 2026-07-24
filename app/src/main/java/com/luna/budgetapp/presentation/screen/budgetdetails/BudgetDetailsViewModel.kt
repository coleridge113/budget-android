package com.luna.budgetapp.presentation.screen.budgetdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.domain.model.Budget
import com.luna.budgetapp.domain.model.DateFilter
import com.luna.budgetapp.domain.model.DateRange
import com.luna.budgetapp.domain.usecase.BudgetUseCases
import com.luna.budgetapp.domain.usecase.ExpenseUseCases
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalTime
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class)
class BudgetDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val budgetUseCases: BudgetUseCases,
    private val expenseUseCases: ExpenseUseCases
): ViewModel() {
    val budgetId: Long = checkNotNull(savedStateHandle["budgetId"])
    private val _dialogState = MutableStateFlow<DialogState?>(null)
    private val _dateFlow = MutableStateFlow<DateRange?>(null)
    private val _budget = budgetUseCases.getBudgetById(budgetId)
    private val _expenses =
        combine(_budget, _dateFlow) { budget, date ->
            budget to date
        }
            .flatMapLatest { (budget, date) ->
                val (defaultStart, defaultEnd) = budget.frequency.resolve()
                val start = _dateFlow.value?.start
                val end = _dateFlow.value?.end

                expenseUseCases.getExpensesByDateRange(
                    categories = budget.interactors.map { it.name },
                    start = start ?: defaultStart,
                    end = end ?: defaultEnd
                )
            }

    val uiState = combine(
        _budget,
        _expenses,
        _dialogState
    ) { budget, expenses, dialog ->
        UiState.Success(
            budget = budget,
            expenses = expenses,
            dialog = dialog
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000.milliseconds),
            initialValue = UiState.Loading
        )

    fun onEvent(event: Event) {
        when (event) {
            Event.DismissDialog -> dismissDialog()
            is Event.ClickCalendar -> showCalendarForm(event.type)
            is Event.ConfirmDate -> updateDateRange(event.date)
        }
    }

    private fun showCalendarForm(type: DateFilter) {
        when (type) {
            DateFilter.Daily -> {
                _dialogState.update {
                    DialogState.DatePicker
                }
            }
            DateFilter.Monthly -> {}
            else -> {}
        }
    }

    private fun updateDateRange(date: LocalDate) {
        _dateFlow.update {
            DateRange(
                start = date.atStartOfDay(),
                end = date.atTime(LocalTime.MAX)
            )
        }
        dismissDialog()
    }

    private fun dismissDialog() {
        _dialogState.update { null }
    }
}
