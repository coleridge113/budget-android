package com.luna.budgetapp.domain

import java.math.BigDecimal
import java.time.LocalDate


data class Expense(
    val id: Long,
    val name: String?,
    val amount: BigDecimal,
    val category: String,
    val type: String,
    val date: LocalDate
)
