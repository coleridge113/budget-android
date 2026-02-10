package com.luna.budgetapp.domain.model

import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

data class Expense(
    val id: Long? = null,
    val name: String? = null,
    val amount: Double,
    val category: String,
    val type: String,
    val date: LocalDateTime = LocalDateTime.now()
)
