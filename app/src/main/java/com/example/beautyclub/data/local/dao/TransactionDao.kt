package com.example.beautyclub.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.beautyclub.data.local.entity.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert
    suspend fun insertTransaction(
        transaction: TransactionEntity
    )

    @Query(
        """
        SELECT *
        FROM transactions
        WHERE memberId = :memberId
        ORDER BY id DESC
        """
    )
    fun getTransactions(
        memberId: Int
    ): Flow<List<TransactionEntity>>
}