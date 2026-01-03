package com.luna.budgetapp.data.local.repository

import com.luna.budgetapp.data.local.dao.ExpenseDao
import com.luna.budgetapp.data.mapper.toEntity
import com.luna.budgetapp.data.mapper.toModel
import com.luna.budgetapp.domain.model.Expense
import com.luna.budgetapp.domain.repository.ExpenseRepository
import com.luna.budgetapp.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.luna.budgetapp.network.ExpenseService
import retrofit2.HttpException
import android.util.Log
import kotlinx.coroutines.flow.onStart
import java.io.IOException

const val TAG = "RepositoryImpl"

class RepositoryImpl(
    private val dao: ExpenseDao,
    private val api: ExpenseService
) : ExpenseRepository {

    override suspend fun getAllExpenses(): Flow<Resource<List<Expense>>> {
        return flow {
            emit(Resource.Loading())
            dao.getAllExpenses().collect { local ->
                emit(Resource.Success(local.map { it.toModel() }))
            }
        }.onStart {
            try {
                val remote = api.getAllExpenses()
                dao.addExpenses(remote.map { it.toEntity() })
            } catch (e: IOException) {
                emit(Resource.Error("Network error, showing cached data"))
                Log.e(TAG, "IOException: ${e.message}")
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: ${e.message}")
                emit(Resource.Error("Server error, showing cached data"))
            }
        }
    }

    override suspend fun getExpensesByCategory(category: String): Flow<Resource<List<Expense>>> {
        return flow {
            emit(Resource.Loading())
            dao.getExpensesByCategory(category).collect { local ->
                emit(Resource.Success(local.map { it.toModel() }))
            }
        }.onStart {
            try {
                val remote = api.getExpenseByCategory(category)
                dao.addExpenses(remote.map { it.toEntity() })
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}")
                emit(Resource.Error("Network error, showing cached data"))
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: ${e.message}")
                emit(Resource.Error("Server error, showing cached data"))
            }
        }
    }

    override suspend fun getExpensesByType(type: String): Flow<Resource<List<Expense>>> {
        return flow {
            emit(Resource.Loading())
            dao.getExpensesByType(type).collect { local ->
                emit(Resource.Success(local.map { it.toModel() }))
            }
        }.onStart {
            try {
                val remote = api.getExpenseByType(type)
                dao.addExpenses(remote.map { it.toEntity() })
            } catch (e: IOException) {
                Log.e(TAG, "IOException: ${e.message}")
                emit(Resource.Error("Network error, showing cached data"))
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException: ${e.message}")
                emit(Resource.Error("Server error, showing cached data"))
            }
        }
    }

    override suspend fun addExpense(expense: Expense) {
        return dao.addExpense(expense.toEntity())
    }

    override suspend fun addExpenses(expenses: List<Expense>) {
        dao.addExpenses(expenses.map { it.toEntity() })
    }

    override suspend fun updateExpense(expense: Expense) {
        return dao.updateExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toEntity())
    }
}
