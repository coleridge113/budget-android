package com.luna.budgetapp.network

import com.luna.budgetapp.data.remote.dto.ExpenseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExpenseService {

    @GET("api/v1/expenses")
    suspend fun getAllExpenses(): List<ExpenseDto>

    @GET("api/v1/expenses/{id}")
    suspend fun getExpenseById(@Path("id") id: Long): Response<ExpenseDto>

    @GET("api/v1/expenses/type/{type}")
    suspend fun getExpenseByType(
        @Path("type") type: String
    ): Response<List<ExpenseDto>>

    @GET("api/v1/expenses/category/{category}")
    suspend fun getExpenseByCategory(
        @Path("category") category: String
    ): Response<List<ExpenseDto>>

    @POST("api/v1/expenses/add")
    suspend fun addExpense(@Body expenseDto: ExpenseDto): Response<ExpenseDto>

    @PUT("api/v1/expenses/add/batch")
    suspend fun addExpenses(@Body expenses: List<ExpenseDto>): Response<ExpenseDto>

    @DELETE("api/v1/expenses/{id}")
    suspend fun deleteExpense(@Path("id") id: Long): Response<Boolean>

    @PUT("api/v1/expenses/{id}")
    suspend fun updateExpense(
        @Body expenseDto: ExpenseDto,
        @Path("id") id: Long
    ): Response<ExpenseDto>
}