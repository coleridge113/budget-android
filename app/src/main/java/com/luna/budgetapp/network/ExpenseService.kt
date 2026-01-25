package com.luna.budgetapp.network

import com.luna.budgetapp.data.remote.dto.ExpenseDto
import com.luna.budgetapp.data.remote.dto.ExpensePresetDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ExpenseService {

    @GET("api/v1/expenses/")
    suspend fun getAllExpenses(): List<ExpenseDto>

    @GET("api/v1/expenses/{id}")
    suspend fun getExpenseById(@Path("id") id: Long): List<ExpenseDto>

    @GET("api/v1/expenses/type/{type}")
    suspend fun getExpenseByType(
        @Path("type") type: String
    ): List<ExpenseDto>

    @GET("api/v1/expenses/category/{category}")
    suspend fun getExpenseByCategory(
        @Path("category") category: String
    ): List<ExpenseDto>

    @POST("api/v1/expenses/add")
    suspend fun addExpense(@Body expenseDto: ExpenseDto): List<ExpenseDto>

    @PUT("api/v1/expenses/add/batch")
    suspend fun addExpenses(@Body expenses: List<ExpenseDto>): List<ExpenseDto>

    @DELETE("api/v1/expenses/{id}")
    suspend fun deleteExpense(@Path("id") id: Long): Boolean

    @PUT("api/v1/expenses/{id}")
    suspend fun updateExpense(
        @Body expenseDto: ExpenseDto,
        @Path("id") id: Long
    ): ExpenseDto

    @GET("api/v1/expense-presets")
    suspend fun getAllExpensePresets(): List<ExpensePresetDto>
}
