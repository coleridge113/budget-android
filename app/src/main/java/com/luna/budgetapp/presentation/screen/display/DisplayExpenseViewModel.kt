package com.luna.budgetapp.presentation.screen.display


    // private fun getAllExpenses() {
    //     viewModelScope.launch {
    //         useCases.getAllExpensesUseCase().collect { resource ->
    //             _uiState.update { curr ->
    //                 when (resource) {
    //                     Resource.Loading -> curr.copy(
    //                         isLoading = true,
    //                         error = ""
    //                     )
    //                     is Resource.Error -> curr.copy(
    //                         isLoading = false,
    //                         error = resource.message
    //                     )
    //                     is Resource.Success -> curr.copy(
    //                         isLoading = false,
    //                         error = "",
    //                         success = resource.data
    //                     )
    //                 }
    //             }
    //         }
    //     }
    // }
    //
