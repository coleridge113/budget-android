package com.luna.budgetapp.di

import androidx.room.Room
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.luna.budgetapp.data.local.AppDatabase
import com.luna.budgetapp.data.local.repository.ExpenseRepositoryImpl
import com.luna.budgetapp.data.local.repository.AuthRepositoryImpl
import com.luna.budgetapp.data.datastore.dataStore
import com.luna.budgetapp.data.utils.PusherManager
import com.luna.budgetapp.domain.usecase.expense.AddExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.DeleteExpenseUseCase
import com.luna.budgetapp.domain.usecase.expense.GetAllExpensesUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByCategoryUseCase
import com.luna.budgetapp.domain.usecase.expense.GetExpensesByTypeUseCase
import com.luna.budgetapp.domain.usecase.expense.UpdateExpenseUseCase
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.data.datastore.AuthLocalDataSource
import com.luna.budgetapp.data.remote.source.AuthRemoteDataSource
import com.luna.budgetapp.domain.repository.AuthRepository
import com.luna.budgetapp.domain.usecase.UseCases
import com.luna.budgetapp.domain.usecase.auth.GetTokenUseCase
import com.luna.budgetapp.network.AuthService
import com.luna.budgetapp.network.ExpenseService
import com.luna.budgetapp.network.interceptors.AuthInterceptor
import com.luna.budgetapp.presentation.screen.addexpense.AddExpenseViewModel
import com.luna.budgetapp.presentation.screen.auth.AuthViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    single(named("auth")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .addInterceptor(AuthInterceptor { get<AuthLocalDataSource>().getJwtToken() })
            .build()
    }
    single(named("public")) {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .addConverterFactory(GsonConverterFactory.create())
    }
    single {
        get<Retrofit.Builder>()
            .client(get(named("auth")))
            .build()
    }
    single {
        get<Retrofit>().create(ExpenseService::class.java)
    }
    single {
        val publicRetrofit = get<Retrofit.Builder>()
            .client(get(named("public")))
            .build()
        publicRetrofit.create(AuthService::class.java)
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
        ExpenseRepositoryImpl(get(), get())
    }
    single {
        AuthRemoteDataSource(get())
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get(), get())
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
    factory {
        GetTokenUseCase(get())
    }
    factory {
        UseCases(
            get(), 
            get(), 
            get(), 
            get(), 
            get(),
            get()
        )
    }
    viewModel {
        AddExpenseViewModel(get(), get<PusherManager>())
    }
    viewModel {
        AuthViewModel(get())
    }
    single<DataStore<Preferences>> { 
        androidContext().dataStore 
    }
    single {
        AuthLocalDataSource(get())
    }
    single {
        PusherManager(get())
    }
}
