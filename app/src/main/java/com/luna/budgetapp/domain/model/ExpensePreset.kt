package com.luna.budgetapp.domain.model

import java.time.LocalDateTime

data class ExpensePreset(
    val id: Long? = null,
    val amount: Long,
    val category: Category,
    val type: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) : TableItem
