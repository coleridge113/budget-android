
package com.luna.budgetapp.domain.model

import java.math.BigDecimal
import java.time.LocalDate

data class Expense(
    val id: Long,
    val name: String?,
    val amount: Double,
    val category: String,
    val type: String,
    val date: String
)