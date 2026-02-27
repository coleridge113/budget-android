package com.luna.budgetapp.domain.model

import com.luna.budgetapp.domain.model.Category

data class CategoryTotalProjection(
    val category: Category,
    val total: Double
)
