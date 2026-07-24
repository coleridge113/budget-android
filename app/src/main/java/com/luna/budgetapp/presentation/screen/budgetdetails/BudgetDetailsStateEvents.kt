package com.luna.budgetapp.presentation.screen.budgetdetails

import com.luna.budgetapp.domain.model.Budget
import com.luna.budgetapp.domain.model.DateFilter
import com.luna.budgetapp.domain.model.Expense
import java.time.LocalDate
import java.time.YearMonth

sealed interface UiState {
    data object Loading : UiState
    data class Error(val message: String? = null) : UiState
    data class Success(
        val budget: Budget,
        val expenses: List<Expense>,
        val dialog: DialogState? = null
    ) : UiState
}


sealed interface Event {
    data object DismissDialog : Event
    data class ClickCalendar(val type: DateFilter) : Event
    data class ConfirmDate(val date: LocalDate) : Event
    data class ConfirmYearMonth(val yearMonth: YearMonth) : Event
}

sealed interface DialogState {
    data object DatePicker : DialogState
    data object YearMonthPicker : DialogState
}
