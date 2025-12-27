package com.luna.budgetapp.di

import androidx.room.Room
import com.luna.budgetapp.data.local.AppDatabase
import com.luna.budgetapp.data.local.repository.RepositoryImpl
import com.luna.budgetapp.domain.usecase.expense.AddExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.DeleteExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.GetAllExpensesUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByCategoryUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByTypeUseCase
import com.luna.budgetapp.domain.usecase.expense.UpdateExpenseUseCase
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.network.ExpenseService
import com.luna.budgetapp.presentation.screen.addexpense.AddExpenseViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExpenseService::class.java)
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "budget_db"
        ).build()
    }
    single {
        get<AppDatabase>().expenseDao()
    }
    single<ExpenseRepository> {
        RepositoryImpl(get(), get())
    }
    factory {
        AddExpenseUseCase(get())
    }
    factory {
        DeleteExpenseUseCase(get())
    }
    factory {
        GetAllExpensesUseCase(get())
    }
    factory {
        GetExpensesByCategoryUseCase(get())
    }
    factory {
        GetExpensesByTypeUseCase(get())
    }
    factory {
        UpdateExpenseUseCase(get())
    }
    viewModel {
        AddExpenseViewModel(get())
    }
}