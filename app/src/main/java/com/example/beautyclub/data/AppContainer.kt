package com.example.beautyclub.data

import android.content.Context
import com.example.beautyclub.data.local.BeautyClubDatabase
import com.example.beautyclub.data.repository.MemberRepository
import com.example.beautyclub.data.repository.TransactionRepository

class AppContainer(
    context: Context
) {

    private val database =
        BeautyClubDatabase.getDatabase(context)

    val memberRepository =
        MemberRepository(
            database.memberDao()
        )

    val transactionRepository =
        TransactionRepository(
            database.transactionDao(),
            database.memberDao()
        )
}