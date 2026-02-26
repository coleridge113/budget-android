package com.luna.budgetapp.domain.model

data class CategoryFilterProfile(
    val profileName: String,
    val filterSetup: List<CategoryFilterItem>
)
