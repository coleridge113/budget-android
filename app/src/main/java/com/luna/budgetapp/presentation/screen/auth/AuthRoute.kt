package com.luna.budgetapp.presentation.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import com.luna.budgetapp.presentation.nav.Routes

@Composable
fun AuthRoute(
    viewModel: AuthViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.navigation.collectLatest { navigation ->
                when (navigation) {
                    ViewModelStateEvents.Navigation.GotoAddExpenseRoute -> {
                        navController.navigate(Routes.AddExpensesRoute) {
                            popUpTo(Routes.AuthRoute) { inclusive = true }
                        }
                    }
                }
            }
    }
}
