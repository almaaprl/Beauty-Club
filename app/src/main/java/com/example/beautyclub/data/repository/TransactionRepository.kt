package com.example.beautyclub.data.repository

import com.example.beautyclub.data.local.dao.MemberDao
import com.example.beautyclub.data.local.dao.TransactionDao
import com.example.beautyclub.data.local.entity.TransactionEntity

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val memberDao: MemberDao
) {

    fun getTransactions(
        memberId: Int
    ) = transactionDao.getTransactions(memberId)

    suspend fun addTransaction(
        memberId: Int,
        treatmentName: String,
        amount: Double,
        date: String
    ) {

        val pointEarned =
            (amount / 10000).toInt()

        val transaction =
            TransactionEntity(
                memberId = memberId,
                treatmentName = treatmentName,
                amount = amount,
                pointEarned = pointEarned,
                date = date
            )

        transactionDao.insertTransaction(
            transaction
        )

        val member =
            memberDao.getMemberById(memberId)

        member?.let {

            memberDao.updateMember(
                it.copy(
                    points =
                        it.points + pointEarned
                )
            )
        }
    }
}