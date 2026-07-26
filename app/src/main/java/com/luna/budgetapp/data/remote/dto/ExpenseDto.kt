package com.luna.budgetapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.luna.budgetapp.domain.model.Category
import java.time.LocalDateTime

data class ExpenseDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?,
    @SerializedName("amount") val amount: Long,
    @SerializedName("category") val category: Category,
    @SerializedName("type") val type: String,
    @SerializedName("date") val date: LocalDateTime
)
