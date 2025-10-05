package com.luna.budgetapp.data.remote.dto

import java.math.BigDecimal
import java.time.LocalDate

data class ExpenseDto(
    val id: Long,
    val name: String?,
    val cost: BigDecimal,
    val category: String,
    val type: String,
    val date: LocalDate
)