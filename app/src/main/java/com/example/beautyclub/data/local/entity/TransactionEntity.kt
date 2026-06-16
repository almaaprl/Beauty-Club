package com.example.beautyclub.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = MemberEntity::class,
            parentColumns = ["id"],
            childColumns = ["memberId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class TransactionEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val memberId: Int,

    val treatmentName: String,

    val amount: Double,

    val pointEarned: Int,

    val date: String,

    val type: String = "Rewards"
)