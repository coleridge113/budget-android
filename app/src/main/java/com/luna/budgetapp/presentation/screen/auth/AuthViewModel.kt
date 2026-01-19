package com.luna.budgetapp.presentation.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.presentation.screen.auth.ViewModelStateEvents
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AuthViewModel(
    private val useCases: UseCases
) : ViewModel() {

    private val _state = MutableStateFlow(ViewModelStateEvents.uiState())
    val state = _state.asStateFlow()

    private val _navigation = Channel<ViewModelStateEvents.Navigation>()
    val navigation = _navigation.receiveAsFlow()

    fun onEvent(event: ViewModelStateEvents.Event) {
        when (event) {
            ViewModelStateEvents.Event.FetchToken -> { fetchToken() }
            ViewModelStateEvents.Event.GotoAddExpenseRoute -> { gotoAddExpenseRoute() }
        }
    }
    private fun fetchToken() {
        viewModelScope.launch {
            useCases.getTokenUseCase()
        }
    }

    private fun gotoAddExpenseRoute() {
        viewModelScope.launch {
            _navigation.send(ViewModelStateEvents.Navigation.GotoAddExpenseRoute)
        }
    }
}

object ViewModelStateEvents {
    data class uiState(
        val isLoading: Boolean = true,
        val error: String = "",
        val success: String = ""
    ) 

    sealed interface Event {
        data object FetchToken : Event
        data object GotoAddExpenseRoute : Event
    }

    sealed class Navigation {
        data object GotoAddExpenseRoute : Navigation()
    }
}
