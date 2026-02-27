package com.luna.budgetapp.domain.repository

import com.luna.budgetapp.domain.model.Category

interface CategoryRepository {

    fun getDefaultCategories(): Map<Category, Boolean>
}
