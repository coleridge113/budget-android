package com.luna.budgetapp.data.mapper

import com.luna.budgetapp.data.local.entity.ExpensePresetEntity
import com.luna.budgetapp.data.remote.dto.ExpensePresetDto
import com.luna.budgetapp.domain.model.ExpensePreset

fun ExpensePresetDto.toEntity(): ExpensePresetEntity {
    return ExpensePresetEntity(
        id = this.id,
        amount = this.amount,
        category = this.category,
        type = this.type,
        createdAt = this.createdAt
    )
}

fun ExpensePresetEntity.toModel(): ExpensePreset {
    return ExpensePreset(
        id = this.id,
        amount = this.amount,
        category = this.category,
        type = this.type,
        createdAt = this.createdAt
    )
}

fun ExpensePreset.toEntity(): ExpensePresetEntity {
    return ExpensePresetEntity(
        id = this.id,
        amount = this.amount,
        category = this.category,
        type = this.type,
        createdAt = this.createdAt
    )
}
