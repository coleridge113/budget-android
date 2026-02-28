package com.luna.budgetapp.presentation.screen.expenselist

import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.model.DateFilter
import com.luna.budgetapp.presentation.model.ChartData
import com.luna.budgetapp.domain.model.Category

val defaultCategories = listOf(
    Category.FOOD,
    Category.DATE,
    Category.BEVERAGE,
    Category.COMMUTE,
    Category.OTHERS,
    Category.FITNESS
)

data class UiState(
    val isExpensesLoading: Boolean = false,
    val error: String? = null,
    val expenses: List<Expense> = emptyList(),
    val dialogState: DialogState? = null,
    val selectedRange: DateFilter = DateFilter.Daily,
    val chartDataList: List<ChartData> = emptyList(),
    val totalAmount: Double = 0.0,
    val selectedCategoryMap: Map<Category, Boolean> = 
        Category.entries
            .associate {
                if (it in defaultCategories) {
                    it to true
                } else {
                    it to false
                }
            }
) 

sealed interface DialogState {
    data object CalendarForm : DialogState
    data class CategoryFilterForm(val filteredCategories: Map<Category, Boolean>) : DialogState
    data class ConfirmDeleteExpense(val expenseId: Long) : DialogState
}

sealed interface Event {
    data object ShowCalendarForm : Event
    data object ShowCategoryFilterDialog : Event
    data object DismissDialog : Event
    data object ResetCategoryFilters : Event
    data class ShowDeleteConfirmationDialog(val expenseId: Long) : Event
    data class DeleteExpense(val expenseId: Long) : Event
    data class SelectDateRange(val selectedRange: DateFilter) : Event
    data class SelectCategoryFilter(val selectedCategoryMap: Map<Category, Boolean>) : Event
}

sealed interface Navigation {}
