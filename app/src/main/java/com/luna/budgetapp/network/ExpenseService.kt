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
    suspend fun getAllExpenses(): Response<List<ExpenseDto>>

    @GET("{id}")
    suspend fun getExpenseById(@Path("id") id: Long): Response<ExpenseDto>

    @GET("type/{type}")
    suspend fun getExpenseByType(
        @Path("type") type: String
    ): Response<List<ExpenseDto>>

    @GET("category/{category}")
    suspend fun getExpenseByCategory(
        @Path("category") category: String
    ): Response<List<ExpenseDto>>

    @POST("add")
    suspend fun addExpense(@Body expenseDto: ExpenseDto): Response<ExpenseDto>

    @PUT("add/batch")
    suspend fun addExpenses(@Body expenses: List<ExpenseDto>): Response<ExpenseDto>

    @DELETE("{id}")
    suspend fun deleteExpense(@Path("id") id: Long): Response<Boolean>

    @PUT("{id}")
    suspend fun updateExpense(
        @Body expenseDto: ExpenseDto,
        @Path("id") id: Long
    ): Response<ExpenseDto>
}