package com.luna.budgetapp.network

import com.luna.budgetapp.data.remote.dto.ExpenseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExpenseService {

    @GET("/")
    suspend fun getAllExpenses(): List<ExpenseDto>

    @GET("{id}")
    suspend fun getExpenseById(@Path("id") id: Long): ExpenseDto

    @GET("type/{type}")
    suspend fun getExpenseByType(@Path("type") type: String): List<ExpenseDto>

    @GET("category/{category}")
    suspend fun getExpenseByCategory(@Path("category") category: String): List<ExpenseDto>

    @POST("add")
    suspend fun addExpense(@Body expenseDto: ExpenseDto): ExpenseDto

    @DELETE("{id}")
    suspend fun deleteExpense(@Path("id") id: Long)

    @PUT("{id}")
    suspend fun updateExpense(@Body expenseDto: ExpenseDto): ExpenseDto
}