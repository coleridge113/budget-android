package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.common.Resource
import com.luna.budgetapp.data.local.dao.ExpensePresetDao
import com.luna.budgetapp.data.mapper.toEntity
import com.luna.budgetapp.data.mapper.toModel
import com.luna.budgetapp.domain.repository.ExpensePresetRepository
import com.luna.budgetapp.domain.model.ExpensePreset
import com.luna.budgetapp.network.ExpenseService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class ExpensePresetRepositoryImpl(
    private val dao: ExpensePresetDao,
    private val api: ExpenseService
): ExpensePresetRepository {
    
    override fun getAllExpensePresets(): Flow<Resource<List<ExpensePreset>>> = flow {
        emit(Resource.Loading)
        try {
            val remote = api.getAllExpensePresets()
            dao.addExpensePresets(remote.map { it.toEntity() })
        } catch (e: Exception) {
            val errorMessage = when(e) {
                is IOException -> "Network error, showing cached data"
                is HttpException -> "Server error, showing cached data"
                else -> "Unknown error occurred"
            }

            emit(Resource.Error(errorMessage))
        }

        emitAll(dao.getAllExpensePresets().map { local ->
            Resource.Success(local.map { it.toModel() })
        })
    }.flowOn(Dispatchers.IO)

    override fun addExpensePresets(expensePresets: List<ExpensePreset>) {
        dao.addExpensePresets(expensePresets.map { it.toEntity() })
    }
}
