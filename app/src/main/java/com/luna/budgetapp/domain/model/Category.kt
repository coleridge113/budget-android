package com.luna.budgetapp.domain.model

enum class Category {
    FOOD,
    DATE,
    BEVERAGE,
    HOUSE,
    COMMUTE,
    BILLS,
    OTHERS,
    GROCERY,
    FITNESS,
    LEISURE,
    PERSONAL;

    fun getDisplayName(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

fun String.toCategory(): Category {
    return Category.entries.find { 
        it.name.equals(this, ignoreCase = true)
    } ?: Category.OTHERS
}
