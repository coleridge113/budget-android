package com.luna.budgetapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class ExpensePresetDto(
    @SerializedName("id") val id: Long,
    @SerializedName("amount") val amount: Double,
    @SerializedName("category") val category: String,
    @SerializedName("type") val type: String,
    @SerializedName("created_at") val createdAt: LocalDateTime
)
