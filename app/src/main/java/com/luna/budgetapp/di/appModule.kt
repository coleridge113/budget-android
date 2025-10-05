package com.luna.budgetapp.di

import androidx.room.Room
import com.luna.budgetapp.data.local.AppDatabase
import com.luna.budgetapp.data.local.repository.Repository
import com.luna.budgetapp.data.local.repository.RepositoryImpl
import com.luna.budgetapp.data.remote.ExpenseRemoteSource
import com.luna.budgetapp.network.ExpenseService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/v1/")
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
    single {
        ExpenseRemoteSource(get())
    }
    single<Repository> {
        RepositoryImpl(
            get()
        )
    }
}