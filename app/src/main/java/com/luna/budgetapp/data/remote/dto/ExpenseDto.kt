package com.luna.budgetapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal
import java.time.LocalDate

data class ExpenseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("amount") val amount: BigDecimal,
    @SerializedName("category") val category: String,
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: LocalDate
)