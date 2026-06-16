package com.example.beautyclub.data.repository

import com.example.beautyclub.data.local.dao.MemberDao
import com.example.beautyclub.data.local.dao.TransactionDao
import com.example.beautyclub.data.local.entity.TransactionEntity

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val memberDao: MemberDao
) {

    fun getTransactions(memberId: Int) =
        transactionDao.getTransactions(memberId)

    // Untuk transaksi treatment biasa (Purchases)
    suspend fun addTransaction(
        memberId: Int,
        treatmentName: String,
        amount: Double,
        date: String
    ) {
        val pointEarned = (amount / 10000).toInt()

        transactionDao.insertTransaction(
            TransactionEntity(
                memberId      = memberId,
                treatmentName = treatmentName,
                amount        = amount,
                pointEarned   = pointEarned,
                date          = date,
                type          = "Purchases"
            )
        )

        val member = memberDao.getMemberById(memberId)
        member?.let {
            memberDao.updateMember(it.copy(points = it.points + pointEarned))
        }
    }

    // Untuk redeem reward (Rewards) — poin negatif, amount 0
    suspend fun addRedemption(
        memberId: Int,
        rewardName: String,
        pointCost: Int,
        date: String
    ) {
        transactionDao.insertTransaction(
            TransactionEntity(
                memberId      = memberId,
                treatmentName = rewardName,
                amount        = 0.0,
                pointEarned   = -pointCost,  // negatif
                date          = date,
                type          = "Rewards"
            )
        )
    }
}