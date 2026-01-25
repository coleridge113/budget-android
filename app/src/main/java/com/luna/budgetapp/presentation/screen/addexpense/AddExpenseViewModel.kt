package com.luna.budgetapp.presentation.screen.addexpense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.usecase.UseCases
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddExpenseViewModel(
    private val useCases: UseCases,
    private val pusherManager: PusherManager
): ViewModel() {

    init {
        initializePusher()
    }

    private val _uiState = MutableStateFlow(ViewModelStateEvents.UiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: ViewModelStateEvents.Event) {
        when (event) {
            ViewModelStateEvents.Event.LoadExpenses -> { getAllExpenses() }
            ViewModelStateEvents.Event.AddExpense -> {}
        }
    }

    private fun getAllExpenses() {
        viewModelScope.launch {
            useCases.getAllExpensesUseCase().collect { resource ->
                _uiState.update { curr ->
                    when (resource) {
                        Resource.Loading -> curr.copy(
                            isLoading = true,
                            error = ""
                        )
                        is Resource.Error -> curr.copy(
                            isLoading = false,
                            error = resource.message
                        )
                        is Resource.Success -> curr.copy(
                            isLoading = false,
                            error = "",
                            success = resource.data
                        )
                    }
                }
            }
        }
    }

    private fun initializePusher() {
        viewModelScope.launch {
            pusherManager.initPusher()
            pusherManager.subscribeToExpenseChannel { getAllExpenses() }
        }
    }
}

object ViewModelStateEvents {
    data class UiState(
        val isLoading: Boolean = false,
        val error: String = "",
        val success: List<Expense> = emptyList()
    )

    sealed interface Event {
        data object LoadExpenses : Event
        data object AddExpense : Event
    }

    sealed class Navigation {
    }
}
