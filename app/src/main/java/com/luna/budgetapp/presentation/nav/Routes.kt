package com.luna.budgetapp.presentation.nav

import kotlinx.serialization.Serializable

@Serializable
sealed class Routes {

    @Serializable
    data object AddExpensesRoute : Routes()

}
